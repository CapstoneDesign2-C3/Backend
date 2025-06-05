package capstone.design.control_automation.detected_object.dto;

public class DetectedObjectRequest {
    public record Upsert(String reId,
                         String feature,
                         int startFrame,
                         int endFrame,
                         String videoUrl,
                         Long cameraId,
                         String keyword) {
    }

    public record Search(String reId,
                         String feature,
                         Long cameraId,
                         String keyword) {
    }

}
