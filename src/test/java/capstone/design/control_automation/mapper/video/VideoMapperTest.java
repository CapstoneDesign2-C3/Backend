package capstone.design.control_automation.mapper.video;

import static org.assertj.core.api.Assertions.assertThat;

import capstone.design.control_automation.common.config.MyBatisConfig;
import capstone.design.control_automation.video.repository.dto.VideoQueryResult.Detail;
import capstone.design.control_automation.video.repository.dto.VideoQueryResult.SimpleWithFixedObject;
import capstone.design.control_automation.video.repository.dto.VideoQueryResult.SimpleWithMobileObject;
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
            new Detail(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 1L, "사람", "/crops/object1.jpg", "노란색 헬멧을 쓴 남성"),
            new Detail(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 3L, "사람", "/crops/object3.jpg", "검정색 가방을 든 여성"),
            new Detail(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 6L, "사람", "/crops/object6.jpg", "손에 도구를 든 작업자"),
            new Detail(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 14L, "사람", "/crops/object14.jpg", "흰색 장갑을 낀 남성"),
            new Detail(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 17L, "사람", "/crops/object17.jpg", "노란 상의와 청바지를 입은 여성"),
            new Detail(1L, "/videos/video1.mp4", "Camera4", 37.5664, 126.9784, 18L, "사람", "/crops/object18.jpg", "보호구 없이 접근한 인물")
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
            "사람", "작업 구역 안에 서 있는 사람");

        SimpleWithMobileObject actual = mapper.findByMobileDetectionId(1L);

        assertThat(actual).isEqualTo(expected);
    }

}
