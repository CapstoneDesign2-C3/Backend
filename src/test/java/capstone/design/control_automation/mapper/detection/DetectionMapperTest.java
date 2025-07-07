package capstone.design.control_automation.mapper.detection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import capstone.design.control_automation.common.config.MyBatisConfig;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@MybatisTest
@Import(MyBatisConfig.class)
@ActiveProfiles("test")
class DetectionMapperTest {

    @Autowired
    DetectionMapper detectionMapper;

    @Test
    @DisplayName("getTrackCountOfDetectedObject")
    void getTrackCountOfDetectedObject() {
        detectionMapper.getTrackCountOfDetectedObject(1L, LocalDateTime.now().minusDays(1), LocalDateTime.now());
    }

    @Test
    void getTracksOfDetectedObject() {
        detectionMapper.getTracksOfDetectedObject(1L, LocalDateTime.now().minusDays(1), LocalDateTime.now(), 1, 1L);
    }

    @Test
    void getPositionsOfDetectedObject() {
        detectionMapper.getPositionsOfDetectedObject(1L, LocalDateTime.now().minusDays(1), LocalDateTime.now());
    }
}
