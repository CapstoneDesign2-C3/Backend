package capstone.design.control_automation.camera.repository;

import capstone.design.control_automation.camera.controller.dto.CameraRequest.Filter;
import capstone.design.control_automation.camera.repository.dto.CameraQueryResult.Position;
import java.util.List;

public interface CameraRepository {

    List<Position> findAllByFilterCondition(Filter filter);
}
