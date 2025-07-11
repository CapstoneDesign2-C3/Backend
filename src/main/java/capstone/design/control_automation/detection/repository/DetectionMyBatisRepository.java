package capstone.design.control_automation.detection.repository;

import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectRequest.FixedObjectFilter;
import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult;
import capstone.design.control_automation.detection.controller.dto.DetectionRequest.Filter;
import capstone.design.control_automation.detection.controller.dto.DetectionResponse.Fixed;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Position;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Track;
import capstone.design.control_automation.mapper.detection.DetectionMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Primary
@Repository
@RequiredArgsConstructor
public class DetectionMyBatisRepository implements DetectionRepository {

    private final DetectionMapper detectionMapper;

    @Override
    public Page<Track> getTracksByFilterCondition(Filter filter, Pageable pageable) {
        Long count = detectionMapper.getTrackCountOfDetectedObject(filter.detectedObjectId(), filter.startTime(),
            filter.endTime());

        if (count == 0L) {
            return Page.empty();
        }

        List<Track> tracks = detectionMapper.getTracksOfDetectedObject(
            filter.detectedObjectId(),
            filter.startTime(),
            filter.endTime(),
            pageable.getPageSize(),
            pageable.getOffset()
        );

        return new PageImpl<>(tracks, pageable, count);
    }

    @Override
    public List<Position> getPositionsByFilterCondition(Filter filter) {
        return detectionMapper.getPositionsOfDetectedObject(
            filter.detectedObjectId(),
            filter.startTime(),
            filter.endTime()
        );
    }

    @Override
    public Page<DetectedObjectQueryResult.FixedObject> findFixedDetectionsByFilterAndIds(
        FixedObjectFilter filter,
        List<Long> fixedObjectIds,
        Pageable pageable
    ) {
        Long count = detectionMapper.getFixedDetectionCountByFilterAndIds(filter.categoryName(), filter.alias(), fixedObjectIds);

        if (count == 0L) {
            return Page.empty();
        }

        List<DetectedObjectQueryResult.FixedObject> fixedObjects = detectionMapper.getFixedDetectionsByFilterAndIds(
            filter.categoryName(),
            filter.alias(),
            fixedObjectIds,
            pageable.getPageSize(),
            pageable.getOffset()
        );

        return new PageImpl<>(fixedObjects, pageable, count);
    }
}
