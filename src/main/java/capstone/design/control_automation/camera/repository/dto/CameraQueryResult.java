package capstone.design.control_automation.camera.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;

public class CameraQueryResult {

    public record Position(
        Long id,
        Double latitude,
        Double longitude
    ) {

        @QueryProjection
        public Position {
        }
    }

    public record Info(
        Long cameraId,
        String scenery,
        Double latitude,
        Double longitude,
        Long videoId,
        String videoUrl,
        LocalDateTime startTime,
        LocalDateTime endTime
    ) {

    }
}
