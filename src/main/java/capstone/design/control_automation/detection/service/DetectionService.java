package capstone.design.control_automation.detection.service;

import capstone.design.control_automation.detection.controller.dto.DetectionRequest.Filter;
import capstone.design.control_automation.detection.controller.dto.DetectionResponse.Track;
import capstone.design.control_automation.detection.repository.DetectionRepository;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Position;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetectionService {

    private final DetectionRepository detectionRepository;

    public Page<Track> getTracksByFilterCondition(Filter filter, Pageable pageable) {
        return detectionRepository.getTracksByFilterCondition(filter, pageable)
            .map(Track::from);
    }

    public List<Position> getPositionsByFilterCondition(Filter filter) {
        return detectionRepository.getPositionsByFilterCondition(filter);
    }

    public List<DetectionQueryResult.Track> getTracksByMobileObjectId(Long id) {
        return detectionRepository.getTracksByMobileObjectId(id);
    }
}
