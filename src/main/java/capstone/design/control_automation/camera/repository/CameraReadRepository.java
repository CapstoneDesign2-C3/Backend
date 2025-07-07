package capstone.design.control_automation.camera.repository;

import capstone.design.control_automation.camera.controller.dto.CameraRequest.Filter;
import capstone.design.control_automation.camera.repository.dto.CameraQueryResult.Position;
import java.util.List;
import org.springframework.stereotype.Repository;

public interface CameraReadRepository {

    List<Position> findAllByFilterCondition(Filter filter);
}
