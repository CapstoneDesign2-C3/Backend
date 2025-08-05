package capstone.design.control_automation.report.util.hwp.fragments.table;

import capstone.design.control_automation.report.util.hwp.dto.GsoParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kr.dogfoot.hwplib.object.bodytext.control.ControlTable;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.sectiondefine.TextDirection;
import kr.dogfoot.hwplib.object.bodytext.control.gso.textbox.LineChange;
import kr.dogfoot.hwplib.object.bodytext.control.gso.textbox.TextVerticalAlignment;
import kr.dogfoot.hwplib.object.bodytext.control.table.Cell;
import kr.dogfoot.hwplib.object.bodytext.control.table.DivideAtPageBoundary;
import kr.dogfoot.hwplib.object.bodytext.control.table.ListHeaderForCell;
import kr.dogfoot.hwplib.object.bodytext.control.table.Row;
import kr.dogfoot.hwplib.object.bodytext.control.table.Table;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import org.springframework.stereotype.Component;

@Component
public class HwpTableConfigurator {

    // table record 지정(row, col 개수 포함)
    public void configureTable(Table table, int borderFillId, GsoParam gsoParam) {
        table.getProperty().setDivideAtPageBoundary(DivideAtPageBoundary.DivideByCell);
        table.getProperty().setAutoRepeatTitleRow(false);
        table.setCellSpacing(0);
        table.setLeftInnerMargin(0);
        table.setRightInnerMargin(0);
        table.setTopInnerMargin(0);
        table.setBottomInnerMargin(gsoParam.bottomMargin());
        table.setBorderFillId(borderFillId);
    }

    public void makeTableCells(Table table, ControlTable controlTable, int rowCount, int colCount, int borderFillId) {
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

    public List<Long> calculateCellSize(List<List<String>> tableData, int maxWidth) {
        double[] lengths = new double[tableData.get(0).size()];
        for (List<String> tableDatum : tableData) {
            for (int j = 0; j < tableDatum.size(); j++) {
                lengths[j] = Math.max(lengths[j], getAutoWidthByText(tableDatum.get(j)));
            }
        }

        double lengthSum = Arrays.stream(lengths).sum();
        return Arrays.stream(lengths)
            .map(length -> maxWidth * length / lengthSum)
            .mapToLong(this::mmToHwp)
            .boxed()
            .toList();
    }

    public void configureCellSize(Cell cell, Long cellSize) {
        ListHeaderForCell listHeader = cell.getListHeader();
        listHeader.setWidth(cellSize);
        listHeader.setTextWidth(cellSize);
    }

    public Paragraph createParagraphForCell(Cell cell) {
        Paragraph paragraph = cell.getParagraphList().addNewParagraph();
        paragraph.createCharShape();
        paragraph.createLineSeg();
        paragraph.getLineSeg().addNewLineSegItem();
        paragraph.createText();
        return paragraph;
    }

    private double getAutoWidthByText(String text) {
        int koreanCount = 0;
        int otherCount = 0;

        for (char c : text.toCharArray()) {
            if (isKorean(c)) {
                koreanCount++;
            } else {
                otherCount++;
            }
        }

        return koreanCount * 2.8 + otherCount * 1.7;
    }

    private boolean isKorean(char c) {
        return (c >= '가' && c <= '힣');
    }

    private long mmToHwp(double mm) {
        return (long) (mm * 72000.0f / 254.0f + 0.5f);
    }

}
