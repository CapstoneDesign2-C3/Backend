package capstone.design.control_automation.report.util.hwp.fragments.table;

import capstone.design.control_automation.report.util.hwp.dto.GsoParam;
import capstone.design.control_automation.report.util.hwp.dto.TableColumn;
import capstone.design.control_automation.report.util.hwp.fragments.HwpConfigurer;
import capstone.design.control_automation.report.util.hwp.fragments.style.BorderFillStyler;
import capstone.design.control_automation.report.util.hwp.fragments.style.GsoConfigurator;
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
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.sectiondefine.TextDirection;
import kr.dogfoot.hwplib.object.bodytext.control.gso.textbox.LineChange;
import kr.dogfoot.hwplib.object.bodytext.control.gso.textbox.TextVerticalAlignment;
import kr.dogfoot.hwplib.object.bodytext.control.table.Cell;
import kr.dogfoot.hwplib.object.bodytext.control.table.ListHeaderForCell;
import kr.dogfoot.hwplib.object.bodytext.control.table.Row;
import kr.dogfoot.hwplib.object.bodytext.control.table.Table;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.docinfo.DocInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HwpTableEditor {

    private static final Map<Class<?>, List<Field>> fieldCache = new ConcurrentHashMap<>();

    private final HwpConfigurer configurer;
    private final BorderFillStyler borderFillStyler;
    private final GsoConfigurator gsoConfigurator;
    private final HwpTableConfigurator hwpTableConfigurator;

    public int addBorderFillInfo(DocInfo docInfo) {
        borderFillStyler.addBorderFillInfo(docInfo);
        return docInfo.getBorderFillList().size();
    }

    public <T> void writeVerticalTable(Paragraph paragraph, T dataToWrite, GsoParam gsoParam, int borderFillId)
        throws IllegalAccessException, UnsupportedEncodingException {
        paragraph.getText().addExtendCharForTable();

        ControlTable controlTable = (ControlTable) paragraph.addNewControl(ControlType.Table);
        gsoConfigurator.configureHeaderGso(controlTable.getHeader(), gsoParam);

        Table table = controlTable.getTable();
        hwpTableConfigurator.configureTable(table, borderFillId, gsoParam);

        List<Field> fields = getFieldsToMakeTable(dataToWrite);
        int rowCount = fields.size() + 1;
        int colCount = 2;
        hwpTableConfigurator.makeTableCells(table, controlTable, rowCount, colCount, borderFillId);

        List<List<String>> tableData = extractVerticalTableDataFromOrigin(dataToWrite, fields);
        writeDataInTable(controlTable, tableData);
    }

    public <T> void writeTable(Paragraph paragraph, List<T> dataToWrite, GsoParam gsoParam, Integer borderFillId)
        throws UnsupportedEncodingException, IllegalAccessException {
        paragraph.getText().addExtendCharForTable();

        ControlTable controlTable = (ControlTable) paragraph.addNewControl(ControlType.Table);
        gsoConfigurator.configureHeaderGso(controlTable.getHeader(), gsoParam);

        Table table = controlTable.getTable();
        hwpTableConfigurator.configureTable(table, borderFillId, gsoParam);

        List<Field> fields = getFieldsToMakeTable(dataToWrite.get(0));
        int rowCount = dataToWrite.size() + 1; // table header 공간 + 1
        int colCount = fields.size() + 1; // table column 개수
        hwpTableConfigurator.makeTableCells(table, controlTable, rowCount, colCount, borderFillId);

        List<List<String>> tableData = extractTableDataFromOrigin(dataToWrite, fields);
        writeDataInTable(controlTable, tableData);
    }

    private <T> List<List<String>> extractVerticalTableDataFromOrigin(T dataToWrite, List<Field> fields)
        throws IllegalAccessException {
        List<List<String>> tableData = new ArrayList<>();
        for (int row = 0; row < fields.size() + 1; row++) {
            tableData.add(new ArrayList<>());
            List<String> rowData = tableData.get(row);
            if (row == 0) {
                rowData.add("분류");
                rowData.add("값");
                continue;
            }
            Field field = fields.get(row - 1);
            rowData.add(field.getAnnotation(TableColumn.class).name());
            rowData.add(field.get(dataToWrite).toString());
        }

        return tableData;
    }

    private <T> List<List<String>> extractTableDataFromOrigin(List<T> dataToWrite, List<Field> fields)
        throws IllegalAccessException {
        List<List<String>> tableData = new ArrayList<>();
        for (int row = 0; row < dataToWrite.size() + 1; row++) {
            tableData.add(new ArrayList<>());
            List<String> rowData = tableData.get(row);
            if (row == 0) {
                rowData.add("번호");
                for (Field f : fields) {
                    rowData.add(f.getAnnotation(TableColumn.class).name());
                }
                continue;
            }

            T curRowData = dataToWrite.get(row - 1);
            rowData.add(String.valueOf(row));
            for (Field f : fields) {
                rowData.add(f.get(curRowData).toString());
            }
        }

        return tableData;
    }

    private void writeDataInTable(ControlTable controlTable, List<List<String>> tableData) throws UnsupportedEncodingException {
        ArrayList<Row> rows = controlTable.getRowList();
        for (int i = 0; i < rows.size(); i++) {
            Row row = rows.get(i);

            ArrayList<Cell> cells = row.getCellList();
            for (int j = 0; j < cells.size(); j++) {
                Cell cell = cells.get(j);
                String text = tableData.get(i).get(j);
                hwpTableConfigurator.configureCellSize(cell, text);

                Paragraph paragraph = hwpTableConfigurator.createParagraphForCell(cell);
                configurer.configureParagraph(paragraph, "tdata");
                paragraph.getText().addString(text);
            }
        }
    }

    private <T> List<Field> getFieldsToMakeTable(T tableData) {
        if (!fieldCache.containsKey(tableData.getClass())) {
            Class<?> tableDataClass = tableData.getClass();
            Field[] fields = tableDataClass.getDeclaredFields();
            List<Field> sorted = Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(TableColumn.class))
                .peek(field -> field.setAccessible(true))
                .sorted(Comparator.comparingInt(field -> field.getAnnotation(TableColumn.class).order()))
                .toList();
            fieldCache.put(tableData.getClass(), sorted);
            return sorted;
        }
        return fieldCache.get(tableData.getClass());
    }

}
