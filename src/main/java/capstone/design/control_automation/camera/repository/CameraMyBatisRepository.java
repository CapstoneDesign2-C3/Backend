package capstone.design.control_automation.camera.repository;

import capstone.design.control_automation.camera.controller.dto.CameraRequest.Filter;
import capstone.design.control_automation.camera.repository.dto.CameraQueryResult.Info;
import capstone.design.control_automation.camera.repository.dto.CameraQueryResult.Position;
import capstone.design.control_automation.mapper.camera.CameraMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
@RequiredArgsConstructor
public class CameraMyBatisRepository implements CameraRepository {

    private final CameraMapper cameraMapper;

    @Override
    public List<Position> findAllByFilterCondition(Filter filter) {
        return cameraMapper.findAllByFilterCondition(
            filter.topLeftLatitude(),
            filter.topLeftLongitude(),
            filter.bottomRightLatitude(),
            filter.bottomRightLongitude()
        );
    }

    @Override
    public List<Info> getCameraWithVideos(Long cameraId) {
        return cameraMapper.findWithVideosById(cameraId);
    }

}
