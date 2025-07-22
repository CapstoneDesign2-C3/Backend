package capstone.design.control_automation.detected_object.controller.dto;

public class DetectedObjectRequest {

    public record Create(
        String alias,
        String cropImgUrl,
        Long categoryId,
        String feature
    ) {

    }

    public record FixedObjectFilter(
        String categoryName,
        String alias,
        String searchInput
    ) {

    }

    public record MobileObjectFilter(
        String categoryName,
        String alias
    ) {

    }
}
