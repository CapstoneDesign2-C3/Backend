package capstone.design.control_automation.camera.controller.dto;

import capstone.design.control_automation.camera.repository.dto.CameraQueryResult;
import capstone.design.control_automation.video.controller.dto.VideoResponse;
import java.util.List;

public class CameraResponse {

    public record Position(Long id, Double latitude, Double longitude) {

        public static Position from(CameraQueryResult.Position cameraPosition) {
            return new Position(
                cameraPosition.id(),
                cameraPosition.latitude(),
                cameraPosition.longitude()
            );
        }
    }

    public record Info(
        String cameraScenery,
        Double cameraLatitude,
        Double cameraLongitude,
        List<VideoResponse.Simple> videos
    ) {

    }

}
