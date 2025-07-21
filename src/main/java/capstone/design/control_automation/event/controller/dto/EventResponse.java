package capstone.design.control_automation.event.controller.dto;

import capstone.design.control_automation.event.repository.dto.EventQueryResult;
import capstone.design.control_automation.event.repository.dto.EventQueryResult.Code;
import java.time.LocalDateTime;

public class EventResponse {

    public record Info(
        Long videoId,
        String eventUUID,
        String eventCodeName,
        LocalDateTime appearedTime,
        LocalDateTime exitTime
    ) {

        public static Info from(EventQueryResult.Info info) {
            return new Info(
                info.videoId(),
                info.eventUUID(),
                info.eventCodeName(),
                info.appearedTime(),
                info.exitTime()
            );
        }
    }

    public record Code(
        Long eventCodeId,
        String eventCodeName
    ) {

        public static EventResponse.Code from(EventQueryResult.Code code) {
            return new Code(
                code.eventCodeId(),
                code.eventCodeName()
            );
        }
    }
}
