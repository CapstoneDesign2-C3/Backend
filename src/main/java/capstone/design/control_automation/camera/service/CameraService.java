package capstone.design.control_automation.camera.service;

import capstone.design.control_automation.camera.controller.dto.CameraRequest.Filter;
import capstone.design.control_automation.camera.controller.dto.CameraResponse.*;
import capstone.design.control_automation.camera.repository.CameraRepository;
import capstone.design.control_automation.camera.repository.dto.CameraQueryResult;
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

    private final CameraRepository cameraRepository;

    public List<Position> getCameraPositionByFilterCondition(Filter filter) {
        return cameraRepository.findAllByFilterCondition(filter)
            .stream().map(Position::from).toList();
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

    public List<Camera> getCameras(){
        return cameraRepository.getCameras().stream().map(Camera::from).toList();
    }
}
