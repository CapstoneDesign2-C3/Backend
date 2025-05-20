package capstone.design.control_automation.video.dto;

import java.time.LocalDateTime;

public record VideoRequest(String summary, String videoUrl, LocalDateTime startTime, LocalDateTime endTime, String thumbnailUrl) {

}
