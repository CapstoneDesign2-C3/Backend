package capstone.design.control_automation.detected_object.controller.dto;

import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult;

public class DetectedObjectResponse {

    public record FixedObject(
        Long detectedObjectId,
        String categoryName,
        String alias,
        String summary
    ) {

        public static FixedObject from(DetectedObjectQueryResult.FixedObject fixedObject) {
            return new FixedObject(
                fixedObject.detectedObjectId(),
                fixedObject.categoryName(),
                fixedObject.alias(),
                fixedObject.summary()
            );
        }
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
