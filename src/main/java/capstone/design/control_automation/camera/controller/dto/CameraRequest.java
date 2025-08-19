package capstone.design.control_automation.camera.controller.dto;

import lombok.Getter;
import lombok.Setter;

public class CameraRequest {

    public record Upsert(
        Double latitude,
        Double longitude,
        String scenery
    ) {

    }

    public record Filter(
        Double topLeftLatitude,
        Double topLeftLongitude,
        Double bottomRightLatitude,
        Double bottomRightLongitude
    ) {

    }

    @Getter
    public static class Camera {
        @Setter
        private Long cameraId;
        private String cameraIP;
        private int cameraPort;
        private Double latitude;
        private Double longitude;
        private String locationName;
        private String locationAddress;
        private String locationAddressDetail;
        private String rtspID;
        private String rtspPassword;
        private String streamPath;
        private String cameraName;
        public Camera() {}
        public Camera(Long cameraId, String cameraIP, int cameraPort, Double latitude, Double longitude,
                      String locationName, String locationAddress, String locationAddressDetail,
                      String rtspID, String rtspPassword, String streamPath, String cameraName) {
            this.cameraId = cameraId;
            this.cameraIP = cameraIP;
            this.cameraPort = cameraPort;
            this.latitude = latitude;
            this.longitude = longitude;
            this.locationName = locationName;
            this.locationAddress = locationAddress;
            this.locationAddressDetail = locationAddressDetail;
            this.rtspID = rtspID;
            this.rtspPassword = rtspPassword;
            this.streamPath = streamPath;
            this.cameraName = cameraName;
        }

    }

}
