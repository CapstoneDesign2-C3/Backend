package capstone.design.control_automation.report.util;

import capstone.design.control_automation.common.client.GoogleStaticMapApiClient;
import capstone.design.control_automation.common.client.MapRequest;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Position;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Track;
import capstone.design.control_automation.report.util.ReportParam.PublishInfo;
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

    @Autowired
    GoogleStaticMapApiClient googleStaticMapApiClient;

    @Test
    void createDetectedObjectReport() throws Exception {
        byte[] report = hwpReportProvider.createDetectedObjectReport(
            List.of(
                new ReportParam.Track(
                    new PublishInfo(
                        LocalDate.of(2025, 7, 25),
                        "이도훈"
                    ),
                    googleStaticMapApiClient.requestStaticMap(new MapRequest(
                        List.of(
                            new Position(1L, 37.4740359, 127.1027386),
                            new Position(2L, 37.4750659, 127.1034386),
                            new Position(3L, 37.4760959, 127.1047356),
                            new Position(4L, 37.4770459, 127.1057386),
                            new Position(5L, 37.4780259, 127.1067286)
                        )
                    )),
                    new byte[0],
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
                    new PublishInfo(
                        LocalDate.of(2025, 7, 25),
                        "이도훈"
                    ),
                    googleStaticMapApiClient.requestStaticMap(new MapRequest(
                        List.of(
                            new Position(1L, 37.4730359, 127.1027386),
                            new Position(2L, 37.4730659, 127.1024386),
                            new Position(3L, 37.4730959, 127.1027356),
                            new Position(4L, 37.4730459, 127.1027386),
                            new Position(5L, 37.4730259, 127.1027286)
                        )
                    )),
                    new byte[0],
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
                    new PublishInfo(
                        LocalDate.of(2025, 7, 25),
                        "이도훈"
                    ),
                    googleStaticMapApiClient.requestStaticMap(new MapRequest(
                        List.of(
                            new Position(1L, 37.4730359, 127.1027386),
                            new Position(2L, 37.4730659, 127.1024386),
                            new Position(3L, 37.4730959, 127.1027356),
                            new Position(4L, 37.4730459, 127.1027386),
                            new Position(5L, 37.4730259, 127.1027286)
                        )
                    )),
                    new byte[0],
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
