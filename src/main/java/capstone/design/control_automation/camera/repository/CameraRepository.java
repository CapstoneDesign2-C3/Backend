package capstone.design.control_automation.camera.repository;

import capstone.design.control_automation.camera.controller.dto.CameraRequest;
import capstone.design.control_automation.camera.controller.dto.CameraRequest.Filter;
import capstone.design.control_automation.camera.repository.dto.CameraQueryResult.*;
import java.util.List;

public interface CameraRepository {

    List<Position> findAllByFilterCondition(Filter filter);

    List<Info> getCameraWithVideos(Long cameraId);

    List<Camera> getCameras();

    void insertCamera(CameraRequest.Camera camera);
}
