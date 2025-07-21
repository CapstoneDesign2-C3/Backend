package capstone.design.control_automation.mapper.event;

import capstone.design.control_automation.event.repository.dto.EventQueryResult;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EventMapper {

    Long getEventCountByFilter(
        String eventCodeName,
        LocalDateTime startTime,
        LocalDateTime endTime
    );

    List<EventQueryResult.Info> getEventsByFilter(
        String eventCodeName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Integer pageSize, Long offset);
}
