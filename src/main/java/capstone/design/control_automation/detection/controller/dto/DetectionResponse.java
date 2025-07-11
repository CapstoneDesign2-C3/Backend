package capstone.design.control_automation.detection.controller.dto;

import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult;
import java.time.LocalDateTime;

public class DetectionResponse {

    public record Position(
        Long videoId,
        double latitudeY,
        double longitudeX
    ) {

        public static Position from(DetectionQueryResult.Position position) {
            return new Position(
                position.videoId(),
                position.latitudeY(),
                position.longitudeX()
            );
        }
    }

    public record Track(
        Long videoId,
        String thumbnailUrl,
        String summary,
        LocalDateTime appearedTime,
        LocalDateTime exitTime
    ) {

        public static Track from(DetectionQueryResult.Track track) {
            return new Track(
                track.videoId(),
                track.thumbnailUrl(),
                track.summary(),
                track.appearedTime(),
                track.exitTime()
            );
        }
    }

    public record Fixed(
        Long videoId,
        String categoryName,
        String alias,
        String summary
    ) {

        public static Fixed from(DetectedObjectQueryResult.FixedObject fixedObject) {
            return new Fixed(
                fixedObject.videoId(),
                fixedObject.categoryName(),
                fixedObject.alias(),
                fixedObject.summary()
            );
        }
    }
}
