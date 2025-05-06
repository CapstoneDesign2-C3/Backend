package capstone.design.control_automation.camera.dto;

import capstone.design.control_automation.camera.entity.CameraStatus;

public record CameraRequest(Double latitude, Double longitude, String angle, String address1, String address2, CameraStatus status) {
}
