package capstone.design.control_automation.video.controller.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VideoRequest {

    public record Upsert(Long cameraId,
                         String summary,
                         String videoUrl,
                         LocalDateTime startTime,
                         String thumbnailUrl,
                         String status) {

    }

    public record Search(LocalDate startDate,
                         LocalDate endDate,
                         String eventType,
                         String cameraLocation,
                         String keyword) {

    }

}
