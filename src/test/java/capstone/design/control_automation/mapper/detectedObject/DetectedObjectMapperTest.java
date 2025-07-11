package capstone.design.control_automation.mapper.detectedObject;

import static org.assertj.core.api.Assertions.assertThat;

import capstone.design.control_automation.common.config.MyBatisConfig;
import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult.MobileObject;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@MybatisTest
@Import(MyBatisConfig.class)
@ActiveProfiles("test")
class DetectedObjectMapperTest {

    @Autowired
    DetectedObjectMapper detectedObjectMapper;

    @Test
    void findMobileObjectCountByFilterAndIds() {
        Long expected = 2L;
        Long actual = detectedObjectMapper.findMobileObjectCountByFilterAndIds("사람", null,
            List.of(1L, 2L, 3L, 4L));

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findMobileObjectsByFilterAndIds() {
        List<MobileObject> expected = List.of(
            new MobileObject(5L, "오토바이", "https://example.com/crop5.jpg", null, "헬멧을 쓴 오토바이 운전자"),
            new MobileObject(6L, "오토바이", "https://example.com/crop6.jpg", null, "주차된 오토바이")
        );
        List<MobileObject> actual = detectedObjectMapper.findMobileObjectsByFilterAndIds("오토바이", "",
            List.of(1L, 2L, 3L, 4L, 5L, 6L), 5, 1L);
    }

}
