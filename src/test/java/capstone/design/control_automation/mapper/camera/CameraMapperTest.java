package capstone.design.control_automation.mapper.camera;

import static org.assertj.core.api.Assertions.assertThat;

import capstone.design.control_automation.camera.repository.dto.CameraQueryResult.Info;
import capstone.design.control_automation.camera.repository.dto.CameraQueryResult.Position;
import capstone.design.control_automation.common.PostgresContainerTest;
import capstone.design.control_automation.common.config.MyBatisConfig;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@MybatisTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Import(MyBatisConfig.class)
class CameraMapperTest extends PostgresContainerTest {

    @Autowired
    CameraMapper cameraMapper;

    @Test
    void findAllByFilterCondition() {
        List<Position> expected = List.of(
            new Position(1L, 37.5661, 126.9781),
            new Position(2L, 37.5662, 126.9782),
            new Position(3L, 37.5663, 126.9783),
            new Position(4L, 37.5664, 126.9784),
            new Position(5L, 37.5665, 126.9785)
        );
        List<Position> actual = cameraMapper.findAllByFilterCondition(50.1,120.1,10.1, 130.01);

        assertThat(actual).hasSize(expected.size());
        assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    void findWithVideosById() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Info> expected = List.of(
            new Info(1L,
                "Camera1",
                37.5661,
                126.9781,
                2L,
                "/thumbs/thumb2.jpg",
                LocalDateTime.parse("2025-07-21 09:10:00", formatter),
                LocalDateTime.parse("2025-07-21 09:14:00", formatter)
            ),
            new Info(1L,
                "Camera1",
                37.5661,
                126.9781,
                11L,
                "/thumbs/thumb11.jpg",
                LocalDateTime.parse("2025-07-21 09:55:00", formatter),
                LocalDateTime.parse("2025-07-21 09:59:00", formatter)
            ),
            new Info(1L,
                "Camera1",
                37.5661,
                126.9781,
                12L,
                "/thumbs/thumb12.jpg",
                LocalDateTime.parse("2025-07-21 10:00:00", formatter),
                LocalDateTime.parse("2025-07-21 10:04:00", formatter)
            ),
            new Info(1L,
                "Camera1",
                37.5661,
                126.9781,
                13L,
                "/thumbs/thumb13.jpg",
                LocalDateTime.parse("2025-07-21 10:05:00", formatter),
                LocalDateTime.parse("2025-07-21 10:09:00", formatter)
            ),
            new Info(1L,
                "Camera1",
                37.5661,
                126.9781,
                25L,
                "/thumbs/thumb5.jpg",
                LocalDateTime.parse("2025-07-21 09:25:00", formatter),
                LocalDateTime.parse("2025-07-21 09:28:00", formatter)
            ),
            new Info(1L,
                "Camera1",
                37.5661,
                126.9781,
                33L,
                "/thumbs/thumb13.jpg",
                LocalDateTime.parse("2025-07-21 10:05:00", formatter),
                LocalDateTime.parse("2025-07-21 10:10:00", formatter)
            ),
            new Info(1L,
                "Camera1",
                37.5661,
                126.9781,
                39L,
                "/thumbs/thumb19.jpg",
                LocalDateTime.parse("2025-07-21 10:35:00", formatter),
                LocalDateTime.parse("2025-07-21 10:40:00", formatter)
            )
        );

        List<Info> actual = cameraMapper.findWithVideosById(1L);

        assertThat(actual).hasSize(expected.size());
        assertThat(actual).hasSameElementsAs(expected);
    }
}
