package capstone.design.control_automation.mapper.video;

import static org.junit.jupiter.api.Assertions.*;

import capstone.design.control_automation.common.config.MyBatisConfig;
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

    @Test
    void findById() {
        mapper.findById(1L);
    }

    @Test
    void findByMobileObjectId() {
        mapper.findByMobileObjectId(1L);
    }

    @Test
    void findByFixedObjectId() {
        mapper.findByFixedObjectId(1L);
    }
}
