package capstone.design.control_automation.mapper.video;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import capstone.design.control_automation.common.PostgresContainerTest;
import capstone.design.control_automation.common.config.MyBatisConfig;
import capstone.design.control_automation.video.repository.dto.VideoQueryResult.Detail;
import capstone.design.control_automation.video.repository.dto.VideoQueryResult.SimpleWithEvent;
import capstone.design.control_automation.video.repository.dto.VideoQueryResult.SimpleWithMobileObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

@MybatisTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Import(MyBatisConfig.class)
class VideoMapperTest extends PostgresContainerTest {

    @Autowired
    private VideoMapper mapper;

    private DateTimeFormatter formatter;

    @BeforeEach
    void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    void findById() {
        List<Detail> expected = List.of(
            new Detail(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 1L, "uuid1", "사람", null),
            new Detail(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 3L, "uuid3", "사람", null),
            new Detail(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 6L, "uuid6", "사람", null),
            new Detail(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 14L, "uuid14", "사람", null),
            new Detail(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 17L, "uuid17", "사람", null),
            new Detail(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 18L, "uuid18", "사람", null)
        );

        List<Detail> actual = mapper.findById(1L);

        assertThat(actual).extracting("videoId", "videoUrl", "cameraScenery", "latitude", "longitude", "detectedObjectId", "detectedObjectUUID", "categoryName")
            .contains(
                tuple(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 1L, "uuid1", "사람"),
                tuple(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 3L, "uuid3", "사람"),
                tuple(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 6L, "uuid6", "사람"),
                tuple(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 14L, "uuid14", "사람"),
                tuple(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 17L, "uuid17", "사람")
            );
    }

    @Test
    void findByMobileObjectId() {
        SimpleWithMobileObject expected = new SimpleWithMobileObject(
            "/videos/video6.mp4",
            "uuid8", "Object8", null,
            LocalDateTime.parse("2025-07-21 09:25:37", formatter), LocalDateTime.parse("2025-07-21 09:26:44", formatter),
            "사람");

        SimpleWithMobileObject actual = mapper.findByMobileDetectionId(1L);

        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFields("detectedObjectCropImg")
            .isEqualTo(expected);

    }

    @Test
    @DisplayName("eventId로 simpleVideo 정보 가져 오기")
    void findByEventIdTest() {
        //given
        SimpleWithEvent expected = new SimpleWithEvent(
            "/videos/video4.mp4",
            "a9778202-6320-43c7-b4a4-404d03513921",
            LocalDateTime.parse("2025-07-21 08:00:00", formatter),
            LocalDateTime.parse("2025-07-21 08:02:47", formatter),
            "배회",
            "위험"
        );
        //when
        SimpleWithEvent actual = mapper.findByEventId(1L);
        //then

        assertThat(actual).isEqualTo(expected);
    }

}
