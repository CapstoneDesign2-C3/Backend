package capstone.design.control_automation.report.util;

import jakarta.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.bodytext.paragraph.charshape.CharPositionShapeIdPair;
import kr.dogfoot.hwplib.object.bodytext.paragraph.lineseg.LineSegItem;
import kr.dogfoot.hwplib.object.bodytext.paragraph.lineseg.ParaLineSeg;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.ParaText;
import kr.dogfoot.hwplib.object.docinfo.CharShape;
import kr.dogfoot.hwplib.object.docinfo.DocInfo;
import kr.dogfoot.hwplib.object.docinfo.ParaShape;
import kr.dogfoot.hwplib.object.docinfo.parashape.Alignment;
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker;
import org.springframework.stereotype.Component;

@Component
public class HwpReportProvider implements ReportProvider {

    private final Integer titleTextSize = 3000;
    private final Integer publishInfoTextSize = 1500;

    private ParaShape titleParaShape;
    private ParaShape publishInfoParaShape;
    private CharShape titleCharShape;
    private CharShape publishInfoCharShape;

    public HWPFile createDetectedObjectReport() throws UnsupportedEncodingException {
        HWPFile hwpFile = BlankFileMaker.make();
        configureHWPFile(hwpFile);

        Section section = hwpFile.getBodyText().getSectionList().getFirst();
        writeTitle(section);
        writePublishInfo(section);

        return hwpFile;
    }

    @PostConstruct
    public void init() {
        DocInfo docInfo = BlankFileMaker.make().getDocInfo();

        loadParaShapes(docInfo);
        loadCharShapes(docInfo);
    }

    private void configureHWPFile(HWPFile hwpFile) {
        ArrayList<ParaShape> paraShapeList = hwpFile.getDocInfo().getParaShapeList();
        paraShapeList.add(titleParaShape); // paraShapeId : 12
        paraShapeList.add(publishInfoParaShape); // paraShapeId : 13

        ArrayList<CharShape> charShapeList = hwpFile.getDocInfo().getCharShapeList();
        charShapeList.add(titleCharShape); // charShapeId : 5
        charShapeList.add(publishInfoCharShape); // charShapeId : 6
    }

    private void writeTitle(Section section) throws UnsupportedEncodingException {
        Paragraph title = section.getParagraph(0);
        title.getHeader().setParaShapeId(12); // 문단 모양 설정 (중앙 정렬)
        title.getCharShape().addParaCharShape(0, 5); // 글자 모양 설정 (글자 크기, Bold)
        LineSegItem firstLine = title.getLineSeg().getLineSegItemList().getFirst(); // Text 높이 설정
        firstLine.setTextPartHeight(titleTextSize);
        firstLine.setDistanceBaseLineToLineVerticalPosition(titleTextSize);

        title.getText().addString("객체 이동 보고서");
    }

    private void writePublishInfo(Section section) throws UnsupportedEncodingException {
        Paragraph publishInfo = section.addNewParagraph();
        publishInfo.createCharShape();
        publishInfo.getHeader().setParaShapeId(13); // 문단 모양 설정 (우측 정렬)
        publishInfo.getCharShape().addParaCharShape(0, 6); // 글자 모양 설정 (글자 크기, Bold)

        publishInfo.createLineSeg();
        ParaLineSeg lineSeg = publishInfo.getLineSeg();
        lineSeg.addNewLineSegItem();
        LineSegItem firstLine = lineSeg.getLineSegItemList().getFirst(); // Text 높이 설정
        firstLine.setTextPartHeight(publishInfoTextSize);
        firstLine.setDistanceBaseLineToLineVerticalPosition(publishInfoTextSize);

        publishInfo.createText();
        ParaText publishInfoText = publishInfo.getText();
        publishInfoText.addString("발행 일자 : 2025.01.01\n");
        publishInfoText.addString("발행자 : 이도훈");
    }

    private void loadParaShapes(DocInfo docInfo) {
        ArrayList<ParaShape> paraShapes = docInfo.getParaShapeList();

        // Title 은 중앙 정렬
        ParaShape originParaShape = paraShapes.get(3);
        titleParaShape = originParaShape.clone();
        titleParaShape.getProperty1().setAlignment(Alignment.Center);
        titleParaShape.setBottomParaSpace(5000);

        // 발행 정보 는 우측 정렬
        publishInfoParaShape = originParaShape.clone();
        publishInfoParaShape.getProperty1().setAlignment(Alignment.Right);
    }

    private void loadCharShapes(DocInfo docInfo) {
        ArrayList<CharShape> charShapes = docInfo.getCharShapeList();
        CharShape originCharShape = charShapes.getFirst();
        titleCharShape = originCharShape.clone(); // add 시, 5번으로 들어감.
        titleCharShape.setBaseSize(titleTextSize);
        titleCharShape.getProperty().setBold(true);

        publishInfoCharShape = originCharShape.clone();
        publishInfoCharShape.setBaseSize(publishInfoTextSize);
    }

}
