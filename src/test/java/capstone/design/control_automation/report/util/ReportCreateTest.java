package capstone.design.control_automation.report.util;

import java.util.ArrayList;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.bodytext.paragraph.charshape.CharPositionShapeIdPair;
import kr.dogfoot.hwplib.object.bodytext.paragraph.charshape.ParaCharShape;
import kr.dogfoot.hwplib.object.bodytext.paragraph.lineseg.LineSegItem;
import kr.dogfoot.hwplib.object.bodytext.paragraph.lineseg.ParaLineSeg;
import kr.dogfoot.hwplib.object.docinfo.CharShape;
import kr.dogfoot.hwplib.object.docinfo.DocInfo;
import kr.dogfoot.hwplib.object.docinfo.ParaShape;
import kr.dogfoot.hwplib.object.docinfo.parashape.Alignment;
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwplib.writer.HWPWriter;
import org.junit.jupiter.api.Test;

class ReportCreateTest {

    /*  Section : 쪽
        Paragraph : 문단
        ParaShape : 문단
     */
    @Test
    void configureParaShapesTest() throws Exception {
        HWPFile hwpFile = BlankFileMaker.make();
        ArrayList<ParaShape> paraShapeList = hwpFile.getDocInfo().getParaShapeList();
        System.out.println("paraShapeList = " + paraShapeList);

        Section section = hwpFile.getBodyText().getSectionList().getFirst();
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

        Paragraph paragraph = hwpFile.getBodyText().getSectionList().getFirst().getParagraph(0);
        int charShapeCount = paragraph.getHeader().getCharShapeCount();
        System.out.println("charShapeCount = " + charShapeCount);

        ParaCharShape paraCharShape = paragraph.getCharShape();
        ArrayList<CharPositionShapeIdPair> positionShapeIdPairList = paraCharShape.getPositonShapeIdPairList();
        CharPositionShapeIdPair charPositionShapeIdPair = positionShapeIdPairList.getFirst();
        System.out.println("charPositionShapeIdPair.getShapeId = " + charPositionShapeIdPair.getShapeId());
        System.out.println("charPositionShapeIdPair.getPosition = " + charPositionShapeIdPair.getPosition());
        paragraph.getText().addString("객체 이동 보고서");

        CharShape changedCharShape = charShapeList.getFirst().clone();
        changedCharShape.setBaseSize(3000);
        changedCharShape.getProperty().setBold(true);
        charShapeList.add(changedCharShape);

        ArrayList<LineSegItem> lineSegItemList = paragraph.getLineSeg().getLineSegItemList();
        System.out.println("lineSegItemList = " + lineSegItemList);
        LineSegItem lineSegItem = lineSegItemList.getFirst();
        lineSegItem.setLineHeight(-100);

        paraCharShape.addParaCharShape(18, 5); // 왜인지 모르겠지만 시작 위치가 16부터 인것 같다
        HWPWriter.toFile(hwpFile, "C:/Users/Suhyeon/Desktop/hwpTest/char_shape_test.hwp");
    }

    @Test
    void writeTwoLineTest() throws Exception {
        HWPFile hwpFile = BlankFileMaker.make();
        Section section = hwpFile.getBodyText().getSectionList().getFirst();
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
    void writeImageTest() throws Exception {
    }
}
