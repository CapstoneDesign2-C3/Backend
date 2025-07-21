package capstone.design.control_automation.event.controller.dto;

import java.time.LocalDateTime;

public class EventRequest {

    public record Filter(
        String eventCodeName,
        LocalDateTime startTime,
        LocalDateTime endTime
    ) {

    }
}
