package capstone.design.control_automation.video.service;

import capstone.design.control_automation.detected_object.client.MobileObjectFeatureClient;
import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectResponse.Common;
import capstone.design.control_automation.event.client.EventSummaryClient;
import capstone.design.control_automation.video.controller.dto.VideoResponse;
import capstone.design.control_automation.video.controller.dto.VideoResponse.SimpleWithEvent;
import capstone.design.control_automation.video.controller.dto.VideoResponse.SimpleWithMobileObject;
import capstone.design.control_automation.video.repository.VideoRepository;
import capstone.design.control_automation.video.repository.dto.VideoQueryResult;
import capstone.design.control_automation.video.repository.dto.VideoQueryResult.Detail;

import java.util.Base64;
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
    private final EventSummaryClient eventSummaryClient;
    private final MobileObjectFeatureClient mobileObjectFeatureClient;

    public VideoResponse.Detail findById(Long videoId) {
        Map<Long, List<VideoQueryResult.Detail>> videoGroup = videoRepository.findById(videoId).stream()
            .collect(Collectors.groupingBy(Detail::videoId));

        List<Common> mobileObjects = videoGroup.values().stream()
            .map(details -> details.stream()
                .map(
                    detail -> new Common(
                        detail.detectedObjectId(),
                        detail.categoryName(),
                        detail.cropImgUrl(),
                        mobileObjectFeatureClient.getFeatureByUuid(detail.detectedObjectUUID())
                    )
                )
                .toList()
            )
            .findAny().get();

        // 모든 Detail의 Video 정보는 같다
        VideoQueryResult.Detail base = videoGroup.get(videoId).get(0);

        return new VideoResponse.Detail(
            base.videoUrl(),
            base.cameraScenery(),
            base.latitude(),
            base.longitude(),
            mobileObjects
        );
    }

    public SimpleWithMobileObject getSimpleVideoByMobileObjectId(Long mobileDetectionId) {
        VideoQueryResult.SimpleWithMobileObject simpleWithMobileObject = videoRepository.findByMobileDetectionId(
            mobileDetectionId);
        return new SimpleWithMobileObject(
            simpleWithMobileObject.videoUrl(),
            simpleWithMobileObject.detectedObjectUUID(),
            simpleWithMobileObject.detectedObjectAlias(),
            Base64.getEncoder().encodeToString(simpleWithMobileObject.detectedObjectCropImg()),
            simpleWithMobileObject.appearedTime(),
            simpleWithMobileObject.exitTime(),
            simpleWithMobileObject.categoryName(),
            mobileObjectFeatureClient.getFeatureByUuid(simpleWithMobileObject.detectedObjectUUID())
        );
    }

    public SimpleWithEvent getSimpleVideoByEvent(Long eventId) {
        VideoQueryResult.SimpleWithEvent simpleWithEvent = videoRepository.findByEventId(eventId);

        return new SimpleWithEvent(
            simpleWithEvent.videoUrl(),
            eventSummaryClient.getSummaryByUuid(simpleWithEvent.eventUUID()),
            simpleWithEvent.eventUUID(),
            simpleWithEvent.appearedTime(),
            simpleWithEvent.exitTime(),
            simpleWithEvent.eventCodeName(),
            simpleWithEvent.eventRisk()
        );
    }
}
