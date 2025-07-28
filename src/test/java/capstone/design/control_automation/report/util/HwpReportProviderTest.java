package capstone.design.control_automation.report.util;

import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Track;
import capstone.design.control_automation.report.util.hwp.HwpReportProvider;
import capstone.design.control_automation.report.util.hwp.TableDataDto.MobileObjectInfo;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.writer.HWPWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@TestPropertySource("classpath:local.env")
class HwpReportProviderTest {

    @Autowired
    HwpReportProvider hwpReportProvider;

    @Test
    void createDetectedObjectReport() throws Exception {
        byte[] report = hwpReportProvider.createDetectedObjectReport(
            List.of(
                new ReportParam.Track(
                    LocalDate.of(2025, 7, 25),
                    "이도훈",
                    new MobileObjectInfo("uuid", "유승종", "사람", "보라색 옷을 입고 있는 남자"),
                    List.of(
                        new Track(1L, "광화문 교차로", "https://example.com/thumbnail1.mp4",
                            LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                        new Track(13L, "북촌 한옥마을 입구", "https://example.com/thumbnail3.mp4",
                            LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                        new Track(25L, "부산 해운대 해수욕장", "https://example.com/thumbnail9.mp4",
                            LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03")),
                        new Track(1L, "광화문 교차로", "https://example.com/thumbnail1.mp4",
                            LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                        new Track(13L, "북촌 한옥마을 입구", "https://example.com/thumbnail3.mp4",
                            LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                        new Track(25L, "부산 해운대 해수욕장", "https://example.com/thumbnail9.mp4",
                            LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03")),
                        new Track(1L, "광화문 교차로", "https://example.com/thumbnail1.mp4",
                            LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                        new Track(13L, "북촌 한옥마을 입구", "https://example.com/thumbnail3.mp4",
                            LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                        new Track(25L, "부산 해운대 해수욕장", "https://example.com/thumbnail9.mp4",
                            LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03"))
                    )
                ),

                new ReportParam.Track(
                    LocalDate.of(2025, 7, 25),
                    "이도훈",
                    new MobileObjectInfo("uuid", "유승종", "사람", "보라색 옷을 입고 있는 남자"),
                    List.of(
                        new Track(1L, "광화문 교차로", "https://example.com/thumbnail1.mp4",
                            LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                        new Track(13L, "북촌 한옥마을 입구", "https://example.com/thumbnail3.mp4",
                            LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                        new Track(25L, "부산 해운대 해수욕장", "https://example.com/thumbnail9.mp4",
                            LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03")),
                        new Track(1L, "광화문 교차로", "https://example.com/thumbnail1.mp4",
                            LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                        new Track(13L, "북촌 한옥마을 입구", "https://example.com/thumbnail3.mp4",
                            LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                        new Track(25L, "부산 해운대 해수욕장", "https://example.com/thumbnail9.mp4",
                            LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03")),
                        new Track(1L, "광화문 교차로", "https://example.com/thumbnail1.mp4",
                            LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                        new Track(13L, "북촌 한옥마을 입구", "https://example.com/thumbnail3.mp4",
                            LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                        new Track(25L, "부산 해운대 해수욕장", "https://example.com/thumbnail9.mp4",
                            LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03"))
                    )
                ),
                new ReportParam.Track(
                    LocalDate.of(2025, 7, 25),
                    "이도훈",
                    new MobileObjectInfo("uuid", "유승종", "사람", "보라색 옷을 입고 있는 남자"),
                    List.of(
                        new Track(1L, "광화문 교차로", "https://example.com/thumbnail1.mp4",
                            LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                        new Track(13L, "북촌 한옥마을 입구", "https://example.com/thumbnail3.mp4",
                            LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                        new Track(25L, "부산 해운대 해수욕장", "https://example.com/thumbnail9.mp4",
                            LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03")),
                        new Track(1L, "광화문 교차로", "https://example.com/thumbnail1.mp4",
                            LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                        new Track(13L, "북촌 한옥마을 입구", "https://example.com/thumbnail3.mp4",
                            LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                        new Track(25L, "부산 해운대 해수욕장", "https://example.com/thumbnail9.mp4",
                            LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03")),
                        new Track(1L, "광화문 교차로", "https://example.com/thumbnail1.mp4",
                            LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                        new Track(13L, "북촌 한옥마을 입구", "https://example.com/thumbnail3.mp4",
                            LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                        new Track(25L, "부산 해운대 해수욕장", "https://example.com/thumbnail9.mp4",
                            LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03"))
                    )
                )
            )
        );

        HWPFile hwpFile = HWPReader.fromInputStream(new ByteArrayInputStream(report));
        HWPWriter.toFile(hwpFile, "./hwptest/report_sample.hwp");
    }

}
