package capstone.design.control_automation.report.util;

import static org.assertj.core.api.Assertions.fail;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.control.Control;
import kr.dogfoot.hwplib.object.bodytext.control.ControlColumnDefine;
import kr.dogfoot.hwplib.object.bodytext.control.ControlType;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.CtrlHeaderColumnDefine;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.CtrlHeaderGso;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.columndefine.ColumnInfo;
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
import kr.dogfoot.hwplib.object.bodytext.paragraph.charshape.CharPositionShapeIdPair;
import kr.dogfoot.hwplib.object.bodytext.paragraph.charshape.ParaCharShape;
import kr.dogfoot.hwplib.object.bodytext.paragraph.lineseg.LineSegItem;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.HWPCharControlChar;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.HWPCharControlExtend;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.ParaText;
import kr.dogfoot.hwplib.object.docinfo.BinData;
import kr.dogfoot.hwplib.object.docinfo.CharShape;
import kr.dogfoot.hwplib.object.docinfo.ParaShape;
import kr.dogfoot.hwplib.object.docinfo.bindata.BinDataCompress;
import kr.dogfoot.hwplib.object.docinfo.bindata.BinDataState;
import kr.dogfoot.hwplib.object.docinfo.bindata.BinDataType;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BorderThickness;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BorderType;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.FillInfo;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.ImageFill;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.ImageFillType;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.PictureEffect;
import kr.dogfoot.hwplib.object.docinfo.parashape.Alignment;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwplib.writer.HWPWriter;
import org.junit.jupiter.api.Test;

class ReportCreateTest {

    private final Set<String> visited = new HashSet<>();

    /*  Section : 쪽
        Paragraph : 문단
        ParaShape : 문단
     */
    @Test
    void configureParaShapesTest() throws Exception {
        HWPFile hwpFile = BlankFileMaker.make();
        ArrayList<ParaShape> paraShapeList = hwpFile.getDocInfo().getParaShapeList();
        System.out.println("paraShapeList = " + paraShapeList);

        Section section = hwpFile.getBodyText().getSectionList().get(0);
        Paragraph paragraph = section.getParagraph(0);
        System.out.println("currentParaShapeId = " + paragraph.getHeader().getParaShapeId());

        ParaShape changedParaShape = paraShapeList.get(3).clone();
        changedParaShape.getProperty1().setAlignment(Alignment.Center);
        paraShapeList.add(changedParaShape);

        paragraph.getHeader().setParaShapeId(12);
        paragraph.getText().addString("hello");

        HWPWriter.toFile(hwpFile, "C:/Users/Suhyeon/Desktop/hwpTest/para_shape_test.hwp");
    }

    @Test
    void configureCharShapesTest() throws Exception {
        HWPFile hwpFile = BlankFileMaker.make();
        ArrayList<CharShape> charShapeList = hwpFile.getDocInfo().getCharShapeList();
        System.out.println("charShapeList = " + charShapeList);

        Paragraph paragraph = hwpFile.getBodyText().getSectionList().get(0).getParagraph(0);
        int charShapeCount = paragraph.getHeader().getCharShapeCount();
        System.out.println("charShapeCount = " + charShapeCount);

        ParaCharShape paraCharShape = paragraph.getCharShape();
        ArrayList<CharPositionShapeIdPair> positionShapeIdPairList = paraCharShape.getPositonShapeIdPairList();
        CharPositionShapeIdPair charPositionShapeIdPair = positionShapeIdPairList.get(0);
        System.out.println("charPositionShapeIdPair.getShapeId = " + charPositionShapeIdPair.getShapeId());
        System.out.println("charPositionShapeIdPair.getPosition = " + charPositionShapeIdPair.getPosition());
        paragraph.getText().addString("객체 이동 보고서");

        CharShape changedCharShape = charShapeList.get(0).clone();
        changedCharShape.setBaseSize(3000);
        changedCharShape.getProperty().setBold(true);
        charShapeList.add(changedCharShape);

        ArrayList<LineSegItem> lineSegItemList = paragraph.getLineSeg().getLineSegItemList();
        System.out.println("lineSegItemList = " + lineSegItemList);
        LineSegItem lineSegItem = lineSegItemList.get(0);
        lineSegItem.setTextPartHeight(3000);
        lineSegItem.setDistanceBaseLineToLineVerticalPosition(3000);

        paraCharShape.addParaCharShape(0, 5); // 왜인지 모르겠지만 시작 위치가 16부터 인것 같다
        HWPWriter.toFile(hwpFile, "C:/Users/Suhyeon/Desktop/hwpTest/char_shape_test.hwp");
    }

