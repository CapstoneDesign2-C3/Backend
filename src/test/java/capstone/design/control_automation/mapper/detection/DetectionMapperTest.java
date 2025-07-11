package capstone.design.control_automation.mapper.detection;

import static org.assertj.core.api.Assertions.assertThat;

import capstone.design.control_automation.common.config.MyBatisConfig;
import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult.FixedObject;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Position;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Track;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@MybatisTest
@Import(MyBatisConfig.class)
@ActiveProfiles("test")
class DetectionMapperTest {

    @Autowired
    DetectionMapper detectionMapper;

    DateTimeFormatter formatter;

    @BeforeEach
    void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    void getTrackCountOfMobileObject() {
        Long expected = 1L;
        Long actual = detectionMapper.getTrackCountOfMobileObject(1L, LocalDateTime.parse("2025-07-09 08:00:00", formatter),
            LocalDateTime.parse("2025-07-09 08:00:15", formatter));

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getTracksOfMobileObject() {
        List<Track> expected = List.of(
            new Track(1L, "https://example.com/thumbnail1.mp4", LocalDateTime.parse("2025-07-09 08:00:10", formatter), LocalDateTime.parse("2025-07-09 08:00:12", formatter)),
            new Track(13L, "https://example.com/thumbnail3.mp4", LocalDateTime.parse("2025-07-09 08:10:30", formatter), LocalDateTime.parse("2025-07-09 08:10:35", formatter)),
            new Track(25L, "https://example.com/thumbnail9.mp4", LocalDateTime.parse("2025-07-09 09:40:00", formatter), LocalDateTime.parse("2025-07-09 09:40:03", formatter))
        );
        List<Track> actual = detectionMapper.getTracksOfMobileObject(1L, LocalDateTime.parse("2025-07-09 08:00:00", formatter),
            LocalDateTime.parse("2025-07-09 10:00:15", formatter), 5, 0L);

        assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    void getPositionsOfMobileObject() {
        List<Position> expected = List.of(
            new Position(1L, 37.5665, 126.9780),
            new Position(13L, 37.5721, 126.9875),
            new Position(25L, 35.1796, 129.0756)
        );

        List<Position> actual = detectionMapper.getPositionsOfMobileObject(1L,
            LocalDateTime.parse("2025-07-09 08:00:00", formatter),
            LocalDateTime.parse("2025-07-09 10:00:15", formatter));

        assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    void getFixedDetectionCountByFilterAndIds() {
        Long expected = 3L;
        Long actual = detectionMapper.getFixedDetectionCountByFilterAndIds(null, null,
            List.of(6L, 7L, 8L, 9L));

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getFixedDetectionsByFilterAndIds() {
        List<FixedObject> expected = List.of(
            new FixedObject(7L, "https://example.com/video7.mp4","화재", null, "자전거 도로 상황"),
            new FixedObject(8L, "https://example.com/video8.mp4", "화재", null, "길거리 공연 장면"),
            new FixedObject(9L, "https://example.com/video9.mp4", "교통사고", null, "도로 정비 작업")
        );

        List<FixedObject> actual = detectionMapper.getFixedDetectionsByFilterAndIds(
            null,
            null,
            List.of(6L, 7L, 8L, 9L),
            10,
            0L
        );

        assertThat(actual).hasSameElementsAs(expected);
    }
}
