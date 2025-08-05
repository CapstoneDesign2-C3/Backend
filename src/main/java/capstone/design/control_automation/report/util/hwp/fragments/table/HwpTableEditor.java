package capstone.design.control_automation.report.util.hwp.fragments.table;

import capstone.design.control_automation.report.util.hwp.dto.GsoParam;
import capstone.design.control_automation.report.util.hwp.dto.TableType;
import capstone.design.control_automation.report.util.hwp.fragments.HwpConfigurer;
import capstone.design.control_automation.report.util.hwp.fragments.style.BorderFillStyler;
import capstone.design.control_automation.report.util.hwp.fragments.style.GsoConfigurator;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kr.dogfoot.hwplib.object.bodytext.control.ControlTable;
import kr.dogfoot.hwplib.object.bodytext.control.ControlType;
import kr.dogfoot.hwplib.object.bodytext.control.table.Cell;
import kr.dogfoot.hwplib.object.bodytext.control.table.Row;
import kr.dogfoot.hwplib.object.bodytext.control.table.Table;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.docinfo.DocInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HwpTableEditor {

    private final HwpConfigurer configurer;
    private final BorderFillStyler borderFillStyler;
    private final GsoConfigurator gsoConfigurator;
    private final HwpTableConfigurator hwpTableConfigurator;
    private final Map<String, HwpTableDataExtractor> tableDataExtractors;

    public int addBorderFillInfo(DocInfo docInfo) {
        borderFillStyler.addBorderFillInfo(docInfo);
        return docInfo.getBorderFillList().size();
    }

    public <T> void writeTable(
        Paragraph paragraph,
        List<T> dataToWrite,
        GsoParam gsoParam,
        Integer borderFillId,
        TableType tableType,
        boolean appendHeader
    ) throws UnsupportedEncodingException, IllegalAccessException {
        paragraph.getText().addExtendCharForTable();

        ControlTable controlTable = (ControlTable) paragraph.addNewControl(ControlType.Table);
        gsoConfigurator.configureHeaderGso(controlTable.getHeader(), gsoParam);

        Table table = controlTable.getTable();
        hwpTableConfigurator.configureTable(table, borderFillId, gsoParam);

        HwpTableDataExtractor hwpTableDataExtractor = getHwpTableDataExtractor(tableType);

        List<List<String>> tableData = hwpTableDataExtractor.convertToTableData(dataToWrite, appendHeader);

        hwpTableConfigurator.makeTableCells(table, controlTable, tableData.size(), tableData.get(0).size(), borderFillId);
        writeDataInTable(controlTable, tableData, gsoParam.width());
    }

    private HwpTableDataExtractor getHwpTableDataExtractor(TableType tableType) {
        if (tableType.equals(TableType.Horizontal))
            return tableDataExtractors.get("horizontalTableDataExtractor");
        if (tableType.equals(TableType.Division))
            return tableDataExtractors.get("divisionTableDataExtractor");
        return tableDataExtractors.get("verticalTableDataExtractor");
    }

    private void writeDataInTable(ControlTable controlTable, List<List<String>> tableData, int maxWidth) throws UnsupportedEncodingException {
        ArrayList<Row> rows = controlTable.getRowList();
        List<Long> cellSizes = hwpTableConfigurator.calculateCellSize(tableData, maxWidth);
        for (int i = 0; i < rows.size(); i++) {
            Row row = rows.get(i);

            ArrayList<Cell> cells = row.getCellList();
            for (int j = 0; j < cells.size(); j++) {
                Cell cell = cells.get(j);
                String text = tableData.get(i).get(j);
                hwpTableConfigurator.configureCellSize(cell, cellSizes.get(j));

                Paragraph paragraph = hwpTableConfigurator.createParagraphForCell(cell);
                configurer.configureParagraph(paragraph, "tdata");
                paragraph.getText().addString(text);
            }
        }
    }

}
