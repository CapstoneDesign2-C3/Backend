package capstone.design.control_automation.video.controller.dto;

import capstone.design.control_automation.camera.repository.dto.CameraQueryResult;
import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectResponse;
import capstone.design.control_automation.video.repository.dto.VideoQueryResult;
import java.time.LocalDateTime;
import java.util.List;

public class VideoResponse {

    public record SimpleWithMobileObject(
        String videoUrl,
        String detectedObjectUUID,
        String detectedObjectAlias,
        String detectedObjectCropUrl,
        LocalDateTime appearedTime,
        LocalDateTime exitTime,
        String categoryName,
        String feature
    ) {

    }

    public record SimpleWithEvent(
        String videoUrl,
        String summary,
        String eventUUID,
        LocalDateTime appearedTime,
        LocalDateTime exitTime,
        String eventCodeName,
        String eventRisk
    ) {

    }

    public record Detail(
        String videoUrl,
        String cameraScenery,
        Double latitude,
        Double longitude,
        List<DetectedObjectResponse.Common> detectedObjects
    ) {

    }

    public record Simple(
        Long videoId,
        String thumbnailUrl,
        LocalDateTime startTime,
        LocalDateTime endTime
    ) {

        public static Simple from(CameraQueryResult.Info info) {
            return new Simple(
                info.videoId(),
                info.videoThumbnailUrl(),
                info.startTime(),
                info.endTime()
            );
        }
    }
}
