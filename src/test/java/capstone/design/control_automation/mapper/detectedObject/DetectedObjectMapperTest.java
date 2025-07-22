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
        Long expected = 20L;
        Long actual = detectedObjectMapper.findMobileObjectCountByFilterAndIds("사람", null);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findMobileObjectsByFilterAndIds() {
        List<MobileObject> expected = List.of(
            new MobileObject(1L, "사람", "/crops/object1.jpg", "Object1", "노란색 헬멧을 쓴 남성"),
            new MobileObject(2L, "사람", "/crops/object2.jpg", "Object2", "형광 조끼를 입은 작업자"),
            new MobileObject(3L, "사람", "/crops/object3.jpg", "Object3", "검정색 가방을 든 여성"),
            new MobileObject(4L, "사람", "/crops/object4.jpg", "Object4", "하얀 모자를 쓴 노인"),
            new MobileObject(5L, "사람", "/crops/object5.jpg", "Object5", "파란 유니폼을 입은 남성")
        );
        List<MobileObject> actual = detectedObjectMapper.findMobileObjectsByFilterAndIds("사람", null,5, 0L);

        assertThat(actual).hasSameElementsAs(expected);
    }

}
