package capstone.design.control_automation.mapper.camera;

import static org.junit.jupiter.api.Assertions.*;

import capstone.design.control_automation.camera.controller.dto.CameraRequest;
import capstone.design.control_automation.camera.controller.dto.CameraRequest.Filter;
import capstone.design.control_automation.common.config.MyBatisConfig;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@MybatisTest
@Import(MyBatisConfig.class)
@ActiveProfiles("test")
class CameraMapperTest {

    @Autowired
    CameraMapper cameraMapper;

    @Test
    void findAllByFilterCondition() {
        cameraMapper.findAllByFilterCondition(30.0, 0.0, 10.0, 100.0);
    }
}
