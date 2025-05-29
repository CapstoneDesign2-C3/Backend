package capstone.design.control_automation.video.service;

import capstone.design.control_automation.camera.entity.Camera;
import capstone.design.control_automation.camera.repository.CameraRepository;
import capstone.design.control_automation.common.exception.ErrorCode;
import capstone.design.control_automation.common.exception.ErrorException;
import capstone.design.control_automation.event.entity.EmergencyStatus;
import capstone.design.control_automation.video.document.VideoDocument;
import capstone.design.control_automation.video.dto.*;
import capstone.design.control_automation.video.entity.QVideo;
import capstone.design.control_automation.video.entity.Video;
import capstone.design.control_automation.video.repository.VideoElastic;
import capstone.design.control_automation.video.repository.VideoRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoService {
    private final JPAQueryFactory queryFactory;
    private final VideoElastic videoElastic;
    private final VideoRepository videoRepository;
    private final CameraRepository cameraRepository;

    @Transactional
    public void saveVideo(VideoRequest videoRequest) {
        Camera camera = cameraRepository.findById(videoRequest.cameraId()).orElseThrow(() -> new ErrorException(ErrorCode.CAMERA_NOT_FOUND));

        Video video = new Video(camera,
                videoRequest.summary(),
                videoRequest.videoUrl(),
                videoRequest.startTime(),
                videoRequest.thumbnailUrl());

        videoRepository.save(video);

        VideoDocument videoDocument = new VideoDocument(video.getId().toString(), video.getSummary());
        videoElastic.save(videoDocument);
    }

    @Transactional
    public void deleteVideo(Long videoId) {
        videoRepository.deleteById(videoId);
        videoElastic.deleteById(videoId.toString());
    }

    public Page<SimpleVideo> findVideo(Pageable pageable, VideoSearchRequest videoSearchRequest) {
        List<Long> postgresVideoIds = findIdsByQueryFactory(videoSearchRequest);
        List<Long> videoDocuments = (videoSearchRequest.keyword() == null)?
                new ArrayList<>(): videoElastic.findBySummaryContaining(videoSearchRequest.keyword()).stream()
                .map(video -> Long.valueOf(video.getId()))
                .toList();

        Set<Long> finalIds;

        if(videoSearchRequest.keyword() == null) finalIds = new HashSet<>(postgresVideoIds);
        else{
            finalIds = postgresVideoIds.stream()
                    .filter(videoDocuments::contains)
                    .collect(Collectors.toSet());
        }

        return videoRepository.findByIdIn(pageable, finalIds).map(SimpleVideo::of);
    }

    public VideoForm getVideoFormById(Long videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new ErrorException(ErrorCode.VIDEO_NOT_FOUND));

        return VideoForm.of(video);
    }

    public Page<SimpleVideo> getAllVideos(Pageable pageable) {
        return videoRepository.findAll(pageable).map(SimpleVideo::of);
    }

    public List<Long> findIdsByQueryFactory(VideoSearchRequest videoSearchRequest){
        QVideo video = QVideo.video;
        return queryFactory
                .select(video.id)
                .from(video)
                .where(
                        videoSearchRequest.startDate() != null ? video.startTime.goe(videoSearchRequest.startDate().atStartOfDay()) : null,
                        videoSearchRequest.endDate() != null ? video.endTime.loe(videoSearchRequest.endDate().atTime(LocalTime.MAX)) : null,
                        videoSearchRequest.eventType() != null ? video.event.emergencyStatus.eq(EmergencyStatus.valueOf(videoSearchRequest.eventType())) : null,
                        videoSearchRequest.cameraLocation() != null ? video.camera.address.address1.eq(videoSearchRequest.cameraLocation()) : null
                )
                .fetch();
    }
}
