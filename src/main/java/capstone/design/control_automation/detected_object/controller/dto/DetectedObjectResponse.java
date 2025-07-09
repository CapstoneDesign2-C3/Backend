package capstone.design.control_automation.detected_object.controller.dto;

import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult;
import capstone.design.control_automation.video.repository.dto.VideoQueryResult.Detail;

public class DetectedObjectResponse {

    // TODO - detection 으로 옮길 것
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

    public record Common(
        Long detectedObjectId,
        String categoryName,
        String cropImgUrl,
        String feature
    ) {

        public static Common of(Detail detail) {
            return new Common(
                detail.detectedObjectId(),
                detail.categoryName(),
                detail.cropImgUrl(),
                detail.feature()
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
