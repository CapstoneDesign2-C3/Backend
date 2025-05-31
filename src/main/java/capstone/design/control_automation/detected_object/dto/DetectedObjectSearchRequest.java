package capstone.design.control_automation.detected_object.dto;

public record DetectedObjectSearchRequest(String reId, String feature, Long cameraId, Long eventId) {
}
