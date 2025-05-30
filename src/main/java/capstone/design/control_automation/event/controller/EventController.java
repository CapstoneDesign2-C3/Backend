package capstone.design.control_automation.event.controller;

import capstone.design.control_automation.event.dto.EventRequest;
import capstone.design.control_automation.event.dto.EventResponse;
import capstone.design.control_automation.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping("/events")
    public String events(Model model){
        List<EventResponse> events = eventService.getEvent();

        model.addAttribute("events", events);
        return "events";
    }

    @PostMapping("/api/v1/event")
    public String createEvent(@ModelAttribute EventRequest eventRequest){
        eventService.createEvent(eventRequest);

        return "redirect:/events";
    }

    @PostMapping("/api/v1/event/delete")
    public String deleteCamera(@RequestParam Long id) {
        eventService.deleteEvent(id);

        return "redirect:/events";
    }
}
