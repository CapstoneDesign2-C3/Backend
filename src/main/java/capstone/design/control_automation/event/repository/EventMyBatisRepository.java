package capstone.design.control_automation.event.repository;

import capstone.design.control_automation.event.controller.dto.EventRequest.Filter;
import capstone.design.control_automation.event.repository.dto.EventQueryResult;
import capstone.design.control_automation.mapper.event.EventMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventMyBatisRepository implements EventRepository {

    private final EventMapper eventMapper;

    @Override
    public Page<EventQueryResult.Info> findEventsByFilter(Filter filter, Pageable pageable) {

        Long count = eventMapper.getEventCountByFilter(
            filter.eventCodeName(),
            filter.startTime(),
            filter.endTime()
        );

        if (count == 0) {
            return Page.empty();
        }

        List<EventQueryResult.Info> eventInfos = eventMapper.getEventsByFilter(
            filter.eventCodeName(),
            filter.startTime(),
            filter.endTime(),
            pageable.getPageSize(),
            pageable.getOffset()
        );

        return new PageImpl<>(eventInfos, pageable, count);
    }
}
