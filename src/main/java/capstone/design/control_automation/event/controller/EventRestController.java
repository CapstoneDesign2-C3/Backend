package capstone.design.control_automation.event.controller;

import capstone.design.control_automation.event.controller.dto.EventRequest;
import capstone.design.control_automation.event.controller.dto.EventResponse.Code;
import capstone.design.control_automation.event.controller.dto.EventResponse.Info;
import capstone.design.control_automation.event.service.EventService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/event")
@RequiredArgsConstructor
public class EventRestController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Page<Info>> findEventsByFilter(
        @ModelAttribute EventRequest.Filter filter,
        Pageable pageable
    ) {
        return ResponseEntity.ok(eventService.findEventPageByFilter(filter, pageable));
    }

    @GetMapping("/code")
    public ResponseEntity<List<Code>> getEventCode() {
        return ResponseEntity.ok(eventService.findEventCodes());
    }

}
