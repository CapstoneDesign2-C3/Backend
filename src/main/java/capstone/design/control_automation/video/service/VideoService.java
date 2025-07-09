package capstone.design.control_automation.video.service;

import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectResponse;
import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectResponse.Common;
import capstone.design.control_automation.video.dto.VideoRequest.Upsert;
import capstone.design.control_automation.video.dto.VideoResponse;
import capstone.design.control_automation.video.dto.VideoResponse.SimpleWithFixedObject;
import capstone.design.control_automation.video.dto.VideoResponse.SimpleWithMobileObject;
import capstone.design.control_automation.video.repository.VideoRepository;
import capstone.design.control_automation.video.repository.dto.VideoQueryResult;
import capstone.design.control_automation.video.repository.dto.VideoQueryResult.Detail;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoService {

    private final VideoRepository videoRepository;

    public VideoResponse.Detail findById(Long videoId) {
        Map<Long, List<VideoQueryResult.Detail>> videoGroup = videoRepository.findById(videoId).stream()
            .collect(Collectors.groupingBy(Detail::videoId));

        List<Common> detectedObjects = videoGroup.values().stream()
            .map(details -> details.stream().map(Common::of).toList())
            .findAny().get();

        // 모든 Detail의 Video 정보는 같다
        VideoQueryResult.Detail base = videoGroup.get(videoId).getFirst();

        return new VideoResponse.Detail(
            base.videoUrl(),
            base.cameraScenery(),
            base.latitude(),
            base.longitude(),
            base.summary(),
            detectedObjects
        );
    }

    public SimpleWithMobileObject getSimpleVideoByMobileObjectId(Long mobileObjectId) {
        return VideoResponse.SimpleWithMobileObject.of(videoRepository.findByMobileObjectId(mobileObjectId));
    }

    public SimpleWithFixedObject getSimpleVideoByFixedObjectId(Long fixedObjectId) {
        return VideoResponse.SimpleWithFixedObject.of(videoRepository.findByFixedObjectId(fixedObjectId));
    }

    public void saveVideo(Upsert upsert) {
    }

    public void deleteVideo(Long videoId) {
    }
}
