package capstone.design.control_automation.report.util.hwp;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kr.dogfoot.hwplib.object.bodytext.control.ControlTable;
import kr.dogfoot.hwplib.object.bodytext.control.ControlType;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.CtrlHeaderGso;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.HeightCriterion;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.HorzRelTo;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.ObjectNumberSort;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.RelativeArrange;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.TextFlowMethod;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.TextHorzArrange;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.VertRelTo;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.WidthCriterion;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.sectiondefine.TextDirection;
import kr.dogfoot.hwplib.object.bodytext.control.gso.textbox.LineChange;
import kr.dogfoot.hwplib.object.bodytext.control.gso.textbox.TextVerticalAlignment;
import kr.dogfoot.hwplib.object.bodytext.control.table.Cell;
import kr.dogfoot.hwplib.object.bodytext.control.table.DivideAtPageBoundary;
import kr.dogfoot.hwplib.object.bodytext.control.table.ListHeaderForCell;
import kr.dogfoot.hwplib.object.bodytext.control.table.Row;
import kr.dogfoot.hwplib.object.bodytext.control.table.Table;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.docinfo.BorderFill;
import kr.dogfoot.hwplib.object.docinfo.DocInfo;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BackSlashDiagonalShape;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BorderThickness;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BorderType;
import kr.dogfoot.hwplib.object.docinfo.borderfill.SlashDiagonalShape;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.PatternFill;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.PatternType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HwpTableEditor {

    private static final Map<Class<?>, List<Field>> fieldCache = new ConcurrentHashMap<>();

    private final HwpConfigurer configurer;

    public int addBorderFillInfo(DocInfo docInfo) {
        BorderFill bf = docInfo.addNewBorderFill();
        bf.getProperty().set3DEffect(false);
        bf.getProperty().setShadowEffect(false);
        bf.getProperty().setSlashDiagonalShape(SlashDiagonalShape.None);
        bf.getProperty().setBackSlashDiagonalShape(BackSlashDiagonalShape.None);
        bf.getLeftBorder().setType(BorderType.Solid);
        bf.getLeftBorder().setThickness(BorderThickness.MM0_5);
        bf.getLeftBorder().getColor().setValue(0x0);
        bf.getRightBorder().setType(BorderType.Solid);
        bf.getRightBorder().setThickness(BorderThickness.MM0_5);
        bf.getRightBorder().getColor().setValue(0x0);
        bf.getTopBorder().setType(BorderType.Solid);
        bf.getTopBorder().setThickness(BorderThickness.MM0_5);
        bf.getTopBorder().getColor().setValue(0x0);
        bf.getBottomBorder().setType(BorderType.Solid);
        bf.getBottomBorder().setThickness(BorderThickness.MM0_5);
        bf.setDiagonalSort(BorderType.None);
        bf.setDiagonalThickness(BorderThickness.MM0_7);

        bf.getFillInfo().getType().setPatternFill(true);
        bf.getFillInfo().createPatternFill();
        PatternFill pf = bf.getFillInfo().getPatternFill();
        pf.setPatternType(PatternType.None);
        pf.getBackColor().setValue(-1);
        pf.getPatternColor().setValue(0);

        return docInfo.getBorderFillList().size();
    }

    public <T> void writeTable(Paragraph paragraph, List<T> tableData, GsoParam gsoParam, Integer borderFillId)
        throws UnsupportedEncodingException, IllegalAccessException {
        paragraph.getText().addExtendCharForTable();

        ControlTable controlTable = (ControlTable) paragraph.addNewControl(ControlType.Table);
        CtrlHeaderGso headerGso = controlTable.getHeader();
        configureHeaderGso(headerGso, gsoParam);

        Table table = controlTable.getTable();

        List<Field> fields = getFieldsToMakeTable(tableData.get(0));

        int rowCount = tableData.size() + 1; // table header 공간 + 1
        int colCount = fields.size() + 1; // table column 개수
        configureTable(table, borderFillId);
        makeTableCells(table, controlTable, rowCount, colCount, borderFillId);
        writeObjectsInCell(controlTable, tableData, fields);
    }

    private <T> List<Field> getFieldsToMakeTable(T tableData) {
        if (!fieldCache.containsKey(tableData.getClass())) {
            Class<?> tableDataClass = tableData.getClass();
            Field[] fields = tableDataClass.getDeclaredFields();
            List<Field> sorted = Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(TableColumn.class))
                .sorted(Comparator.comparingInt(field -> field.getAnnotation(TableColumn.class).order()))
                .toList();
            fieldCache.put(tableData.getClass(), sorted);
            return sorted;
        }
        return fieldCache.get(tableData.getClass());
    }

    //control header record 설정
    private void configureHeaderGso(CtrlHeaderGso headerGso, GsoParam gsoParam) {
        headerGso.getProperty().setLikeWord(false);
        headerGso.getProperty().setApplyLineSpace(false);
        headerGso.getProperty().setVertRelTo(VertRelTo.Para);
        headerGso.getProperty().setVertRelativeArrange(RelativeArrange.TopOrLeft);
        headerGso.getProperty().setHorzRelTo(HorzRelTo.Para);
        headerGso.getProperty().setHorzRelativeArrange(RelativeArrange.TopOrLeft);
        headerGso.getProperty().setVertRelToParaLimit(false); // 다름
        headerGso.getProperty().setAllowOverlap(false);
        headerGso.getProperty().setWidthCriterion(WidthCriterion.Absolute);
        headerGso.getProperty().setHeightCriterion(HeightCriterion.Absolute);
        headerGso.getProperty().setProtectSize(false);
        headerGso.getProperty().setTextFlowMethod(TextFlowMethod.FitWithText);
        headerGso.getProperty().setTextHorzArrange(TextHorzArrange.BothSides);
        headerGso.getProperty().setObjectNumberSort(ObjectNumberSort.Table);
        headerGso.setxOffset(mmToHwp(gsoParam.posX()));
        headerGso.setyOffset(mmToHwp(gsoParam.posY()));
        headerGso.setWidth(mmToHwp(gsoParam.width()));
        headerGso.setHeight(mmToHwp(gsoParam.height()));
        headerGso.setzOrder(0);
        headerGso.setOutterMarginLeft(0);
        headerGso.setOutterMarginRight(0);
        headerGso.setOutterMarginTop(0);
        headerGso.setOutterMarginBottom(0);
    }

    // table record 지정(row, col 개수 포함)
    private void configureTable(Table table, int borderFillId) {
        table.getProperty().setDivideAtPageBoundary(DivideAtPageBoundary.DivideByCell);
        table.getProperty().setAutoRepeatTitleRow(false);
        table.setCellSpacing(0);
        table.setLeftInnerMargin(0);
        table.setRightInnerMargin(0);
        table.setTopInnerMargin(0);
        table.setBottomInnerMargin(0);
        table.setBorderFillId(borderFillId);
    }

    private void makeTableCells(Table table, ControlTable controlTable, int rowCount, int colCount, int borderFillId) {
        table.setRowCount(rowCount);
        table.setColumnCount(colCount);
        ArrayList<Integer> cellCountOfRowList = table.getCellCountOfRowList();
        for (int i = 0; i < rowCount; i++) {
            cellCountOfRowList.add(colCount);
            Row row = controlTable.addNewRow();
            for (int j = 0; j < colCount; j++) {
                Cell cell = row.addNewCell();
                configureListHeaderForCell(cell, i, j, borderFillId);
            }
        }
    }

    public <T> void writeObjectsInCell(ControlTable controlTable, List<T> tableData, List<Field> fields)
        throws UnsupportedEncodingException, IllegalAccessException {
        ArrayList<Row> rows = controlTable.getRowList();
        List<String> data = getTHeader(fields);

        for (int rowIdx = 0; rowIdx < rows.size(); rowIdx++) {
            Row row = rows.get(rowIdx);
            if (rowIdx != 0) {
                T tdata = tableData.get(rowIdx - 1);
                data = new ArrayList<>();
                data.add(String.valueOf(rowIdx));
                for (Field f : fields) {
                    f.setAccessible(true);
                    data.add(f.get(tdata).toString());
                }
            }

            ArrayList<Cell> cells = row.getCellList();
            for (int colIdx = 0; colIdx < cells.size(); colIdx++) {
                Cell cell = cells.get(colIdx);
                String text = data.get(colIdx);
                configureCellSize(cell, mmToHwp(getAutoWidthByText(text)));

                Paragraph paragraph = createParagraphForCell(cell);
                paragraph.getText().addString(text);
            }
        }
    }

    private List<String> getTHeader(List<Field> fields) {
        List<String> data = new ArrayList<>();
        data.add("번호");
        for (Field field : fields) {
            data.add(field.getAnnotation(TableColumn.class).name());
        }
        return data;
    }

    private void configureListHeaderForCell(Cell cell, int rowIndex, int colIndex, int borderFillId) {
        ListHeaderForCell lh = cell.getListHeader();
        lh.setParaCount(1);
        lh.getProperty().setTextDirection(TextDirection.Horizontal);
        lh.getProperty().setLineChange(LineChange.Normal);
        lh.getProperty().setTextVerticalAlignment(TextVerticalAlignment.Center);
        lh.getProperty().setProtectCell(false);
        lh.getProperty().setEditableAtFormMode(false);
        lh.setColIndex(colIndex);
        lh.setRowIndex(rowIndex);
        lh.setColSpan(1);
        lh.setRowSpan(1);
        lh.setHeight(mmToHwp(8));
        lh.setLeftMargin(0);
        lh.setRightMargin(0);
        lh.setTopMargin(0);
        lh.setBottomMargin(0);
        lh.setBorderFillId(borderFillId);
        lh.setFieldName("");
    }

    private void configureCellSize(Cell cell, Long width) {
        ListHeaderForCell listHeader = cell.getListHeader();
        listHeader.setWidth(width);
        listHeader.setTextWidth(width);
    }

    private double getAutoWidthByText(String text) {
        return Math.max(Math.min((text.length() * 2.5), 40), 8);
    }

    private long mmToHwp(double mm) {
        return (long) (mm * 72000.0f / 254.0f + 0.5f);
    }

    // cell의 텍스트 지정
    private Paragraph createParagraphForCell(Cell cell) {
        Paragraph paragraph = cell.getParagraphList().addNewParagraph();
        paragraph.createCharShape();
        paragraph.createLineSeg();
        paragraph.getLineSeg().addNewLineSegItem();
        paragraph.createText();
        configurer.configureParagraph(paragraph, "tdata");
        return paragraph;
    }

}
