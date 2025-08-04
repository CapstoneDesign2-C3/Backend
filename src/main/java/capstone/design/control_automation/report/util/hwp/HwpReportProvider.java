package capstone.design.control_automation.report.util.hwp;

import capstone.design.control_automation.report.util.ReportParam;
import capstone.design.control_automation.report.util.ReportParam.Track;
import capstone.design.control_automation.report.util.ReportProvider;
import capstone.design.control_automation.report.util.hwp.GsoParam.PaperSize;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
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
        List<ReportParam.Track> tracks
    ) throws Exception {
        HWPFile hwpFile = BlankFileMaker.make();
        configurer.configureHWPFile(hwpFile);
        Section section = hwpFile.getBodyText().getSectionList().get(0);

        for (int i = 0; i < tracks.size(); i++) {
            Track track = tracks.get(i);

            Paragraph title = section.getParagraph(0);
            if (i == 0) {
                title.getLineSeg().getLineSegItemList().remove(0);
            } else {
                title = createParagraph(section);
                columnMaker.mergeToOneColumn(title);
            }

            writeText(title, "title", "객체 이동 보고서");

            Paragraph publishInfo = createParagraph(section);
            writeText(publishInfo, "publishInfo",
                "발행 일자 : " + track.publishInfo().publishDate().toString() + "\n");
            writeText(publishInfo, "publishInfo",
                "발행자 : " + track.publishInfo().author());

            Paragraph map = createParagraph(section);
            configurer.configureParagraph(map, "map");
            int mapImageId = imageEditor.addBinDataToHwpFile(hwpFile, track.mapImage());
            imageEditor.writeImage(map, mapImageId, new GsoParam(0, 0, PaperSize.MAX_WIDTH.getValue(), 75));

            Paragraph bodyLeftColumn = createParagraph(section);
            columnMaker.configureColumn(bodyLeftColumn, 40.0, 90.0);

            int cropImageId = imageEditor.addBinDataToHwpFile(hwpFile, track.cropImage());
            imageEditor.writeImage(bodyLeftColumn, cropImageId, new GsoParam(0, 0, 44, 60));

            writeText(bodyLeftColumn, "body",
                "객체 분류");
            int tableBorderFillId = tableEditor.addBorderFillInfo(hwpFile.getDocInfo());
            tableEditor.writeVerticalTable(
                bodyLeftColumn,
                track.mobileObjectInfo(),
                new GsoParam(0, 70, 40, 60),
                tableBorderFillId
            );

            Paragraph bodyRightColumn = createParagraph(section);
            writeText(bodyRightColumn, "body",
                "객체 이동 현황\n");
            tableEditor.writeTable(
                bodyRightColumn,
                track.trackOfMobileObject(),
                new GsoParam(0, 0, 100, 75),
                tableBorderFillId
            );

        }

        return extractBytesFromHwpFile(hwpFile);
    }

    public byte[] createEventReport(
    ) throws Exception {
        HWPFile hwpFile = BlankFileMaker.make();
        configurer.configureHWPFile(hwpFile);

        Section section = hwpFile.getBodyText().getSectionList().get(0);
        Paragraph title = section.getParagraph(0);
        title.getLineSeg().getLineSegItemList().remove(0);
        writeText(title, "title", "이벤트 발생 보고서");

        return extractBytesFromHwpFile(hwpFile);
    }

    private byte[] extractBytesFromHwpFile(HWPFile hwpFile) throws Exception {
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
        paragraph.createText();
        return paragraph;
    }
}
