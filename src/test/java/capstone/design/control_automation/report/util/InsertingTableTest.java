package capstone.design.control_automation.report.util;

import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult;
import capstone.design.control_automation.report.util.hwp.HwpTableEditor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class InsertingTableTest {

    private HwpTableEditor hwpTableEditor;
    private static List<DetectionQueryResult.Track> tracks;

    @BeforeAll
    public static void setup() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        tracks = List.of(
            new DetectionQueryResult.Track(1L, "광화문 교차로", "https://example.com/thumbnail1.mp4",
                LocalDateTime.parse("2025-07-09 08:00:10", formatter), LocalDateTime.parse("2025-07-09 08:00:12", formatter)),
            new DetectionQueryResult.Track(13L, "북촌 한옥마을 입구", "https://example.com/thumbnail3.mp4",
                LocalDateTime.parse("2025-07-09 08:10:30", formatter), LocalDateTime.parse("2025-07-09 08:10:35", formatter)),
            new DetectionQueryResult.Track(25L, "부산 해운대 해수욕장", "https://example.com/thumbnail9.mp4",
                LocalDateTime.parse("2025-07-09 09:40:00", formatter), LocalDateTime.parse("2025-07-09 09:40:03", formatter))
        );
    }

    @Test
    public void insertingTableTest() throws Exception {
    }
}
