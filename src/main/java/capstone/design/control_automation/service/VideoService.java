package capstone.design.control_automation.service;

import capstone.design.control_automation.domain.document.VideoDocument;
import capstone.design.control_automation.domain.entity.Video;
import capstone.design.control_automation.dto.VideoRequest;
import capstone.design.control_automation.dto.VideoResponse;
import capstone.design.control_automation.repository.VideoRepository;
import capstone.design.control_automation.repository.VideoSearchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class VideoService {
    private final VideoSearchRepository videoSearchRepository;
    private final VideoRepository videoRepository;

    @Transactional
    public void saveVideo(VideoRequest videoRequest){
        Video video = new Video(videoRequest.summary(), videoRequest.videoUrl(), LocalDateTime.now());

        videoRepository.save(video);

        VideoDocument videoDocument = new VideoDocument(video.getId().toString(), video.getSummary());
        videoSearchRepository.save(videoDocument);
    }

    @Transactional
    public void deleteVideo(Long videoId){
        videoRepository.deleteById(videoId);
        videoSearchRepository.deleteById(videoId.toString());
    }

    @Transactional(readOnly = true)
    public List<VideoResponse> findVideos(String keyword){
        List<VideoDocument> videoDocuments = videoSearchRepository.findBySummaryContaining(keyword);

        return videoDocuments.stream().map(VideoDocument::mapToResponse).toList();
    }
}
