package capstone.design.control_automation.detection.controller.dto;

import java.time.LocalDateTime;

public class DetectionRequest {

    public record Filter(
        Long detectedObjectId,
        LocalDateTime startTime,
        LocalDateTime endTime
    ) {

    }
}
