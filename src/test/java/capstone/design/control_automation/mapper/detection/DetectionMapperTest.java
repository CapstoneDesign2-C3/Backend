package capstone.design.control_automation.mapper.detection;

import static org.assertj.core.api.Assertions.assertThat;

import capstone.design.control_automation.common.PostgresContainerTest;
import capstone.design.control_automation.common.config.MyBatisConfig;
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
import org.springframework.test.annotation.DirtiesContext;

@MybatisTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Import(MyBatisConfig.class)
class DetectionMapperTest extends PostgresContainerTest {

    @Autowired
    DetectionMapper detectionMapper;

    DateTimeFormatter formatter;

    @BeforeEach
    void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    void getTrackCountOfMobileObject() {
        Long expected = 3L;
        Long actual = detectionMapper.getTrackCountOfMobileObject(
            1L,
            LocalDateTime.parse("2025-07-21 09:00:00", formatter),
            LocalDateTime.parse("2025-07-21 09:50:40", formatter));

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getTracksOfMobileObject() {
        List<Track> expected = List.of(
            new Track(2L, "Camera5", "/thumbs/thumb9.jpg", LocalDateTime.parse("2025-07-21 09:40:33", formatter),
                LocalDateTime.parse("2025-07-21 09:41:59", formatter)),
            new Track(62L, "Camera4", "/thumbs/thumb1.jpg", LocalDateTime.parse("2025-07-21 09:00:22", formatter),
                LocalDateTime.parse("2025-07-21 09:00:51", formatter)),
            new Track(79L, "Camera2", "/thumbs/thumb10.jpg", LocalDateTime.parse("2025-07-21 09:45:35", formatter),
                LocalDateTime.parse("2025-07-21 09:46:37", formatter))
        );

        List<Track> actual = detectionMapper.getTracksOfMobileObject(
            1L,
            LocalDateTime.parse("2025-07-21 09:00:00", formatter),
            LocalDateTime.parse("2025-07-21 10:00:00", formatter),
            5, 0L);

        assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    void getPositionsOfMobileObject() {
        List<Position> expected = List.of(
            new Position(15L, 37.5661, 126.9781),
            new Position(63L, 37.5665, 126.9785),
            new Position(64L, 37.5662, 126.9782),
            new Position(74L, 37.5665, 126.9785),
            new Position(94L, 37.5664, 126.9784)
        );

        List<Position> actual = detectionMapper.getPositionsOfMobileObject(3L,
            LocalDateTime.parse("2025-07-21 09:00:00", formatter),
            LocalDateTime.parse("2025-07-21 10:00:40", formatter));

        assertThat(actual).hasSameElementsAs(expected);
    }

}
