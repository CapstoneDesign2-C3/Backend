package capstone.design.control_automation.event.repository;

import capstone.design.control_automation.event.controller.dto.EventRequest.Filter;
import capstone.design.control_automation.event.repository.dto.EventQueryResult;
import capstone.design.control_automation.event.repository.dto.EventQueryResult.Code;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventRepository {

    Page<EventQueryResult.Info> findEventPageByFilter(Filter filter, Pageable pageable);

    List<Code> findAllEventCodes();

    List<EventQueryResult.InfoForTable> findEventsByTimeRange(LocalDateTime startTime, LocalDateTime endTime);
}
