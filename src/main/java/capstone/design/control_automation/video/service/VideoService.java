package capstone.design.control_automation.video.service;

import capstone.design.control_automation.common.exception.ErrorCode;
import capstone.design.control_automation.common.exception.ErrorException;
import capstone.design.control_automation.video.document.VideoDocument;
import capstone.design.control_automation.video.entity.Video;
import capstone.design.control_automation.video.dto.SimpleVideo;
import capstone.design.control_automation.video.dto.VideoForm;
import capstone.design.control_automation.video.dto.VideoRequest;
import capstone.design.control_automation.video.dto.VideoResponse;
import capstone.design.control_automation.video.repository.VideoElastic;
import capstone.design.control_automation.video.repository.VideoRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoElastic videoElastic;
    private final VideoRepository videoRepository;

    @Transactional
    public void saveVideo(VideoRequest videoRequest) {
        Video video = new Video(videoRequest.summary(), videoRequest.videoUrl(), LocalDateTime.now(), null);

        videoRepository.save(video);

        VideoDocument videoDocument = new VideoDocument(video.getId().toString(), video.getSummary());
        videoElastic.save(videoDocument);
    }

    @Transactional
    public void deleteVideo(Long videoId) {
        videoRepository.deleteById(videoId);
        videoElastic.deleteById(videoId.toString());
    }

    @Transactional(readOnly = true)
    public List<VideoResponse> findVideos(String keyword) {
        List<VideoDocument> videoDocuments = videoElastic.findBySummaryContaining(keyword);

        return videoDocuments.stream().map(VideoDocument::mapToResponse).toList();
    }

    @Transactional(readOnly = true)
    public VideoForm getVideoFormById(Long videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new ErrorException(ErrorCode.VIDEO_NOT_FOUND));

        return VideoForm.of(video);
    }

    @Transactional(readOnly = true)
    public Page<SimpleVideo> getAllVideos(Pageable pageable) {
        return videoRepository.findAll(pageable).map(SimpleVideo::of);
    }

}
