package capstone.design.control_automation.mapper.detection;

import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Position;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Track;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DetectionMapper {

    Long getTrackCountOfMobileObject(Long mobileObjectId, LocalDateTime startTime, LocalDateTime endTime);

    List<Track> getTracksOfMobileObject(Long mobileObjectId, LocalDateTime startTime, LocalDateTime endTime, Integer pageSize,
        Long offset);

    List<Position> getPositionsOfMobileObject(Long mobileObjectId, LocalDateTime startTime, LocalDateTime endTime);
}
