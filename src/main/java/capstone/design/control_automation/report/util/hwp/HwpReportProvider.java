package capstone.design.control_automation.report.util.hwp;

import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult;
import capstone.design.control_automation.report.util.ReportProvider;
import capstone.design.control_automation.report.util.hwp.GsoParam.PaperSize;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.docinfo.DocInfo;
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HwpReportProvider implements ReportProvider {

    private final HwpConfigurer configurer;
    private final HwpImageEditor imageEditor;
    private final HwpTableEditor tableEditor;

    public HWPFile createDetectedObjectReport() throws IOException {
        HWPFile hwpFile = BlankFileMaker.make();
        configurer.configureHWPFile(hwpFile);
        Section section = hwpFile.getBodyText().getSectionList().get(0);

        Paragraph title = section.getParagraph(0);
        writeText(title, "title", "객체 이동 보고서");

        Paragraph publishInfo = createParagraph(section);
        writeText(publishInfo, "publishInfo",
            "발행 일자 : 2025.01.01\n"
                + "발행자 : 이도훈");

        Paragraph map = createParagraph(section);
        int imageId = imageEditor.addBinDataToHwpFile(hwpFile, loadFile());
        imageEditor.writeImage(map, imageId, new GsoParam(0, 0, PaperSize.MAX_WIDTH.getValue(), 75));

        Paragraph table = createParagraph(section);
        DocInfo docInfo = hwpFile.getDocInfo();
        tableEditor.writeTable(table,
                new GsoParam(30, 0, 120, 75)
        , List.of(
                        new DetectionQueryResult.Track(1L, "광화문 교차로", "https://example.com/thumbnail1.mp4", LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                        new DetectionQueryResult.Track(13L, "북촌 한옥마을 입구", "https://example.com/thumbnail3.mp4", LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                        new DetectionQueryResult.Track(25L, "부산 해운대 해수욕장", "https://example.com/thumbnail9.mp4", LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03"))
                )
        , docInfo);

        return hwpFile;
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

    private byte[] loadFile() throws IOException {
        File file = new File("./hwptest/sample.jpg");
        byte[] buffer = new byte[(int) file.length()];
        InputStream ios = null;
        try {
            ios = new FileInputStream(file);
            ios.read(buffer);
        } finally {
            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
            }
        }
        return buffer;
    }
}
