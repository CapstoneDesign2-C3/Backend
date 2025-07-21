package capstone.design.control_automation.mapper.event;

import static org.assertj.core.api.Assertions.assertThat;

import capstone.design.control_automation.common.config.MyBatisConfig;
import capstone.design.control_automation.event.repository.dto.EventQueryResult.Code;
import capstone.design.control_automation.event.repository.dto.EventQueryResult.Info;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@MybatisTest
@Import(MyBatisConfig.class)
@ActiveProfiles("test")
public class EventMapperTest {

    @Autowired
    private EventMapper mapper;

    private DateTimeFormatter formatter;

    @BeforeEach
    void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    @DisplayName("filter 조건으로 eventCount 조회")
    void findEventCountByFilterTest() {
        //given
        Long expected = 5L;
        //when
        Long actual = mapper.getEventCountByFilter(
            "화재",
            LocalDateTime.parse("2025-07-21 08:00:00", formatter),
            LocalDateTime.parse("2025-07-21 09:00:00", formatter)
        );
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("filter 조건으로 events 조회")
    void findEventsByFilterTest() {
        //given
        List<Info> expected = List.of(
            new Info(
                24L,
                "a9778202-6320-43c7-b4a4-404d03513921",
                "배회",
                LocalDateTime.parse("2025-07-21 08:00:00", formatter),
                LocalDateTime.parse("2025-07-21 08:02:47", formatter)
            ),
            new Info(
                38L,
                "b57312ae-45d7-4b60-9e57-a8e47f7ac9ff",
                "배회",
                LocalDateTime.parse("2025-07-21 08:54:00", formatter),
                LocalDateTime.parse("2025-07-21 08:55:18", formatter)
            )
        );
        //when
        List<Info> actual = mapper.getEventsByFilter(
            "배회",
            null,
            null,
            5, 0L
        );
        //then
        assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    @DisplayName("모든 이벤트 코드 조회")
    void getAllEventCodesTest() {
        //given
        List<Code> expected = List.of(
            new Code(1L, "안전조끼"),
            new Code(2L, "위험구역 침입"),
            new Code(3L, "배회"),
            new Code(4L, "화재"),
            new Code(5L, "사람")
        );
        //when
        List<Code> actual = mapper.getAllEventCodes();
        //then

        assertThat(actual).hasSameElementsAs(expected);
    }
}
