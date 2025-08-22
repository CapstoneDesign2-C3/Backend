package capstone.design.control_automation.report.util;

import capstone.design.control_automation.common.client.MapRequest;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Position;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Track;
import capstone.design.control_automation.common.client.GoogleStaticMapService;
import capstone.design.control_automation.report.util.ReportParam.DetectionTimeRange;
import capstone.design.control_automation.report.util.ReportParam.Event;
import capstone.design.control_automation.report.util.ReportParam.PublishInfo;
import capstone.design.control_automation.report.util.hwp.HwpReportProvider;
import capstone.design.control_automation.report.util.hwp.dto.TableDataDto.EventCount;
import capstone.design.control_automation.report.util.hwp.dto.TableDataDto.EventInfo;
import capstone.design.control_automation.report.util.hwp.dto.TableDataDto.MobileObjectInfo;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.writer.HWPWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureWireMock(port = 0)
@TestPropertySource("classpath:local.env")
class HwpReportProviderTest {

    @Autowired
    HwpReportProvider hwpReportProvider;

    @Autowired
    GoogleStaticMapService googleStaticMapService;

    @Test
    void createDetectedObjectReport() throws Exception {
        byte[] report = hwpReportProvider.createDetectedObjectReport(
            List.of(
                new ReportParam.Track(
                    new PublishInfo(
                        LocalDate.of(2025, 7, 25),
                        "이도훈"
                    ),
                    googleStaticMapService.getStaticMap(new MapRequest(
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
                        new Track(1L, "광화문 교차로", new byte[0],
                            LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                        new Track(13L, "북촌 한옥마을 입구", new byte[0],
                            LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                        new Track(25L, "부산 해운대 해수욕장", new byte[0],
                            LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03")),
                        new Track(1L, "광화문 교차로", new byte[0],
                            LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                        new Track(13L, "북촌 한옥마을 입구", new byte[0],
                            LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                        new Track(25L, "부산 해운대 해수욕장", new byte[0],
                            LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03")),
                        new Track(1L, "광화문 교차로", new byte[0],
                            LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                        new Track(13L, "북촌 한옥마을 입구", new byte[0],
                            LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                        new Track(25L, "부산 해운대 해수욕장", new byte[0],
                            LocalDateTime.parse("2025-07-09T09:40:00"), null)
                    )
                ),
                new ReportParam.Track(
                    new PublishInfo(
                        LocalDate.of(2025, 7, 25),
                        "이도훈"
                    ),
                    googleStaticMapService.getStaticMap(new MapRequest(
                        List.of(
                            new Position(1L, 37.4730359, 127.1027386),
                            new Position(2L, 37.4730659, 127.1024386),
                            new Position(3L, 37.4730959, 127.1027356),
                            new Position(4L, 37.4730459, 127.1027386),
                            new Position(5L, 37.4730259, 127.1027286)
                        )
                    )),
                    new byte[0],
                    new MobileObjectInfo("uuid", null, "사람", null),
                    List.of(
                        new Track(1L, "광화문 교차로", new byte[0],
                            LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                        new Track(13L, "북촌 한옥마을 입구", new byte[0],
                            LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                        new Track(25L, "부산 해운대 해수욕장", new byte[0],
                            LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03")),
                        new Track(1L, "광화문 교차로", new byte[0],
                            LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                        new Track(13L, "북촌 한옥마을 입구", new byte[0],
                            LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                        new Track(25L, "부산 해운대 해수욕장", new byte[0],
                            LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03")),
                        new Track(1L, "광화문 교차로", new byte[0],
                            LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                        new Track(13L, "북촌 한옥마을 입구", new byte[0],
                            LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                        new Track(25L, "부산 해운대 해수욕장", new byte[0],
                            LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03"))
                    )
                ),
                new ReportParam.Track(
                    new PublishInfo(
                        LocalDate.of(2025, 7, 25),
                        "이도훈"
                    ),
                    googleStaticMapService.getStaticMap(new MapRequest(
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
                        new Track(1L, "광화문 교차로", new byte[0],
                            LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                        new Track(13L, "북촌 한옥마을 입구", new byte[0],
                            LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                        new Track(25L, "부산 해운대 해수욕장", new byte[0],
                            LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03")),
                        new Track(1L, "광화문 교차로", new byte[0],
                            LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                        new Track(13L, "북촌 한옥마을 입구", new byte[0],
                            LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                        new Track(25L, "부산 해운대 해수욕장", new byte[0],
                            LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03")),
                        new Track(1L, "광화문 교차로", new byte[0],
                            LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                        new Track(13L, "북촌 한옥마을 입구", new byte[0],
                            LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                        new Track(25L, "부산 해운대 해수욕장", new byte[0],
                            LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03"))
                    )
                )
            )
        );

        HWPFile hwpFile = HWPReader.fromInputStream(new ByteArrayInputStream(report));
        HWPWriter.toFile(hwpFile, "./hwptest/report_sample.hwp");
    }

    @Test
    void measureCreateDetectedObjectReportPerformance() throws Exception {
        List<Long> durations = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            LocalDateTime start = LocalDateTime.now();

            // 실행할 테스트 메서드 호출
            byte[] report = hwpReportProvider.createDetectedObjectReport(
                List.of(
                    new ReportParam.Track(
                        new PublishInfo(
                            LocalDate.of(2025, 7, 25),
                            "이도훈"
                        ),
                        googleStaticMapService.getStaticMap(new MapRequest(
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
                            new Track(1L, "광화문 교차로", new byte[0],
                                LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                            new Track(13L, "북촌 한옥마을 입구", new byte[0],
                                LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                            new Track(25L, "부산 해운대 해수욕장", new byte[0],
                                LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03")),
                            new Track(1L, "광화문 교차로", new byte[0],
                                LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                            new Track(13L, "북촌 한옥마을 입구", new byte[0],
                                LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                            new Track(25L, "부산 해운대 해수욕장", new byte[0],
                                LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03")),
                            new Track(1L, "광화문 교차로", new byte[0],
                                LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                            new Track(13L, "북촌 한옥마을 입구", new byte[0],
                                LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                            new Track(25L, "부산 해운대 해수욕장", new byte[0],
                                LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03"))
                        )
                    ),
                    new ReportParam.Track(
                        new PublishInfo(
                            LocalDate.of(2025, 7, 25),
                            "이도훈"
                        ),
                        googleStaticMapService.getStaticMap(new MapRequest(
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
                            new Track(1L, "광화문 교차로", new byte[0],
                                LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                            new Track(13L, "북촌 한옥마을 입구", new byte[0],
                                LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                            new Track(25L, "부산 해운대 해수욕장", new byte[0],
                                LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03")),
                            new Track(1L, "광화문 교차로", new byte[0],
                                LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                            new Track(13L, "북촌 한옥마을 입구", new byte[0],
                                LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                            new Track(25L, "부산 해운대 해수욕장", new byte[0],
                                LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03")),
                            new Track(1L, "광화문 교차로", new byte[0],
                                LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                            new Track(13L, "북촌 한옥마을 입구", new byte[0],
                                LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                            new Track(25L, "부산 해운대 해수욕장", new byte[0],
                                LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03"))
                        )
                    ),
                    new ReportParam.Track(
                        new PublishInfo(
                            LocalDate.of(2025, 7, 25),
                            "이도훈"
                        ),
                        googleStaticMapService.getStaticMap(new MapRequest(
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
                            new Track(1L, "광화문 교차로", new byte[0],
                                LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                            new Track(13L, "북촌 한옥마을 입구", new byte[0],
                                LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                            new Track(25L, "부산 해운대 해수욕장", new byte[0],
                                LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03")),
                            new Track(1L, "광화문 교차로", new byte[0],
                                LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                            new Track(13L, "북촌 한옥마을 입구", new byte[0],
                                LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                            new Track(25L, "부산 해운대 해수욕장", new byte[0],
                                LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03")),
                            new Track(1L, "광화문 교차로", new byte[0],
                                LocalDateTime.parse("2025-07-09T08:00:10"), LocalDateTime.parse("2025-07-09T08:00:12")),
                            new Track(13L, "북촌 한옥마을 입구", new byte[0],
                                LocalDateTime.parse("2025-07-09T08:10:30"), LocalDateTime.parse("2025-07-09T08:10:35")),
                            new Track(25L, "부산 해운대 해수욕장", new byte[0],
                                LocalDateTime.parse("2025-07-09T09:40:00"), LocalDateTime.parse("2025-07-09T09:40:03"))
                        )
                    )
                )
            );

            LocalDateTime end = LocalDateTime.now();
            durations.add(Duration.between(start, end).toMillis());
        }

        writeCsv(durations, "./report_times.csv");
    }

    private void writeCsv(List<Long> durations, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("iteration,time_ms\n");
            for (int i = 0; i < durations.size(); i++) {
                writer.write((i + 1) + "," + durations.get(i) + "\n");
            }
        }
    }

    @Test
    void createEventReport() throws Exception {
        byte[] report = hwpReportProvider.createEventReport(
            new Event(
                new PublishInfo(
                    LocalDate.of(2025, 7, 25),
                    "이도훈"
                ),
                new DetectionTimeRange(
                    LocalDateTime.of(2025, 8, 1, 10, 10),
                    LocalDateTime.of(2025, 8, 1, 23, 10)
                ),
                List.of(
                    new EventInfo(
                        "uuid1",
                        "화재",
                        "강남구 도곡로 25",
                        LocalDateTime.of(2025, 8, 1, 10, 10),
                        LocalDateTime.of(2025, 8, 1, 23, 10)
                    ),
                    new EventInfo(
                        "uuid1",
                        "화재",
                        "강남구 도곡로 25",
                        LocalDateTime.of(2025, 8, 1, 10, 10),
                        LocalDateTime.of(2025, 8, 1, 23, 10)
                    ),
                    new EventInfo(
                        "uuid1",
                        "화재",
                        "강남구 도곡로 25",
                        LocalDateTime.of(2025, 8, 1, 10, 10),
                        LocalDateTime.of(2025, 8, 1, 23, 10)
                    )
                ),
                List.of(
                    new EventCount(
                        "화재", 3
                    ),
                    new EventCount(
                        "안전 구역 침입", 2
                    ),
                    new EventCount(
                        "안전모 미 착용", 10
                    )
                )
            )
        );

        HWPFile hwpFile = HWPReader.fromInputStream(new ByteArrayInputStream(report));
        HWPWriter.toFile(hwpFile, "./hwptest/report_event_sample.hwp");
    }
}
