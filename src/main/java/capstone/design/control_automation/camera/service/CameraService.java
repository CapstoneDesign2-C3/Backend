package capstone.design.control_automation.camera.service;

import capstone.design.control_automation.camera.controller.dto.CameraRequest;
import capstone.design.control_automation.camera.controller.dto.CameraRequest.Filter;
import capstone.design.control_automation.camera.controller.dto.CameraResponse;
import capstone.design.control_automation.camera.entity.Camera;
import capstone.design.control_automation.camera.repository.CameraJpaRepository;
import capstone.design.control_automation.camera.repository.CameraReadRepository;
import capstone.design.control_automation.common.exception.ErrorCode;
import capstone.design.control_automation.common.exception.ErrorException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CameraService {

    private final CameraJpaRepository cameraJpaRepository;
    private final CameraReadRepository cameraReadRepository;

    @Transactional
    public Long createCamera(CameraRequest.Upsert upsert) {
        Camera camera = new Camera(upsert.latitude(), upsert.longitude(), upsert.scenery());

        cameraJpaRepository.save(camera);

        return camera.getId();
    }

    @Transactional
    public void updateCamera(Long cameraId, CameraRequest.Upsert upsert) {
        Camera camera = cameraJpaRepository.findById(cameraId)
            .orElseThrow(() -> new ErrorException(ErrorCode.CAMERA_NOT_FOUND));

        camera.updateInfo(
            upsert.latitude(),
            upsert.longitude(),
            upsert.scenery()
        );
    }

    @Transactional
    public void deleteCamera(Long id) {
        cameraJpaRepository.deleteById(id);
    }

    public List<CameraResponse.Position> getCameraPositionByFilterCondition(Filter filter) {
        return cameraReadRepository.findAllByFilterCondition(filter)
            .stream().map(CameraResponse.Position::from).toList();
    }

}
