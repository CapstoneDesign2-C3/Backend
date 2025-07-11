package capstone.design.control_automation.video.repository;

import capstone.design.control_automation.video.repository.dto.VideoQueryResult;
import capstone.design.control_automation.video.repository.dto.VideoQueryResult.Detail;
import java.util.List;

public interface VideoRepository {

    List<Detail> findById(Long id);

    VideoQueryResult.SimpleWithMobileObject findByMobileDetectionId(Long mobileObjectId);

    VideoQueryResult.SimpleWithFixedObject findByFixedObjectId(Long id);
}
