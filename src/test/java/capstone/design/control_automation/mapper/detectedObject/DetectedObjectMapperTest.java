package capstone.design.control_automation.mapper.detectedObject;

import static org.junit.jupiter.api.Assertions.*;

import capstone.design.control_automation.common.config.MyBatisConfig;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

@MybatisTest
@Import(MyBatisConfig.class)
@ActiveProfiles("test")
class DetectedObjectMapperTest {

    @Autowired
    DetectedObjectMapper detectedObjectMapper;

    @Test
    void findFixedObjectCountByFilterAndIds() {
        detectedObjectMapper.findFixedObjectCountByFilterAndIds("category", "alias", List.of(1L, 2L, 3L, 4L));
    }

    @Test
    void findFixedObjectsByFilterAndIds() {
        detectedObjectMapper.findFixedObjectsByFilterAndIds("category", "alias", List.of(1L, 2L), 1, 1L);
    }
}
