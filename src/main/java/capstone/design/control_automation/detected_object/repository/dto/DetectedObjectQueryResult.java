package capstone.design.control_automation.detected_object.repository.dto;

import com.querydsl.core.annotations.QueryProjection;

public class DetectedObjectQueryResult {

    public record MobileObject(
        Long mobileObjectId,
        String mobileObjectUuid,
        String categoryName,
        String cropImgUrl,
        String alias
    ) {

        @QueryProjection
        public MobileObject {

        }
    }
}
