package capstone.design.control_automation.detection.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;

public class DetectionQueryResult {

    public record Track(
        Long detectionId,
        String cameraScenery,
        String thumbnailUrl,
        LocalDateTime appearedTime,
        LocalDateTime exitTime
    ) {

        @QueryProjection
        public Track {

        }
    }

    public record Position(
        Long detectionId,
        double latitudeY,
        double longitudeX
    ) {

        @QueryProjection
        public Position {

        }
    }

}
