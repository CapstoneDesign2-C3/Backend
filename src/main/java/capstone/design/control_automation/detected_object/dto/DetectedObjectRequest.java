package capstone.design.control_automation.detected_object.dto;

public record DetectedObjectRequest(String reId,
                                    String feature,
                                    int startFrame,
                                    int endFrame,
                                    String videoUrl,
                                    Long cameraId,
                                    Long eventId) {
}
