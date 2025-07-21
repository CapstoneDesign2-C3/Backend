package capstone.design.control_automation.report.util.hwp;

import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.control.Control;
import kr.dogfoot.hwplib.object.bodytext.control.ControlColumnDefine;
import kr.dogfoot.hwplib.object.bodytext.control.ControlType;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.CtrlHeaderColumnDefine;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.columndefine.ColumnDefineHeaderProperty;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.columndefine.ColumnDirection;

import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.columndefine.ColumnSort;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.bodytext.paragraph.header.ParaHeader;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.HWPChar;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.HWPCharControlExtend;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.ParaText;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BorderThickness;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BorderType;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
public class ColumnProvider {
    private final HwpConfigurer configurer;

    public HWPFile test() throws UnsupportedEncodingException {
        HWPFile hwpFile = BlankFileMaker.make();
        configurer.configureHWPFile(hwpFile);
        Section section = hwpFile.getBodyText().getSectionList().get(0);
        Paragraph column = section.getParagraph(0);

        configurer.configureParagraph(column, "column");
        ControlColumnDefine columnDefine = (ControlColumnDefine) column.addNewControl(ControlType.ColumnDefine);

        setHeaderColumnDefine(columnDefine.getHeader());
        setColumnDefineHeaderProperty(columnDefine.getHeader().getProperty());
        setParaHeader(column.getHeader());

        ParaText pt = column.getText();
        for (int i = 0; i < pt.getCharList().size(); i++) {
            HWPChar c = pt.getCharList().get(i);
            System.out.printf("[%d] %s\n", i, c.getClass().getSimpleName());

            if (c instanceof HWPCharControlExtend) {
                HWPCharControlExtend ext = (HWPCharControlExtend) c;
                System.out.printf(" -> code : 0x%04X\n", ext.getCode());
                try {
                    byte[] addition = ext.getAddition();
                    String asString = new String(addition).trim();
                    System.out.printf(" -> addition : '%s'\n", asString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return hwpFile;
    }

    public void reversing() throws Exception {
        HWPFile hwpFile = HWPReader.fromFile("C:/Users/dn060/capstone2/Backend/hwptest/column_added.hwp");

        Section section = hwpFile.getBodyText().getSectionList().get(0);
        System.out.println("number of sections : " + hwpFile.getBodyText().getSectionList().size());
        Paragraph column = section.getParagraph(0);
        System.out.println("number of paragraphs : " + section.getParagraphCount());
        ParaText paraText = column.getText();
        System.out.println("charList length : " + paraText.getCharList().size());
        ControlColumnDefine columnDefine = new ControlColumnDefine();

        for (int i = 0; i < paraText.getCharList().size(); i++) {
            HWPChar c = paraText.getCharList().get(i);
            System.out.printf("[%d] %s\n", i, c.getClass().getSimpleName());

            if (c instanceof HWPCharControlExtend) {
                HWPCharControlExtend ext = (HWPCharControlExtend) c;
                System.out.printf(" -> code : 0x%04X\n", ext.getCode());
                try {
                    byte[] addition = ext.getAddition();
                    String asString = new String(addition).trim();
                    System.out.printf(" -> addition : '%s'\n", asString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        for(Control control: column.getControlList()){
            if(control.getClass() == ControlColumnDefine.class){
                columnDefine = (ControlColumnDefine) control;
            }
        }

        CtrlHeaderColumnDefine header = columnDefine.getHeader();
        System.out.println("getDivideLineColor : " + header.getDivideLineColor().getValue());
        System.out.println("getColumnInfoList : " + header.getColumnInfoList());
        System.out.printf("getProperty : %b\n", header.getProperty().getValue());
        System.out.println("getDivideLineSort : " + header.getDivideLineSort());
        System.out.println("getProperty2 : " + header.getProperty2());
        System.out.println("getDivideLineThickness : " + header.getDivideLineThickness());
        System.out.println("getGapBetweenColumn : " + header.getGapBetweenColumn());
        ColumnDefineHeaderProperty columnDefineHeaderProperty = header.getProperty();
        System.out.println();
        System.out.println("getColumnCount : " + columnDefineHeaderProperty.getColumnCount());
        System.out.println("getColumnDirection : " + columnDefineHeaderProperty.getColumnDirection());
        System.out.println("getColumnSort : " + columnDefineHeaderProperty.getColumnSort());

        ParaHeader paraHeader = column.getHeader();
        System.out.println("isLastInList : " + paraHeader.isLastInList());
        System.out.println("hasSectColDef : " + paraHeader.getControlMask().hasSectColDef());
        System.out.println("getCharShapeCount : " + paraHeader.getCharShapeCount());
        System.out.println("getRangeTagCount : " + paraHeader.getRangeTagCount());
        System.out.println("setLineAlignCount : " + paraHeader.getLineAlignCount());
        System.out.println("getInstanceID : " + paraHeader.getInstanceID());
        System.out.println("getIsMergedByTrack : " + paraHeader.getIsMergedByTrack());
    }

    private void setHeaderColumnDefine(CtrlHeaderColumnDefine headerColumnDefine){
        headerColumnDefine.getDivideLineColor().setValue(0);
        headerColumnDefine.setDivideLineSort(BorderType.None);
        headerColumnDefine.setDivideLineThickness(BorderThickness.MM0_1);
        headerColumnDefine.setGapBetweenColumn(2268);
    }

    private void setColumnDefineHeaderProperty(ColumnDefineHeaderProperty columnDefineHeaderProperty){
        columnDefineHeaderProperty.setColumnSort(ColumnSort.Normal);
        columnDefineHeaderProperty.setColumnCount((short) 2);
        columnDefineHeaderProperty.setColumnDirection(ColumnDirection.FromLeft);
        columnDefineHeaderProperty.setSameWidth(true);
    }

    private void setParaHeader(ParaHeader paraHeader){
        paraHeader.setLastInList(true);
        paraHeader.getControlMask().setHasSectColDef(true);
        paraHeader.getDivideSort().setDivideSection(true);
        paraHeader.getDivideSort().setDivideMultiColumn(true);
        paraHeader.getDivideSort().setDividePage(false);
        paraHeader.getDivideSort().setDivideColumn(false);
        paraHeader.setCharShapeCount(1);
        paraHeader.setRangeTagCount(0);
        paraHeader.setLineAlignCount(60);
        paraHeader.setInstanceID(0);
        paraHeader.setIsMergedByTrack(0);
    }
}
