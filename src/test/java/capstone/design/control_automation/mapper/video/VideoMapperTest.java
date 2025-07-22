package capstone.design.control_automation.mapper.video;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.springframework.test.context.ActiveProfiles;

@MybatisTest
@Import(MyBatisConfig.class)
@ActiveProfiles("test")
class VideoMapperTest {

    @Autowired
    private VideoMapper mapper;

    private DateTimeFormatter formatter;

    @BeforeEach
    void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    void findById() {
        List <Detail> expected = List.of(
            new Detail(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 1L, "uuid1", "사람", "/crops/object1.jpg"),
            new Detail(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 3L, "uuid3", "사람", "/crops/object3.jpg"),
            new Detail(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 6L, "uuid6", "사람", "/crops/object6.jpg"),
            new Detail(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 14L, "uuid14", "사람", "/crops/object14.jpg"),
            new Detail(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 17L, "uuid17", "사람", "/crops/object17.jpg"),
            new Detail(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 18L, "uuid18", "사람", "/crops/object18.jpg")
        );

        List<Detail> actual = mapper.findById(1L);

        assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    void findByMobileObjectId() {
        SimpleWithMobileObject expected = new SimpleWithMobileObject(
            "/videos/video6.mp4",
            "uuid8", "Object8", "/crops/object8.jpg",
            LocalDateTime.parse("2025-07-21 09:25:37", formatter), LocalDateTime.parse("2025-07-21 09:26:44", formatter),
            "사람");

        SimpleWithMobileObject actual = mapper.findByMobileDetectionId(1L);

        assertThat(actual).isEqualTo(expected);
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
