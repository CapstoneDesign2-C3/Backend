package capstone.design.control_automation.mapper.camera;

import capstone.design.control_automation.camera.repository.dto.CameraQueryResult.Info;
import capstone.design.control_automation.camera.repository.dto.CameraQueryResult.Position;
import capstone.design.control_automation.common.config.MyBatisConfig;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@MybatisTest
@Import(MyBatisConfig.class)
@ActiveProfiles("test")
class CameraMapperTest {

    @Autowired
    CameraMapper cameraMapper;

    @Test
    void findAllByFilterCondition() {
        List<Position> allByFilterCondition = cameraMapper.findAllByFilterCondition(30.0, 0.0, 10.0, 100.0);
    }

    @Test
    void findWithVideosById() {
        List<Info> withVideosById = cameraMapper.findWithVideosById(1L);
    }
}