    @Test
    void writeTwoLineTest() throws Exception {
        HWPFile hwpFile = BlankFileMaker.make();
        Section section = hwpFile.getBodyText().getSectionList().get(0);
        Paragraph paragraph = section.getParagraph(0);
        System.out.println("currentParaShapeId = " + paragraph.getHeader().getParaShapeId());

        paragraph.getText().addString("hello");

        Paragraph paragraph1 = section.addNewParagraph();

        paragraph1.createCharShape();
        paragraph1.createText();

        paragraph1.getText().addString("world");
        HWPWriter.toFile(hwpFile, "C:/Users/Suhyeon/Desktop/hwpTest/two_line_test.hwp");
    }

    @Test
    void configureColumnTest() throws Exception {
        HWPFile hwpFile2 = BlankFileMaker.make();

        Paragraph paragraph = hwpFile2.getBodyText().getSectionList().get(0)
            .addNewParagraph();
        paragraph.createCharShape();
        paragraph.createLineSeg();
        paragraph.getLineSeg().addNewLineSegItem();
        paragraph.createText();

        ParaText paraText2 = paragraph.getText();
        HWPCharControlExtend hwpChar = paraText2.addNewExtendControlChar();
        HWPCharControlChar hwpCharControlChar = paraText2.addNewCharControlChar();
        hwpCharControlChar.setCode(13);
        hwpChar.setCode(2);
        byte[] arr = new byte[12];
        String str = "dloc";
        System.arraycopy(str.getBytes(StandardCharsets.US_ASCII), 0, arr, 0, str.length());
        hwpChar.setAddition(arr);
        paraText2.addString("a"); // 이것도 해야함.
        System.out.println(paraText2.getCharList().size());

        paragraph.addNewControl(ControlType.ColumnDefine);
        // 단 세팅
        ControlColumnDefine controlColumnDefine2 = (ControlColumnDefine) paragraph.getControlList().get(0);
        controlColumnDefine2.getHeader().getProperty().setValue(8);
//        controlColumnDefine2.getHeader().setProperty2(2268);
        ColumnInfo columnInfo1 = controlColumnDefine2.getHeader().addNewColumnInfo();
        ColumnInfo columnInfo2 = controlColumnDefine2.getHeader().addNewColumnInfo();
//        columnInfo1.setWidth(5000); // 10338
//        columnInfo1.setGap(1746);
//        columnInfo2.setWidth(5000); // 20684
//        columnInfo2.setGap(1746);
        columnInfo1.setWidth(mmToHwp(40));
        columnInfo1.setGap(mmToHwp(10));
        columnInfo2.setWidth(mmToHwp(100));

        CtrlHeaderColumnDefine header2 = controlColumnDefine2.getHeader();

        header2.getDivideLine().setThickness(BorderThickness.MM0_1);
        header2.getDivideLine().getColor().setValue(0);
        header2.getDivideLine().setType(BorderType.None);

        HWPWriter.toFile(hwpFile2, "./hwptest/column_test2.hwp");
    }

    private int mmToHwp(double mm) {
        double weight = 10f / 13f;
        return (int) (mm * 72000.0f * weight / 254.0f + 0.5f);
    }

    @Test
    void reverse() throws Exception {
        HWPFile hwpFile = HWPReader.fromFile("./hwptest/report_sample.hwp");

        Section section = hwpFile.getBodyText().getSectionList().get(0);
        Paragraph[] paragraphs = section.getParagraphs();

//        HWPFile hwpFile1 = BlankFileMaker.make();
//        compareObjects(hwpFile1.getBodyText().getSectionList().get(0).getParagraph(0).getLineSeg(), paragraphs[1].getLineSeg(), "seg");
        compareObjects(paragraphs[0], paragraphs[5], "title");
//        compareObjects(paragraphs[1], paragraphs[6], "publishInfo");
//        compareObjects(paragraphs[2], paragraphs[7], "map");
//        compareObjects(paragraphs[3], paragraphs[8], "leftColumn");
//        compareObjects(paragraphs[4], paragraphs[9], "rightColumn");

        fail();
    }

