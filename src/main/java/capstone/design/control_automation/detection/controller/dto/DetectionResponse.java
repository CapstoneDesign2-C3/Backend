package capstone.design.control_automation.detection.controller.dto;

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
        LocalDateTime discoveredTime
    ) {

        public static Track from(DetectionQueryResult.Track track) {
            return new Track(
                track.videoId(),
                track.thumbnailUrl(),
                track.summary(),
                track.appearedTime(),
                track.discoveredTime()
            );
        }
    }

    public record Fixed(
        Long detectionId,
        String categoryName,
        String alias,
        String summary
    ) {

    }
}
