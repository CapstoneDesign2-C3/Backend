package capstone.design.control_automation.mapper.video;

import capstone.design.control_automation.video.repository.dto.VideoQueryResult;
import capstone.design.control_automation.video.repository.dto.VideoQueryResult.Detail;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VideoMapper {

    List<Detail> findById(Long id);

    VideoQueryResult.SimpleWithMobileObject findByMobileDetectionId(Long detectionId);

    VideoQueryResult.SimpleWithEvent findByEventId(Long eventId);
}
