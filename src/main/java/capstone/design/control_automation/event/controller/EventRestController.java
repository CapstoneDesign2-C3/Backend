package capstone.design.control_automation.event.controller;

import capstone.design.control_automation.common.exception.ErrorCode;
import capstone.design.control_automation.event.dto.EventRequest;
import capstone.design.control_automation.event.dto.EventResponse;
import capstone.design.control_automation.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/event")
@RequiredArgsConstructor
public class EventRestController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventRequest eventRequest){
        eventService.createEvent(eventRequest);

        return ResponseEntity.ok(ErrorCode.OK.getMessage());
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getEvent(){
        return ResponseEntity.ok(eventService.getEvent());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEvent(@RequestBody Long id){
        eventService.deleteEvent(id);

        return ResponseEntity.ok(ErrorCode.OK.getMessage());
    }
}
