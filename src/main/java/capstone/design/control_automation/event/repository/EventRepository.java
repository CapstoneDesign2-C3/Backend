package capstone.design.control_automation.event.repository;

import capstone.design.control_automation.event.controller.dto.EventRequest.Filter;
import capstone.design.control_automation.event.repository.dto.EventQueryResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventRepository {

    Page<EventQueryResult.Info> findEventsByFilter(Filter filter, Pageable pageable);
}
