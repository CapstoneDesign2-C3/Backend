package capstone.design.control_automation.video.repository.dto;

import java.time.LocalDateTime;

public class VideoQueryResult {

    public record SimpleWithMobileObject(
        String videoUrl,
        String summary,
        String detectedObjectAlias,
        String detectedObjectCropUrl,
        LocalDateTime appearedTime,
        LocalDateTime exitTime,
        String categoryName,
        String feature
    ) {

    }

    public record SimpleWithFixedObject(
        String videoUrl,
        String summary,
        String detectedObjectAlias,
        LocalDateTime appearedTime,
        LocalDateTime exitTime,
        String categoryName
    ) {

    }

    public record Detail(
        Long videoId,
        String videoUrl,
        String cameraScenery,
        Double latitude,
        Double longitude,
        String summary,
        Long detectedObjectId,
        String categoryName,
        String cropImgUrl,
        String feature
    ) {

    }
}
