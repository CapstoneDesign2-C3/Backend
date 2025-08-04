package capstone.design.control_automation.detected_object.controller.dto;

public class DetectedObjectRequest {

    public record MobileObjectFilter(
        String categoryName,
        String alias
    ) {

    }
}
