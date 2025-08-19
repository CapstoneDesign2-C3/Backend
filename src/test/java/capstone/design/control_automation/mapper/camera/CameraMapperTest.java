package capstone.design.control_automation.mapper.camera;

import static org.assertj.core.api.Assertions.assertThat;

import capstone.design.control_automation.camera.repository.dto.CameraQueryResult.*;
import capstone.design.control_automation.common.PostgresContainerTest;
import capstone.design.control_automation.common.config.MyBatisConfig;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

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

    @Test
    @DisplayName("getCameras 테스트")
    public void getCamerasTest(){
        List<Camera> expected = List.of(
                new Camera(1L,
                        "192.168.1.101",
                        554,
                        37.5661,
                        126.9781,
                        "카메라1 위치",
                        "서울특별시",
                        "주소1 상세",
                        "user1",
                        "pw1",
                        "/stream/1",
                        "Camera1"
                ),
                new Camera(2L,
                        "192.168.1.102",
                        554,
                        37.5662,
                        126.9782,
                        "카메라2 위치",
                        "서울특별시",
                        "주소2 상세",
                        "user2",
                        "pw2",
                        "/stream/2",
                        "Camera2"
                ),
                new Camera(3L,
                        "192.168.1.103",
                        554,
                        37.5663,
                        126.9783,
                        "카메라3 위치",
                        "서울특별시",
                        "주소3 상세",
                        "user3",
                        "pw3",
                        "/stream/3",
                        "Camera3"
                ),
                new Camera(4L,
                        "192.168.1.104",
                        554,
                        37.5664,
                        126.9784,
                        "카메라4 위치",
                        "서울특별시",
                        "주소4 상세",
                        "user4",
                        "pw4",
                        "/stream/4",
                        "Camera4"
                ),
                new Camera(5L,
                        "192.168.1.105",
                        554,
                        37.5665,
                        126.9785,
                        "카메라5 위치",
                        "서울특별시",
                        "주소5 상세",
                        "user5",
                        "pw5",
                        "/stream/5",
                        "Camera5"
                )
        );

        List<Camera> actual = cameraMapper.getCameras();

        assertThat(actual).isEqualTo(expected);
    }
}
