package capstone.design.control_automation.detection.service;

import capstone.design.control_automation.common.onHold.NaturalLangSearchModel;
import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectRequest.FixedObjectFilter;
import capstone.design.control_automation.detection.controller.dto.DetectionRequest.Filter;
import capstone.design.control_automation.detection.controller.dto.DetectionResponse;
import capstone.design.control_automation.detection.controller.dto.DetectionResponse.Fixed;
import capstone.design.control_automation.detection.controller.dto.DetectionResponse.Position;
import capstone.design.control_automation.detection.controller.dto.DetectionResponse.Track;
import capstone.design.control_automation.detection.repository.DetectionJpaRepository;
import capstone.design.control_automation.detection.repository.DetectionRepository;
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

    private final DetectionJpaRepository detectionJpaRepository;
    private final DetectionRepository detectionRepository;
    private final NaturalLangSearchModel naturalLangSearchModel;

    public Page<Track> getTracksByFilterCondition(Filter filter, Pageable pageable) {
        return detectionRepository.getTracksByFilterCondition(filter, pageable)
            .map(Track::from);
    }

    public List<Position> getPositionsByFilterCondition(Filter filter) {
        return detectionRepository.getPositionsByFilterCondition(filter)
            .stream().map(Position::from).toList();
    }

    public Page<Fixed> getFixedDetectionByFilterCondition(FixedObjectFilter filter, Pageable pageable) {
        List<Long> fixedObjectIdBySearchInput = naturalLangSearchModel.findFixedObjectBySearchInput(filter.searchInput());

        return detectionRepository.findFixedDetectionsByFilterAndIds(filter, fixedObjectIdBySearchInput, pageable)
            .map(Fixed::from);
    }

}
