package capstone.design.control_automation.video.dto;

import java.time.LocalDate;

public record VideoSearchRequest(LocalDate startDate,
                                 LocalDate endDate,
                                 String eventType,
                                 String cameraLocation,
                                 String keyword) {
}
