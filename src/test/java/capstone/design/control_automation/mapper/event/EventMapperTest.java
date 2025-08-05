package capstone.design.control_automation.mapper.event;

import static org.assertj.core.api.Assertions.assertThat;

import capstone.design.control_automation.common.PostgresContainerTest;
import capstone.design.control_automation.common.config.MyBatisConfig;
import capstone.design.control_automation.event.repository.dto.EventQueryResult.Code;
import capstone.design.control_automation.event.repository.dto.EventQueryResult.CountForTable;
import capstone.design.control_automation.event.repository.dto.EventQueryResult.Info;
import capstone.design.control_automation.event.repository.dto.EventQueryResult.InfoForTable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

@MybatisTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Import(MyBatisConfig.class)
public class EventMapperTest extends PostgresContainerTest {

    @Autowired
    private EventMapper mapper;

    private DateTimeFormatter formatter;
    @Autowired
    private EventMapper eventMapper;

    @BeforeEach
    void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    @DisplayName("filter 조건으로 eventCount 조회")
    void findEventCountByFilterTest() {
        //given
        Long expected = 6L;
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
                "/thumbs/thumb4.jpg",
                1L,
                "a9778202-6320-43c7-b4a4-404d03513921",
                "배회",
                LocalDateTime.parse("2025-07-21 08:00:00", formatter),
                LocalDateTime.parse("2025-07-21 08:02:47", formatter)
            ),
            new Info(
                38L,
                "/thumbs/thumb18.jpg",
                19L,
                "b57312ae-45d7-4b60-9e57-a8e47f7ac9ff",
                "배회",
                LocalDateTime.parse("2025-07-21 08:54:00", formatter),
                LocalDateTime.parse("2025-07-21 08:55:18", formatter)
            ),
            new Info(
                39L,
                "/thumbs/thumb19.jpg",
                17L,
                "461738b1-6a42-4900-9d19-2a6cec8c42d1",
                "배회",
                LocalDateTime.parse("2025-07-21 08:48:00", formatter),
                LocalDateTime.parse("2025-07-21 08:50:02", formatter)
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

    @Test
    @DisplayName("탐지 시작시간, 끝시간으로 Event 들 가져오기")
    void getEventsByTimeRange() {
        //given
        LocalDateTime startTime = LocalDateTime.parse("2025-07-21 08:00:00", formatter);
        LocalDateTime endTime = LocalDateTime.parse("2025-07-21 08:10:00", formatter);

        List<InfoForTable> expected = List.of(
            new InfoForTable(
                "a9778202-6320-43c7-b4a4-404d03513921",
                "배회",
                "Camera3",
                LocalDateTime.parse("2025-07-21 08:00:00", formatter),
                LocalDateTime.parse("2025-07-21 08:02:47", formatter)
            ),
            new InfoForTable(
                "337af53c-1922-44bd-aa77-ad8277b697fc",
                "화재",
                "Camera4",
                LocalDateTime.parse("2025-07-21 08:03:00", formatter),
                LocalDateTime.parse("2025-07-21 08:03:53", formatter)
            ),
            new InfoForTable(
                "6d7fac0a-1123-48f3-b127-fdb656cbbe3a",
                "안전조끼",
                "Camera2",
                LocalDateTime.parse("2025-07-21 08:06:00", formatter),
                LocalDateTime.parse("2025-07-21 08:07:21", formatter)
            ),
            new InfoForTable(
                "37c5c261-5e9a-4eb2-b4c0-074b6867f8b0",
                "안전조끼",
                "Camera2",
                LocalDateTime.parse("2025-07-21 08:09:00", formatter),
                LocalDateTime.parse("2025-07-21 08:10:32", formatter)
            )
        );
        //when

        List<InfoForTable> actual = eventMapper.getEventsByTimeRange(startTime, endTime);
        //then

        assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    @DisplayName("TimeRange 로 이벤트 종류별 수 가져오기")
    void getEventCountsByTimeRange() {
        //given
        LocalDateTime startTime = LocalDateTime.parse("2025-07-21 08:00:00", formatter);
        LocalDateTime endTime = LocalDateTime.parse("2025-07-21 08:30:00", formatter);

        List<CountForTable> expected = List.of(
            new CountForTable(
                "배회", 1
            ),
            new CountForTable(
                "화재", 3
            ),
            new CountForTable(
                "안전조끼", 6
            ),
            new CountForTable(
                "위험구역 침입", 1
            )
        );

        //when
        List<CountForTable> actual = eventMapper.getEventCountsByTimeRange(startTime, endTime);
        //then

        assertThat(actual).hasSameElementsAs(expected);
    }
}
