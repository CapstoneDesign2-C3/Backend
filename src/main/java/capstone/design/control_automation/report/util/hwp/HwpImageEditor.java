package capstone.design.control_automation.report.util.hwp;

import java.awt.Rectangle;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.CtrlHeaderGso;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.GsoHeaderProperty;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.HeightCriterion;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.HorzRelTo;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.ObjectNumberSort;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.RelativeArrange;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.TextFlowMethod;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.TextHorzArrange;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.VertRelTo;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.WidthCriterion;
import kr.dogfoot.hwplib.object.bodytext.control.gso.ControlRectangle;
import kr.dogfoot.hwplib.object.bodytext.control.gso.GsoControlType;
import kr.dogfoot.hwplib.object.bodytext.control.gso.shapecomponent.ShapeComponentNormal;
import kr.dogfoot.hwplib.object.bodytext.control.gso.shapecomponent.lineinfo.LineArrowShape;
import kr.dogfoot.hwplib.object.bodytext.control.gso.shapecomponent.lineinfo.LineArrowSize;
import kr.dogfoot.hwplib.object.bodytext.control.gso.shapecomponent.lineinfo.LineEndShape;
import kr.dogfoot.hwplib.object.bodytext.control.gso.shapecomponent.lineinfo.LineInfo;
import kr.dogfoot.hwplib.object.bodytext.control.gso.shapecomponent.lineinfo.LineType;
import kr.dogfoot.hwplib.object.bodytext.control.gso.shapecomponent.lineinfo.OutlineStyle;
import kr.dogfoot.hwplib.object.bodytext.control.gso.shapecomponent.shadowinfo.ShadowInfo;
import kr.dogfoot.hwplib.object.bodytext.control.gso.shapecomponent.shadowinfo.ShadowType;
import kr.dogfoot.hwplib.object.bodytext.control.gso.shapecomponenteach.ShapeComponentRectangle;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.docinfo.BinData;
import kr.dogfoot.hwplib.object.docinfo.bindata.BinDataCompress;
import kr.dogfoot.hwplib.object.docinfo.bindata.BinDataState;
import kr.dogfoot.hwplib.object.docinfo.bindata.BinDataType;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.FillInfo;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.ImageFill;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.ImageFillType;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.PictureEffect;
import org.springframework.stereotype.Component;

@Component
public class HwpImageEditor {

    public int addBinDataToHwpFile(HWPFile hwpFile, byte[] fileBinary) {
        int binDataId = hwpFile.getBinData().getEmbeddedBinaryDataList().size() + 1;
        String streamName = "Bin" + String.format("%04X", binDataId) + ".jpg";
        hwpFile.getBinData().addNewEmbeddedBinaryData(streamName, fileBinary, BinDataCompress.ByStorageDefault);

        BinData bd = new BinData();
        bd.getProperty().setType(BinDataType.Embedding);
        bd.getProperty().setCompress(BinDataCompress.ByStorageDefault);
        bd.getProperty().setState(BinDataState.NotAccess);
        bd.setBinDataID(binDataId);
        bd.setExtensionForEmbedding("jpg");
        hwpFile.getDocInfo().getBinDataList().add(bd);

        return binDataId;
    }

    public void writeImage(Paragraph paragraph, int binDataId, GsoParam gsoParam) {
        paragraph.getText().addExtendCharForGSO();

        Rectangle rectangle = new Rectangle(
            gsoParam.posX(),
            gsoParam.posY(),
            gsoParam.width(),
            gsoParam.height()
        );

        ControlRectangle controlRectangle = (ControlRectangle) paragraph.addNewGsoControl(GsoControlType.Rectangle);

        CtrlHeaderGso headerGso = controlRectangle.getHeader();
        configureGsoHeader(headerGso, rectangle);
        GsoHeaderProperty headerProperty = headerGso.getProperty();
        configureGsoHeaderProperty(headerProperty);

        ShapeComponentNormal shapeComponent = (ShapeComponentNormal) controlRectangle.getShapeComponent();
        configureShapeComponent(shapeComponent, rectangle);
        configureLineInfo(shapeComponent);
        configureFillInfo(shapeComponent, binDataId);
        configureShadowInfo(shapeComponent);

        configureShapeComponentRectangle(controlRectangle, rectangle);
    }

    private void configureShapeComponent(ShapeComponentNormal sc, Rectangle shapePosition) {
//        sc.getProperty().setRotateWithImage(true);
        sc.setOffsetX(0);
        sc.setOffsetY(0);
        sc.setGroupingCount(0);
        sc.setLocalFileVersion(1);
        sc.setWidthAtCreate(fromMM(shapePosition.width));
        sc.setHeightAtCreate(fromMM(shapePosition.height));
        sc.setWidthAtCurrent(fromMM(shapePosition.width));
        sc.setHeightAtCurrent(fromMM(shapePosition.height));
        sc.setRotateAngle(0);
        sc.setRotateXCenter(fromMM(shapePosition.width / 2));
        sc.setRotateYCenter(fromMM(shapePosition.height / 2));
        sc.setMatrixsNormal();
    }

