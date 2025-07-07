package capstone.design.control_automation.mapper.camera;

import capstone.design.control_automation.camera.controller.dto.CameraRequest.Filter;
import capstone.design.control_automation.camera.repository.dto.CameraQueryResult.Position;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CameraMapper {

    List<Position> findAllByFilterCondition(Double topLeftLatitude, Double topLeftLongitude, Double bottomRightLatitude, Double bottomRightLongitude);
}
