package capstone.design.control_automation.mapper.detection;

import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Position;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Track;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DetectionMapper {

    Long getTrackCountOfDetectedObject(Long detectedObjectId, LocalDateTime startTime, LocalDateTime endTime);

    List<Track> getTracksOfDetectedObject(Long detectedObjectId, LocalDateTime startTime, LocalDateTime endTime, Integer pageSize, Long offset);

    List<Position> getPositionsOfDetectedObject(Long detectedObjectId, LocalDateTime startTime, LocalDateTime endTime);
}