    private void configureGsoHeader(CtrlHeaderGso hdr, Rectangle shapePosition) {
        hdr.setyOffset(fromMM(shapePosition.y));
        hdr.setxOffset(fromMM(shapePosition.x));
        hdr.setWidth(fromMM(shapePosition.width));
        hdr.setHeight(fromMM(shapePosition.height));
        hdr.setzOrder(0);
        hdr.setOutterMarginLeft(0);
        hdr.setOutterMarginRight(0);
        hdr.setOutterMarginTop(0);
        hdr.setOutterMarginBottom(0);
        hdr.setInstanceId(0x5bb840e1);
        hdr.setPreventPageDivide(false);
        hdr.getExplanation().setBytes(null);
    }

    private void configureGsoHeaderProperty(GsoHeaderProperty prop) {
        prop.setLikeWord(false);
        prop.setApplyLineSpace(false);
        prop.setVertRelTo(VertRelTo.Para);
        prop.setVertRelativeArrange(RelativeArrange.TopOrLeft);
        prop.setHorzRelTo(HorzRelTo.Para);
        prop.setHorzRelativeArrange(RelativeArrange.TopOrLeft);
        prop.setVertRelToParaLimit(true);
        prop.setAllowOverlap(false);
        prop.setWidthCriterion(WidthCriterion.Absolute);
        prop.setHeightCriterion(HeightCriterion.Absolute);
        prop.setProtectSize(false);
        prop.setTextFlowMethod(TextFlowMethod.FitWithText);
        prop.setTextHorzArrange(TextHorzArrange.BothSides);
        prop.setObjectNumberSort(ObjectNumberSort.Figure);
    }

    private void configureFillInfo(ShapeComponentNormal shapeComponent, int binDataID) {
        shapeComponent.createFillInfo();
        FillInfo fi = shapeComponent.getFillInfo();
        fi.getType().setPatternFill(false);
        fi.getType().setImageFill(true);
        fi.getType().setGradientFill(false);
//        fi.createPatternFill();
//        PatternFill patternFill = fi.getPatternFill();
//        patternFill.setPatternType(PatternType.None);
//
//        patternFill.getPatternColor().setValue(0x000000);
//        patternFill.getBackColor().setValue(0x000000);
        fi.createImageFill();
        ImageFill imgF = fi.getImageFill();
        imgF.setImageFillType(ImageFillType.FitSize);
        imgF.getPictureInfo().setBrightness((byte) 0);
        imgF.getPictureInfo().setContrast((byte) 0);
        imgF.getPictureInfo().setEffect(PictureEffect.RealPicture);
        imgF.getPictureInfo().setBinItemID(binDataID);
    }

    private void configureLineInfo(ShapeComponentNormal shapeComponent) {
        shapeComponent.createLineInfo();
        LineInfo li = shapeComponent.getLineInfo();
        li.getProperty().setLineEndShape(LineEndShape.Flat);
        li.getProperty().setStartArrowShape(LineArrowShape.None);
        li.getProperty().setStartArrowSize(LineArrowSize.MiddleMiddle);
        li.getProperty().setEndArrowShape(LineArrowShape.None);
        li.getProperty().setEndArrowSize(LineArrowSize.MiddleMiddle);
        li.getProperty().setFillStartArrow(true);
        li.getProperty().setFillEndArrow(true);
        li.getProperty().setLineType(LineType.None);
        li.setOutlineStyle(OutlineStyle.Normal);
        li.setThickness(0);
        li.getColor().setValue(0);
    }

    private static void configureShadowInfo(ShapeComponentNormal shapeComponent) {
        shapeComponent.createShadowInfo();
        ShadowInfo si = shapeComponent.getShadowInfo();
        si.setType(ShadowType.None);
        si.getColor().setValue(0xc4c4c4);
        si.setOffsetX(283);
        si.setOffsetY(283);
        si.setTransparent((short) 0);
    }

    private void configureShapeComponentRectangle(ControlRectangle controlRectangle, Rectangle shapePosition) {
        ShapeComponentRectangle scr = controlRectangle.getShapeComponentRectangle();
        scr.setRoundRate((byte) 0);
        scr.setX1(0);
        scr.setY1(0);
        scr.setX2(fromMM(shapePosition.width));
        scr.setY2(0);
        scr.setX3(fromMM(shapePosition.width));
        scr.setY3(fromMM(shapePosition.height));
        scr.setX4(0);
        scr.setY4(fromMM(shapePosition.height));
    }

    private int fromMM(int mm) {
        if (mm == 0) {
            return 1;
        }

        return (int) ((double) mm * 72000.0f / 254.0f + 0.5f);
    }

}
