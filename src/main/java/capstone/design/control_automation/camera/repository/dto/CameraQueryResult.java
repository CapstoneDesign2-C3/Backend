package capstone.design.control_automation.camera.repository.dto;

import com.querydsl.core.annotations.QueryProjection;

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
}
