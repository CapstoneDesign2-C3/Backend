package capstone.design.control_automation.detection.controller.dto;

import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult;
import java.time.LocalDateTime;
import java.util.Base64;

public class DetectionResponse {

    public record Position(
        Long detectionId,
        double latitudeY,
        double longitudeX
    ) {

        public static Position from(DetectionQueryResult.Position position) {
            return new Position(
                position.detectionId(),
                position.latitudeY(),
                position.longitudeX()
            );
        }
    }

    public record Track(
        Long detectionId,
        String cameraScenery,
        String detectionCropImg,
        LocalDateTime appearedTime,
        LocalDateTime exitTime
    ) {

        public static Track from(DetectionQueryResult.Track track) {
            return new Track(
                track.detectionId(),
                track.cameraScenery(),
                Base64.getEncoder().encodeToString(track.detectionCropImg()),
                track.appearedTime(),
                track.exitTime()
            );
        }
    }

}
