package capstone.design.control_automation.video.repository.dto;

import java.time.LocalDateTime;

public class VideoQueryResult {

    public record SimpleWithMobileObject(
        String videoUrl,
        String detectedObjectUUID,
        String detectedObjectAlias,
        byte[] detectedObjectCropImg,
        LocalDateTime appearedTime,
        LocalDateTime exitTime,
        String categoryName
    ) {

    }

    public record SimpleWithEvent(
        String videoUrl,
        String eventUUID,
        LocalDateTime appearedTime,
        LocalDateTime exitTime,
        String eventCodeName,
        String eventRisk
    ) {

    }

    public record Detail(
        Long videoId,
        String videoUrl,
        String cameraScenery,
        Double latitude,
        Double longitude,
        Long detectedObjectId,
        String detectedObjectUUID,
        String categoryName,
        byte[] cropImg
    ) {

    }
}
