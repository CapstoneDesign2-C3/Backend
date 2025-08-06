package capstone.design.control_automation.mapper.dashboard;

import capstone.design.control_automation.common.PostgresContainerTest;
import capstone.design.control_automation.common.config.MyBatisConfig;
import capstone.design.control_automation.dashboard.repository.dto.DashboardQueryResult.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@MybatisTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Import(MyBatisConfig.class)
public class DashboardMapperTest extends PostgresContainerTest {
    @Autowired
    private DashboardMapper mapper;

    @Test
    @DisplayName("이벤트별 개수 테스트")
    void countByEventTest(){
        List<Pie> expected = List.of(
                new Pie("배회", 3),
                new Pie("사람", 0),
                new Pie("화재", 6),
                new Pie("안전조끼", 7),
                new Pie("위험구역 침입", 4)
                );

        List<Pie> result = mapper.countByEvent(LocalDate.of(2025,7,21));

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("시간별 개수 테스트")
    void countByTimeTest(){
        List<Data> expected = List.of(
                new Data(LocalDateTime.of(2025,7,21,0,0), 0),
                new Data(LocalDateTime.of(2025,7,21,1,0), 0),
                new Data(LocalDateTime.of(2025,7,21,2,0), 0),
                new Data(LocalDateTime.of(2025,7,21,3,0), 0),
                new Data(LocalDateTime.of(2025,7,21,4,0), 0),
                new Data(LocalDateTime.of(2025,7,21,5,0), 0),
                new Data(LocalDateTime.of(2025,7,21,6,0), 0),
                new Data(LocalDateTime.of(2025,7,21,7,0), 0),
                new Data(LocalDateTime.of(2025,7,21,8,0), 20),
                new Data(LocalDateTime.of(2025,7,21,9,0), 0),
                new Data(LocalDateTime.of(2025,7,21,10,0), 0),
                new Data(LocalDateTime.of(2025,7,21,11,0), 0),
                new Data(LocalDateTime.of(2025,7,21,12,0), 0),
                new Data(LocalDateTime.of(2025,7,21,13,0), 0),
                new Data(LocalDateTime.of(2025,7,21,14,0), 0),
                new Data(LocalDateTime.of(2025,7,21,15,0), 0),
                new Data(LocalDateTime.of(2025,7,21,16,0), 0),
                new Data(LocalDateTime.of(2025,7,21,17,0), 0),
                new Data(LocalDateTime.of(2025,7,21,18,0), 0),
                new Data(LocalDateTime.of(2025,7,21,19,0), 0),
                new Data(LocalDateTime.of(2025,7,21,20,0), 0),
                new Data(LocalDateTime.of(2025,7,21,21,0), 0),
                new Data(LocalDateTime.of(2025,7,21,22,0), 0),
                new Data(LocalDateTime.of(2025,7,21,23,0), 0)
        );

        List<Data> result = mapper.countByTime(LocalDate.of(2025,7,21));

        assertThat(expected).isEqualTo(result);
    }

    @Test
    @DisplayName("카메라별 개수 테스트")
    void countByCameraTest(){
        List<Bar> expected = List.of(
                new Bar("Camera1", 3),
                new Bar("Camera2", 6),
                new Bar("Camera3", 4),
                new Bar("Camera4", 6),
                new Bar("Camera5", 1)
        );

        List<Bar> result = mapper.countByCamera(LocalDate.of(2025,7,21));

        assertThat(expected).isEqualTo(result);
    }

    @Test
    @DisplayName("리스크별 개수 테스트")
    void countByRiskTest(){
        List<Bar> expected = List.of(
                new Bar("경고", 4),
                new Bar("안전", 0),
                new Bar("위험", 3),
                new Bar("주의", 13)
        );

        List<Bar> result = mapper.countByRisk(LocalDate.of(2025,7,21));

        assertThat(expected).isEqualTo(result);
    }
}
