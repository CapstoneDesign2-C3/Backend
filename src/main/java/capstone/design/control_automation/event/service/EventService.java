package capstone.design.control_automation.event.service;

import capstone.design.control_automation.event.dto.EventRequest;
import capstone.design.control_automation.event.dto.EventResponse;
import capstone.design.control_automation.event.entity.Event;
import capstone.design.control_automation.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {
    private final EventRepository eventRepository;

    @Transactional
    public void createEvent(EventRequest eventRequest){
        Event event = new Event(eventRequest.status(), eventRequest.keyword(), eventRequest.isObject());

        eventRepository.save(event);
    }

    public List<EventResponse> getEvent(){
        return eventRepository.findAll().stream().map(Event::mapToResponse).toList();
    }

    @Transactional
    public void deleteEvent(Long id){
        eventRepository.deleteById(id);
    }

    public List<String> getKeywords(){
        return eventRepository.findDistinctKeywords();
    }
}
