package capstone.design.control_automation.mapper.camera;

import static org.assertj.core.api.Assertions.assertThat;

import capstone.design.control_automation.camera.repository.dto.CameraQueryResult.Info;
import capstone.design.control_automation.camera.repository.dto.CameraQueryResult.Position;
import capstone.design.control_automation.common.config.MyBatisConfig;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@MybatisTest
@Import(MyBatisConfig.class)
@ActiveProfiles("test")
class CameraMapperTest {

    @Autowired
    CameraMapper cameraMapper;

    @Test
    void findAllByFilterCondition() {
        List<Position> expected = List.of(
            new Position(1L, 37.5665, 126.9780),
            new Position(2L, 37.5700, 126.9768),
            new Position(3L, 37.5721, 126.9875),
            new Position(4L, 37.5511, 126.9882),
            new Position(5L, 37.5070, 127.0591),
            new Position(6L, 37.4847, 126.8963),
            new Position(7L, 37.4774, 126.9816),
            new Position(8L, 37.3927, 126.9611),
            new Position(9L, 35.1796, 129.0756),
            new Position(10L, 35.1531, 129.1186)
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
                "광화문 교차로",
                37.5665,
                126.9780,
                1L,
                "https://example.com/thumbnail1.mp4",
                LocalDateTime.parse("2025-07-09 08:00:00", formatter),
                LocalDateTime.parse("2025-07-09 08:05:00", formatter)
            )
        );

        List<Info> actual = cameraMapper.findWithVideosById(1L);

        assertThat(actual).hasSize(expected.size());
        assertThat(actual).hasSameElementsAs(expected);
    }
}
