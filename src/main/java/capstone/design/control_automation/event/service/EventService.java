package capstone.design.control_automation.event.service;

import capstone.design.control_automation.event.controller.dto.EventRequest.Filter;
import capstone.design.control_automation.event.controller.dto.EventResponse;
import capstone.design.control_automation.event.controller.dto.EventResponse.Code;
import capstone.design.control_automation.event.controller.dto.EventResponse.Info;
import capstone.design.control_automation.event.repository.EventRepository;
import capstone.design.control_automation.report.util.hwp.dto.TableDataDto;
import capstone.design.control_automation.report.util.hwp.dto.TableDataDto.EventCount;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {

    private final EventRepository eventRepository;

    public Page<Info> findEventPageByFilter(Filter filter, Pageable pageable) {
        return eventRepository.findEventPageByFilter(filter, pageable)
            .map(EventResponse.Info::from);
    }

    public List<Code> findEventCodes() {
        return eventRepository.findAllEventCodes().stream()
            .map(EventResponse.Code::from).toList();
    }

    public List<TableDataDto.EventInfo> findEventsByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return eventRepository.findEventsByTimeRange(startTime, endTime).stream()
            .map(TableDataDto.EventInfo::from).toList();
    }

    public List<EventCount> findEventCountsByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return eventRepository.findEventCountsByTimeRange(startTime, endTime).stream()
            .map(TableDataDto.EventCount::from).toList();
    }
}
