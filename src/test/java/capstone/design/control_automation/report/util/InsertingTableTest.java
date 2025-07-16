package capstone.design.control_automation.report.util;

import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult;
import capstone.design.control_automation.report.util.hwp.HwpTableEditor;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.writer.HWPWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class InsertingTableTest {
    private final String folder_path = "C:/Users/dn060/hwptest/"; // 개인 폴더 경로로 지정할 것
    private HwpTableEditor hwpTableEditor;
    private static List<DetectionQueryResult.Track> tracks;

    @BeforeAll
    public static void setup(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        tracks = List.of(
                new DetectionQueryResult.Track(1L, "광화문 교차로", "https://example.com/thumbnail1.mp4", LocalDateTime.parse("2025-07-09 08:00:10", formatter), LocalDateTime.parse("2025-07-09 08:00:12", formatter)),
                new DetectionQueryResult.Track(13L, "북촌 한옥마을 입구", "https://example.com/thumbnail3.mp4", LocalDateTime.parse("2025-07-09 08:10:30", formatter), LocalDateTime.parse("2025-07-09 08:10:35", formatter)),
                new DetectionQueryResult.Track(25L, "부산 해운대 해수욕장", "https://example.com/thumbnail9.mp4", LocalDateTime.parse("2025-07-09 09:40:00", formatter), LocalDateTime.parse("2025-07-09 09:40:03", formatter))
        );
    }

    @Test
    public void insertingTableTest() throws Exception {
        String filename = folder_path + "blank.hwp";
        HWPFile hwpFile = HWPReader.fromFile(filename);

        hwpTableEditor = new HwpTableEditor(hwpFile);

        if (hwpFile != null) {
            hwpTableEditor.makeTable(hwpFile, tracks);
            String writePath = folder_path + "result-inserting-table.hwp";
            HWPWriter.toFile(hwpFile, writePath);
        }
    }
}
