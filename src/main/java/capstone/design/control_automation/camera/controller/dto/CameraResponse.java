package capstone.design.control_automation.camera.controller.dto;

import capstone.design.control_automation.camera.repository.dto.CameraQueryResult;

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

}
