package capstone.design.control_automation.report.util.hwp;

import java.nio.charset.StandardCharsets;
import kr.dogfoot.hwplib.object.bodytext.control.ControlColumnDefine;
import kr.dogfoot.hwplib.object.bodytext.control.ControlType;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.CtrlHeaderColumnDefine;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.columndefine.ColumnInfo;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.HWPCharControlExtend;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.ParaText;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BorderThickness;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BorderType;
import org.springframework.stereotype.Component;

@Component
public class HwpColumnMaker {

    private static final double DEFAULT_COLUMN_GAP = 10;

    public void configureColumn(Paragraph paragraph, Double... widths) throws Exception {
        addColumnControl(paragraph);
        configureColumnInfo(paragraph, widths);
    }

    public void mergeToOneColumn(Paragraph paragraph) throws Exception {
        paragraph.getHeader().getDivideSort().setDividePage(true);
        addColumnControl(paragraph);

        ControlColumnDefine columnDefine = (ControlColumnDefine) paragraph.addNewControl(ControlType.ColumnDefine);
        CtrlHeaderColumnDefine columnDefineHeader = columnDefine.getHeader();
        configureOneColumn(columnDefineHeader);
    }

    private void configureOneColumn(CtrlHeaderColumnDefine columnDefineHeader) {
        columnDefineHeader.setProperty2(32768);
        columnDefineHeader.getProperty().setColumnCount((short) 1);
        columnDefineHeader.getDivideLine().setType(BorderType.None);
        columnDefineHeader.getDivideLine().getColor().setValue(0);
        columnDefineHeader.getDivideLine().setThickness(BorderThickness.MM0_1);
    }

    private void addColumnControl(Paragraph paragraph) throws Exception {
        ParaText paraText = paragraph.getText();
        HWPCharControlExtend columnControlExtend = paraText.addNewExtendControlChar();
        columnControlExtend.setCode(2);
        byte[] arr = new byte[12];
        String str = "dloc";
        System.arraycopy(str.getBytes(StandardCharsets.US_ASCII), 0, arr, 0, str.length());
        columnControlExtend.setAddition(arr);
        paraText.addNewCharControlChar().setCode(13); // 문단 끝 셋팅 (text 에 아무것도 안넣으면 에러 발생하는 경우를 막기 위해)
    }

    private void configureColumnInfo(Paragraph paragraph, Double... widths) {
        ControlColumnDefine controlColumnDefine = (ControlColumnDefine) paragraph.addNewControl(ControlType.ColumnDefine);
        CtrlHeaderColumnDefine header = controlColumnDefine.getHeader();
        header.getProperty().setValue(8);
        header.getDivideLine().setType(BorderType.None);
        header.getDivideLine().getColor().setValue(0);
        header.getDivideLine().setThickness(BorderThickness.MM0_1);
        double hwpWidth = 150;
        for (int i = 0; i < widths.length; i++) {
            if (i == widths.length - 1 && hwpWidth <= widths[i] + DEFAULT_COLUMN_GAP) {
                ColumnInfo columnInfo = header.addNewColumnInfo();
                columnInfo.setWidth(mmToHwp(hwpWidth));
                break;
            }
            ColumnInfo columnInfo = header.addNewColumnInfo();
            columnInfo.setWidth(mmToHwp(widths[i]));
            columnInfo.setGap(mmToHwp(DEFAULT_COLUMN_GAP));
            hwpWidth -= widths[i] + DEFAULT_COLUMN_GAP;
        }
    }

    private int mmToHwp(double mm) {
        double weight = 10f / 13f;
        return (int) (mm * 72000.0f * weight / 254.0f + 0.5f);
    }
}
