package capstone.design.control_automation.camera.service;

import capstone.design.control_automation.camera.controller.dto.CameraRequest;
import capstone.design.control_automation.camera.controller.dto.CameraRequest.Filter;
import capstone.design.control_automation.camera.controller.dto.CameraResponse;
import capstone.design.control_automation.camera.controller.dto.CameraResponse.Info;
import capstone.design.control_automation.camera.entity.Camera;
import capstone.design.control_automation.camera.repository.CameraJpaRepository;
import capstone.design.control_automation.camera.repository.CameraRepository;
import capstone.design.control_automation.camera.repository.dto.CameraQueryResult;
import capstone.design.control_automation.common.exception.ErrorCode;
import capstone.design.control_automation.common.exception.ErrorException;
import capstone.design.control_automation.video.controller.dto.VideoResponse.Simple;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CameraService {

    private final CameraJpaRepository cameraJpaRepository;
    private final CameraRepository cameraRepository;

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
        return cameraRepository.findAllByFilterCondition(filter)
            .stream().map(CameraResponse.Position::from).toList();
    }

    public Info getCameraInfo(Long cameraId) {
        Map<Long, List<CameraQueryResult.Info>> cameraInfos = cameraRepository.getCameraWithVideos(cameraId).stream()
            .collect(Collectors.groupingBy(CameraQueryResult.Info::cameraId));

        List<Simple> videos = cameraInfos.values().stream()
            .map(infos -> infos.stream().map(Simple::from).toList()).findAny().get();

        CameraQueryResult.Info baseCamera = cameraInfos.get(cameraId).get(0);

        return new Info(
            baseCamera.scenery(),
            baseCamera.latitude(),
            baseCamera.longitude(),
            videos
        );
    }
}
