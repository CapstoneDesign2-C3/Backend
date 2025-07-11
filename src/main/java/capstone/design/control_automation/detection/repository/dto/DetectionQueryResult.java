package capstone.design.control_automation.detection.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;

public class DetectionQueryResult {
    public record Track(
        Long videoId,
        String thumbnailUrl,
        String summary,
        LocalDateTime appearedTime,
        LocalDateTime exitTime
    ) {
        @QueryProjection
        public Track {

        }
    }

    public record Position(
        Long videoId,
        double latitudeY,
        double longitudeX
    ) {

        @QueryProjection
        public Position {

        }
    }

}
