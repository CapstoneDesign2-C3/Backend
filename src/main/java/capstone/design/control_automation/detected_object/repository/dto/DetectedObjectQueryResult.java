package capstone.design.control_automation.detected_object.repository.dto;

import com.querydsl.core.annotations.QueryProjection;

public class DetectedObjectQueryResult {

    public record FixedObject(
        Long videoId,
        String videoUrl,
        String categoryName,
        String alias,
        String summary
    ) {

        @QueryProjection
        public FixedObject {

        }
    }

    public record MobileObject(
        Long detectedObjectId,
        String categoryName,
        String cropImgUrl,
        String alias,
        String feature
    ) {

        @QueryProjection
        public MobileObject {

        }
    }
}
