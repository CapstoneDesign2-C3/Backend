package capstone.design.control_automation.report.util.hwp;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.bodytext.paragraph.lineseg.LineSegItem;
import kr.dogfoot.hwplib.object.docinfo.CharShape;
import kr.dogfoot.hwplib.object.docinfo.DocInfo;
import kr.dogfoot.hwplib.object.docinfo.ParaShape;
import kr.dogfoot.hwplib.object.docinfo.parashape.Alignment;
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HwpConfigurer {

    private final StyleIdContext styleIdContext;

    private final Map<String, ParaShape> paraShapeMap = new LinkedHashMap<>();
    private final Map<String, CharShape> charShapeMap = new LinkedHashMap<>();
    private final Map<String, Integer> textSizeMap = new HashMap<>();

    public void configureHWPFile(HWPFile hwpFile) {
        ArrayList<ParaShape> paraShapeList = hwpFile.getDocInfo().getParaShapeList();
        ArrayList<CharShape> charShapeList = hwpFile.getDocInfo().getCharShapeList();
        for (String key : paraShapeMap.keySet()) {
            styleIdContext.putParaShapeId(key, paraShapeList.size());
            paraShapeList.add(paraShapeMap.get(key));
        }

        for (String key : charShapeMap.keySet()) {
            styleIdContext.putCharShapeId(key, charShapeList.size());
            charShapeList.add(charShapeMap.get(key));
        }
    }

    public void configureParagraph(Paragraph paragraph, String paramName) {
        if (paraShapeMap.containsKey(paramName)) {
            paragraph.getHeader().setParaShapeId(styleIdContext.getParaShapeId(paramName)); // 문단 모양 설정
        }

        if (charShapeMap.containsKey(paramName)) {
            paragraph.getCharShape().addParaCharShape(0, styleIdContext.getCharShapeId(paramName)); // 글자 모양 설정
        }

        if (textSizeMap.containsKey(paramName)) {
            LineSegItem firstLine = paragraph.getLineSeg().getLineSegItemList().get(0); // Text 높이 설정
            firstLine.setTextPartHeight(textSizeMap.get(paramName));
            firstLine.setDistanceBaseLineToLineVerticalPosition(textSizeMap.get(paramName));
        }
    }

    // === Shape 지정 장소 ===
    @PostConstruct
    public void init() {
        DocInfo docInfo = BlankFileMaker.make().getDocInfo();

        fillTextSizeMap();
        loadParaShapes(docInfo);
        loadCharShapes(docInfo);
    }

    private void fillTextSizeMap() {
        textSizeMap.put("title", 2500);
        textSizeMap.put("publishInfo", 1200);
        textSizeMap.put("tdata", 800);
        textSizeMap.put("column", 1000);
    }

    private void loadParaShapes(DocInfo docInfo) {
        ArrayList<ParaShape> paraShapes = docInfo.getParaShapeList();

        // Title 은 중앙 정렬
        ParaShape originParaShape = paraShapes.get(3);

        ParaShape titleParaShape = originParaShape.clone();
        titleParaShape.getProperty1().setAlignment(Alignment.Center);
        titleParaShape.setBottomParaSpace(5000);
        paraShapeMap.put("title", titleParaShape);

        // 발행 정보 는 우측 정렬
        ParaShape publishInfoParaShape = originParaShape.clone();
        publishInfoParaShape.getProperty1().setAlignment(Alignment.Right);
        publishInfoParaShape.setBottomParaSpace(2000);
        paraShapeMap.put("publishInfo", publishInfoParaShape);

        ParaShape mapParaShape = originParaShape.clone();
        mapParaShape.setBottomParaSpace(2000);
        paraShapeMap.put("map", mapParaShape);

        ParaShape tdataParaShape = originParaShape.clone();
        tdataParaShape.getProperty1().setAlignment(Alignment.Center);
        paraShapeMap.put("tdata", tdataParaShape);

        // 단 은 좌측 정렬, 글자 크기 10
        ParaShape columnParashape = originParaShape.clone();
        columnParashape.getProperty1().setAlignment(Alignment.Left);
        columnParashape.setBottomParaSpace(1000);
        paraShapeMap.put("column", columnParashape);
    }

    private void loadCharShapes(DocInfo docInfo) {
        ArrayList<CharShape> charShapes = docInfo.getCharShapeList();
        CharShape originCharShape = charShapes.get(0);

        CharShape titleCharShape = originCharShape.clone(); // add 시, 5번으로 들어감.
        titleCharShape.setBaseSize(textSizeMap.get("title"));
        titleCharShape.getProperty().setBold(true);
        charShapeMap.put("title", titleCharShape);

        CharShape publishInfoCharShape = originCharShape.clone();
        publishInfoCharShape.setBaseSize(textSizeMap.get("publishInfo"));
        charShapeMap.put("publishInfo", publishInfoCharShape);

        CharShape tdataCharShape = originCharShape.clone();
        tdataCharShape.setBaseSize(textSizeMap.get("tdata"));
        charShapeMap.put("tdata", tdataCharShape);

        CharShape columnCharShape = originCharShape.clone();
        columnCharShape.setBaseSize(textSizeMap.get("column"));
        charShapeMap.put("column", columnCharShape);
    }

}
