package capstone.design.control_automation.video.repository;

import capstone.design.control_automation.mapper.video.VideoMapper;
import capstone.design.control_automation.video.repository.dto.VideoQueryResult.Detail;
import capstone.design.control_automation.video.repository.dto.VideoQueryResult.SimpleWithEvent;
import capstone.design.control_automation.video.repository.dto.VideoQueryResult.SimpleWithMobileObject;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
@RequiredArgsConstructor
public class VideoMyBatisRepository implements VideoRepository {

    private final VideoMapper videoMapper;

    @Override
    public List<Detail> findById(Long id) {
        return videoMapper.findById(id);
    }

    @Override
    public SimpleWithMobileObject findByMobileDetectionId(Long mobileDetectionId) {
        return videoMapper.findByMobileDetectionId(mobileDetectionId);
    }

    @Override
    public SimpleWithEvent findByEventId(Long eventId) {
        return videoMapper.findByEventId(eventId);
    }


}
