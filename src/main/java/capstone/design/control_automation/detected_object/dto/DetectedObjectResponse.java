package capstone.design.control_automation.detected_object.dto;

public record DetectedObjectResponse(Long id,
                                     String reId,
                                     String feature,
                                     int startFrame,
                                     int endFrame,
                                     String videoUrl,
                                     Long cameraId,
                                     Long eventId) {
}
