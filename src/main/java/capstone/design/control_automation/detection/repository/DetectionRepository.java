package capstone.design.control_automation.detection.repository;

import capstone.design.control_automation.detection.controller.dto.DetectionRequest.Filter;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Position;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Track;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DetectionRepository {

    Page<Track> getTracksByFilterCondition(Filter filter, Pageable pageable);

    List<Position> getPositionsByFilterCondition(Filter filter);

}
