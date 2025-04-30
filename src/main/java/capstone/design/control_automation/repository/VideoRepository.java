package capstone.design.control_automation.repository;

import capstone.design.control_automation.domain.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VideoRepository {

    Video getVideoById(Long videoId);

    Page<Video> getAllVideos(Pageable pageable);
}
