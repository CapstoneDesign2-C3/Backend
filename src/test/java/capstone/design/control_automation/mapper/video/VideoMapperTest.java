package capstone.design.control_automation.mapper.video;

import capstone.design.control_automation.common.config.MyBatisConfig;
import capstone.design.control_automation.video.repository.dto.VideoQueryResult.Detail;
import capstone.design.control_automation.video.repository.dto.VideoQueryResult.SimpleWithFixedObject;
import capstone.design.control_automation.video.repository.dto.VideoQueryResult.SimpleWithMobileObject;
import java.util.List;
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
        List<Detail> byId = mapper.findById(1L);
    }

    @Test
    void findByMobileObjectId() {
        SimpleWithMobileObject byMobileObjectId = mapper.findByMobileObjectId(1L);
    }

    @Test
    void findByFixedObjectId() {
        SimpleWithFixedObject byFixedObjectId = mapper.findByFixedObjectId(1L);
    }
}
