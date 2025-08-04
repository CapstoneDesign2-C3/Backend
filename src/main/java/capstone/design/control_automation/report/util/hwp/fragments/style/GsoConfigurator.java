package capstone.design.control_automation.report.util.hwp.fragments.style;

import capstone.design.control_automation.report.util.hwp.dto.GsoParam;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.CtrlHeaderGso;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.HeightCriterion;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.HorzRelTo;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.ObjectNumberSort;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.RelativeArrange;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.TextFlowMethod;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.TextHorzArrange;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.VertRelTo;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.WidthCriterion;
import org.springframework.stereotype.Component;

@Component
public class GsoConfigurator {

    public void configureHeaderGso(CtrlHeaderGso headerGso, GsoParam gsoParam) {
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
        headerGso.getProperty().setTextFlowMethod(TextFlowMethod.TakePlace);
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
        headerGso.setOutterMarginBottom(gsoParam.bottomMargin());
    }

    private long mmToHwp(double mm) {
        return (long) (mm * 72000.0f / 254.0f + 0.5f);
    }

}
