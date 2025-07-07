package capstone.design.control_automation.camera.controller.dto;

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
}
