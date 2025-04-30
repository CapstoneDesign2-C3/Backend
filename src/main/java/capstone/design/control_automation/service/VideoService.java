package capstone.design.control_automation.service;

import capstone.design.control_automation.domain.entity.Video;
import capstone.design.control_automation.dto.SimpleVideo;
import capstone.design.control_automation.dto.VideoForm;
import capstone.design.control_automation.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoService {

    private final VideoRepository videoRepository;

    public VideoForm getVideoFormById(Long videoId) {
        Video video = videoRepository.getVideoById(videoId);
        return VideoForm.of(video);
    }

    public Page<SimpleVideo> getAllVideos(Pageable pageable) {
        return videoRepository.getAllVideos(pageable).map(SimpleVideo::of);
    }

}
