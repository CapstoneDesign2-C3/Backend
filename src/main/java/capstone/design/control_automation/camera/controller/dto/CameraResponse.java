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

    public record Camera(Long cameraId,
                         String cameraIP,
                         int cameraPort,
                         Double latitude,
                         Double longitude,
                         String locationName,
                         String locationAddress,
                         String locationAddressDetail,
                         String rtspID,
                         String rtspPassword,
                         String streamPath,
                         String cameraName){
        public static Camera from(CameraQueryResult.Camera camera){
            return new Camera(camera.cameraId(),
                    camera.cameraIP(),
                    camera.cameraPort(),
                    camera.latitude(),
                    camera.longitude(),
                    camera.locationName(),
                    camera.locationAddress(),
                    camera.locationAddressDetail(),
                    camera.rtspID(),
                    camera.rtspPassword(),
                    camera.streamPath(),
                    camera.cameraName());
        }
    }
}
