package capstone.design.control_automation.report.util.hwp;

import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Track;
import java.util.ArrayList;
import kr.dogfoot.hwplib.object.bodytext.control.ControlTable;
import kr.dogfoot.hwplib.object.bodytext.control.ControlType;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.CtrlHeaderGso;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.*;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.sectiondefine.TextDirection;
import kr.dogfoot.hwplib.object.bodytext.control.gso.textbox.LineChange;
import kr.dogfoot.hwplib.object.bodytext.control.gso.textbox.TextVerticalAlignment;
import kr.dogfoot.hwplib.object.bodytext.control.table.*;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.bodytext.paragraph.charshape.ParaCharShape;
import kr.dogfoot.hwplib.object.bodytext.paragraph.header.ParaHeader;
import kr.dogfoot.hwplib.object.bodytext.paragraph.lineseg.LineSegItem;
import kr.dogfoot.hwplib.object.docinfo.BorderFill;
import kr.dogfoot.hwplib.object.docinfo.DocInfo;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BackSlashDiagonalShape;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BorderThickness;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BorderType;
import kr.dogfoot.hwplib.object.docinfo.borderfill.SlashDiagonalShape;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.PatternFill;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.PatternType;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
public class HwpTableEditor {

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

    public void writeTable(Paragraph paragraph, List<DetectionQueryResult.Track> tracks, GsoParam gsoParam, Integer borderFillId)
        throws UnsupportedEncodingException {
        paragraph.getText().addExtendCharForTable();

        ControlTable controlTable = (ControlTable) paragraph.addNewControl(ControlType.Table);
        CtrlHeaderGso headerGso = controlTable.getHeader();
        configureHeaderGso(headerGso, gsoParam);

        Table table = controlTable.getTable();

        int rowCount = tracks.size() + 1; // table header 공간 + 1
        int colCount = 4; // table column 개수
        configureTable(table, borderFillId);
        makeTableCells(table, controlTable, rowCount, colCount, borderFillId);
        writeObjectsInCell(controlTable, tracks);
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

    public void writeObjectsInCell(ControlTable controlTable, List<DetectionQueryResult.Track> tracks)
        throws UnsupportedEncodingException {
        ArrayList<Row> rows = controlTable.getRowList();
        for (int rowIdx = 0; rowIdx < rows.size(); rowIdx++) {
            Row row = rows.get(rowIdx);
            List<String> data = List.of("경로 번호", "출현 장소", "출현 시간", "퇴장 시간");

            if (rowIdx != 0) {
                Track track = tracks.get(rowIdx - 1);
                data = List.of(
                    String.valueOf(rowIdx),
                    track.cameraScenery(),
                    track.appearedTime().toString(),
                    track.exitTime().toString()
                );
            }

            ArrayList<Cell> cells = row.getCellList();
            for(int colIdx = 0; colIdx < cells.size(); colIdx++) {
                Cell cell = cells.get(colIdx);
                String text = data.get(colIdx);
                configureCellSize(cell, mmToHwp(getAutoWidthByText(text)));

                Paragraph paragraph = createParagraphForCell(cell);
                paragraph.getText().addString(text);
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
        configureParaHeader(paragraph);
        paragraph.createText();
        configureCharShape(paragraph);
        configureLineSegItem(paragraph);
        return paragraph;
    }

    // 셀 문단의 스타일 관리
    private void configureParaHeader(Paragraph p) {
        ParaHeader ph = p.getHeader();
        ph.setLastInList(true);
        ph.setParaShapeId(1);
        ph.setStyleId((short) 1);
        ph.getDivideSort().setDivideSection(false);
        ph.getDivideSort().setDivideMultiColumn(false);
        ph.getDivideSort().setDividePage(false);
        ph.getDivideSort().setDivideColumn(false);
        ph.setCharShapeCount(1);
        ph.setRangeTagCount(0);
        ph.setLineAlignCount(1);
        ph.setInstanceID(0);
        ph.setIsMergedByTrack(0);
    }

    // paragraph의 스타일 지정
    private void configureCharShape(Paragraph p) {
        p.createCharShape();

        ParaCharShape pcs = p.getCharShape();
        // 셀의 글자 모양을 이미 만들어진 글자 모양으로 사용함
        pcs.addParaCharShape(0, 1);
    }

    private void configureLineSegItem(Paragraph p) {
        p.createLineSeg();

        LineSegItem lsi = p.getLineSeg().addNewLineSegItem();
        lsi.setTextStartPosition(0);
        lsi.setLineVerticalPosition(0);
        lsi.setLineHeight(ptToLineHeight(10.0));
        lsi.setTextPartHeight(ptToLineHeight(10.0));
        lsi.setDistanceBaseLineToLineVerticalPosition(ptToLineHeight(10.0 * 0.85));
        lsi.setLineSpace(ptToLineHeight(3.0));
        lsi.setStartPositionFromColumn(0);
        lsi.setSegmentWidth((int) mmToHwp(50.0));
        lsi.getTag().setFirstSegmentAtLine(true);
        lsi.getTag().setLastSegmentAtLine(true);
    }

    private int ptToLineHeight(double pt) {
        return (int) (pt * 100.0f);
    }

}
