package capstone.design.control_automation.report.util.hwp;

import capstone.design.control_automation.report.util.ReportProvider;
import capstone.design.control_automation.report.util.hwp.GsoParam.PaperSize;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HwpReportProvider implements ReportProvider {

    private final HwpConfigurer configurer;
    private final HwpImageEditor imageEditor;

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
        File file = new File("C:/Users/Suhyeon/Desktop/hwpTest/image.png");
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