    private void compareObjects(Object o1, Object o2, String path) throws IllegalAccessException {
        if (o1 == null && o2 == null) {
            return;
        }

        if (o1 == null || o2 == null) {
            System.out.printf("[DIFF] %s → %s vs %s\n", path, format(o1), format(o2));
            return;
        }

        Class<?> clazz = o1.getClass();
        if (!clazz.isInstance(o2)) {
            System.out.printf("[DIFF] %s -> %s vs %s\n", path, o1.getClass(), o2.getClass());
            return;
        }

        // 리스트인 경우
        if (o1 instanceof List<?> list1 && o2 instanceof List<?> list2) {
            int max = Math.max(list1.size(), list2.size());
            for (int i = 0; i < max; i++) {
                Object v1 = i < list1.size() ? list1.get(i) : null;
                Object v2 = i < list2.size() ? list2.get(i) : null;
                compareObjects(v1, v2, path + "[" + i + "]");
            }
            return;
        }

        // 기본형 or equals 비교로 끝낼 수 있는 타입
        if (isLeafType(clazz)) {
            if (!Objects.equals(o1, o2)) {
                System.out.printf("[DIFF] %s → %s vs %s\n", path, format(o1), format(o2));
            }
            return;
        }

        // 무한루프 방지용
        if (!visited.add(System.identityHashCode(o1) + "|" + System.identityHashCode(o2))) {
            return;
        }

        // 필드 순회
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object f1 = field.get(o1);
                Object f2 = field.get(o2);
                compareObjects(f1, f2, path + "." + field.getName());
            }
            clazz = clazz.getSuperclass();
        }
    }

    private boolean isLeafType(Class<?> clazz) {
        return clazz.isPrimitive() ||
            clazz == String.class ||
            Number.class.isAssignableFrom(clazz) ||
            Boolean.class.isAssignableFrom(clazz) ||
            Enum.class.isAssignableFrom(clazz) ||
            clazz.getPackageName().startsWith("java.");
    }


    private String format(Object obj) {
        if (obj == null) {
            return "null";
        }
        return obj.toString();
    }
    /*
        GSO 는 그리기 도구
     */
    @Test
    void insertRectangleTest() throws Exception {

        HWPFile hwpFile = BlankFileMaker.make();
        Section section = hwpFile.getBodyText().getSectionList().get(0);
        Paragraph paragraph = section.getParagraph(0);

        byte[] fileBinary = loadFile();

        int streamIndex = hwpFile.getBinData().getEmbeddedBinaryDataList().size() + 1;
        String streamName = "Bin" + String.format("%04X", streamIndex) + ".jpg";
        hwpFile.getBinData().addNewEmbeddedBinaryData(streamName, fileBinary, BinDataCompress.ByStorageDefault);

        BinData bd = new BinData();
        bd.getProperty().setType(BinDataType.Embedding);
        bd.getProperty().setCompress(BinDataCompress.ByStorageDefault);
        bd.getProperty().setState(BinDataState.NotAccess);
        bd.setBinDataID(streamIndex);
        bd.setExtensionForEmbedding("jpg");
        hwpFile.getDocInfo().getBinDataList().add(bd);

        Rectangle shapePosition = new Rectangle(0, 0, 150, 100);

        paragraph.getText().addExtendCharForGSO();
        ControlRectangle controlRectangle = (ControlRectangle) paragraph.addNewGsoControl(GsoControlType.Rectangle);

        CtrlHeaderGso headerGso = (CtrlHeaderGso) controlRectangle.getHeader();
        GsoHeaderProperty headerProperty = headerGso.getProperty();
        configureGsoHeaderProperty(headerProperty);
        configureGsoHeader(headerGso, shapePosition);

        ShapeComponentNormal shapeComponent = (ShapeComponentNormal) controlRectangle.getShapeComponent();
        configureShapeComponent(shapeComponent, shapePosition);
        configureLineInfo(shapeComponent);
        configureFillInfo(shapeComponent, streamIndex);
        configureShadowInfo(shapeComponent);
        shapeComponent.setMatrixsNormal();

        configureShapeComponentRectangle(controlRectangle, shapePosition);

        HWPWriter.toFile(hwpFile, "C:/Users/Suhyeon/Desktop/hwpTest/image_insert_test.hwp");
    }

    private byte[] loadFile() throws IOException {
        File file = new File("C:/Users/Suhyeon/Desktop/hwpTest/image.png");
        byte[] buffer = new byte[(int) file.length()];
        InputStream ios = null;
        try {
            ios = new FileInputStream(file);
            ios.read(buffer);
        } finally {
            try {
                if (ios != null) {
                    ios.close();
                }
            } catch (IOException e) {
            }
        }
        return buffer;
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

    private static void configureShadowInfo(ShapeComponentNormal shapeComponent) {
        shapeComponent.createShadowInfo();
        ShadowInfo si = shapeComponent.getShadowInfo();
        si.setType(ShadowType.None);
        si.getColor().setValue(0xc4c4c4);
        si.setOffsetX(283);
        si.setOffsetY(283);
        si.setTransparent((short) 0);
    }

    private void configureFillInfo(ShapeComponentNormal shapeComponent, int binDataID) {
        shapeComponent.createFillInfo();
        FillInfo fi = shapeComponent.getFillInfo();
        fi.getType().setPatternFill(false);
        fi.getType().setImageFill(true);
        fi.getType().setGradientFill(false);

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

    private int fromMM(int mm) {
        if (mm == 0) {
            return 1;
        }

        return (int) ((double) mm * 72000.0f / 254.0f + 0.5f);
    }
}
