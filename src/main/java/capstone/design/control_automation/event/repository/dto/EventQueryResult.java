package capstone.design.control_automation.event.repository.dto;

import java.time.LocalDateTime;

public class EventQueryResult {

    public record Info(
        Long videoId,
        String videoThumbnailUrl,
        Long eventId,
        String eventUUID,
        String eventCodeName,
        LocalDateTime appearedTime,
        LocalDateTime exitTime
    ) {

    }

    public record Code(
        Long eventCodeId,
        String eventCodeName
    ) {

    }

    public record InfoForTable(
        String uuid,
        String eventType,
        String detectedPlace,
        LocalDateTime startFrameAt,
        LocalDateTime endFrameAt
    ) {

    }
}
