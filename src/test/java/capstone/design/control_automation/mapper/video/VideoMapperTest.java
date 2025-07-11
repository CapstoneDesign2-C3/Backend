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
            new Detail(1L, "https://example.com/video1.mp4", "광화문 교차로", 37.5665, 126.9780, "출근길 인파 포착", 1L, "사람", "https://example.com/crop1.jpg", "흰 셔츠를 입은 남성"),
            new Detail(1L, "https://example.com/video1.mp4", "광화문 교차로", 37.5665, 126.9780, "출근길 인파 포착", 11L, "사람", "https://example.com/crop11.jpg", "횡단보도 앞에 서 있는 사람"),
            new Detail(1L, "https://example.com/video1.mp4", "광화문 교차로", 37.5665, 126.9780, "출근길 인파 포착", 7L, "화재", "https://example.com/crop7.jpg", "건물 창문에서 화염 발생")
        );

        List<Detail> actual = mapper.findById(1L);

        assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    void findByMobileObjectId() {
        SimpleWithMobileObject expected = new SimpleWithMobileObject("https://example.com/video1.mp4", "b3f8b7d9-17c7-4af2-b262-2db13ae7e40c", null, "https://example.com/crop1.jpg", LocalDateTime.parse("2025-07-09 08:00:10", formatter), LocalDateTime.parse("2025-07-09 08:00:12", formatter), "사람", "흰 셔츠를 입은 남성");

        SimpleWithMobileObject actual = mapper.findByMobileDetectionId(1L);

        assertThat(actual).isEqualTo(expected);
    }

}
