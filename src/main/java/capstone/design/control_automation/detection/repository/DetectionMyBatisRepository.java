package capstone.design.control_automation.detection.repository;

import capstone.design.control_automation.detection.controller.dto.DetectionRequest.Filter;
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
        Long count = detectionMapper.getTrackCountOfMobileObject(filter.detectedObjectId(), filter.startTime(),
            filter.endTime());

        if (count == 0L) {
            return Page.empty();
        }

        List<Track> tracks = detectionMapper.getTracksOfMobileObject(
            filter.detectedObjectId(),
            filter.startTime(),
            filter.endTime(),
            pageable.getPageSize(),
            pageable.getOffset()
        );

        return new PageImpl<>(tracks, pageable, count);
    }

    @Override
    public List<Track> getRecent10TracksByMobileObjectId(Long mobileObjectId) {
        return detectionMapper.getTracksOfMobileObject(mobileObjectId, null, null, 10, null);
    }

    @Override
    public List<Position> getPositionsByFilterCondition(Filter filter) {
        return detectionMapper.getPositionsOfMobileObject(
            filter.detectedObjectId(),
            filter.startTime(),
            filter.endTime(),
            filter.count()
        );
    }

}
