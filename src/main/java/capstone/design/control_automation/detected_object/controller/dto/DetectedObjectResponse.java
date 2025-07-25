package capstone.design.control_automation.detected_object.controller.dto;

public class DetectedObjectResponse {

    public record Common(
        Long detectedObjectId,
        String categoryName,
        String cropImgUrl,
        String feature
    ) {

    }

    public record MobileObject(
        Long detectedObjectId,
        String categoryName,
        String cropImgUrl,
        String alias,
        String feature
    ) {

    }
}
