package capstone.design.control_automation.mapper.detectedObject;

import capstone.design.control_automation.common.config.MyBatisConfig;
import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult.FixedObject;
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
    void findFixedObjectCountByFilterAndIds() {
        Long fixedObjectCountByFilterAndIds = detectedObjectMapper.findFixedObjectCountByFilterAndIds("category", "alias",
            List.of(1L, 2L, 3L, 4L));
    }

    @Test
    void findFixedObjectsByFilterAndIds() {
        List<FixedObject> fixedObjects = detectedObjectMapper.findFixedObjectsByFilterAndIds("category", "alias", List.of(1L, 2L),
            1, 1L);
    }

    @Test
    void findMobileObjectCountByFilterAndIds() {
        Long mobileObjectCountByFilterAndIds = detectedObjectMapper.findMobileObjectCountByFilterAndIds("category", "alias",
            List.of(1L, 2L, 3L, 4L));
    }

    @Test
    void findMobileObjectsByFilterAndIds() {
        List<MobileObject> mobileObjectsByFilterAndIds = detectedObjectMapper.findMobileObjectsByFilterAndIds("category", "alias",
            List.of(1L, 2L), 1, 1L);
    }

}
