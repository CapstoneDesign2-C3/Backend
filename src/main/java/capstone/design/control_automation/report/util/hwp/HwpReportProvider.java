package capstone.design.control_automation.report.util.hwp;

import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult;
import capstone.design.control_automation.report.util.ReportProvider;
import capstone.design.control_automation.report.util.hwp.GsoParam.PaperSize;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwplib.writer.HWPWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HwpReportProvider implements ReportProvider {

    private final HwpConfigurer configurer;
    private final HwpImageEditor imageEditor;
    private final HwpTableEditor tableEditor;
    private final HwpColumnMaker columnMaker;

    public byte[] createDetectedObjectReport(
        LocalDate date,
        String author,
        TableDataDto.MobileObjectInfo mobileObjectInfo,
        List<DetectionQueryResult.Track> trackOfMobileObject
    ) throws Exception {
        HWPFile hwpFile = BlankFileMaker.make();
        configurer.configureHWPFile(hwpFile);
        Section section = hwpFile.getBodyText().getSectionList().get(0);

        Paragraph title = section.getParagraph(0);
        writeText(title, "title", "객체 이동 보고서");

        Paragraph publishInfo = createParagraph(section);
        writeText(publishInfo, "publishInfo",
            "발행 일자 : " + date.toString() + "\n"
                + "발행자 : " + author);

        Paragraph map = createParagraph(section);
        configurer.configureParagraph(map, "map");
        int mapImageId = imageEditor.addBinDataToHwpFile(hwpFile, loadFile("./hwptest/image.png"));
        imageEditor.writeImage(map, mapImageId, new GsoParam(0, 0, PaperSize.MAX_WIDTH.getValue(), 75));

        Paragraph bodyLeftColumn = createParagraph(section);
        columnMaker.configureColumn(bodyLeftColumn, 40.0, 90.0);

        int cropImageId = imageEditor.addBinDataToHwpFile(hwpFile, loadFile("./hwptest/crop.png"));
        imageEditor.writeImage(bodyLeftColumn, cropImageId, new GsoParam(0, 0, 44, 60));

        writeText(bodyLeftColumn, "body",
            "객체 분류");
        int tableBorderFillId = tableEditor.addBorderFillInfo(hwpFile.getDocInfo());
        tableEditor.writeVerticalTable(
            bodyLeftColumn,
            mobileObjectInfo,
            new GsoParam(0, 70, 40, 60),
            tableBorderFillId
        );

        Paragraph bodyRightColumn = createParagraph(section);
        writeText(bodyRightColumn, "body",
            "객체 이동 현황\n");
        tableEditor.writeTable(
            bodyRightColumn,
            trackOfMobileObject,
            new GsoParam(0, 0, 100, 75),
            tableBorderFillId
        );

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HWPWriter.toStream(hwpFile, out);
        return out.toByteArray();
    }

    private void writeText(Paragraph paragraph, String paraName, String text) throws UnsupportedEncodingException {
        configurer.configureParagraph(paragraph, paraName);
        paragraph.getText().addString(text);
    }

    private Paragraph createParagraph(Section section) {
        Paragraph paragraph = section.addNewParagraph();
        paragraph.createCharShape();
        paragraph.createLineSeg();
        paragraph.getLineSeg().addNewLineSegItem();
        paragraph.createText();
        return paragraph;
    }

    private byte[] loadFile(String path) throws IOException {
        File file = new File(path);
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
}
