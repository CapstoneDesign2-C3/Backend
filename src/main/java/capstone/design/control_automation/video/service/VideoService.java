package capstone.design.control_automation.video.service;

import capstone.design.control_automation.video.dto.VideoRequest.Upsert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoService {

    public void saveVideo(Upsert upsert) {
    }

    public void deleteVideo(Long videoId) {
    }
}
