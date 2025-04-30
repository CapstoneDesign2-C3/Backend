package capstone.design.control_automation.dto;

import capstone.design.control_automation.domain.entity.Camera;
import capstone.design.control_automation.domain.entity.EmergencyStatus;
import capstone.design.control_automation.domain.entity.Video;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class VideoForm {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String thumbnailUrl;
    private String memo;

    private Long cameraId;
    private String cameraRegion; // 카메라가 비추는 장소 ex) 강변 1
    private Double cameraLatitude;
    private Double cameraLongitude;

    private EmergencyStatus emergencyStatus;

    private List<String> detects;

    public static VideoForm of(Video video) {
        Camera camera = video.getCamera();
        return new VideoForm(
            video.getStartTime(),
            video.getEndTime(),
            video.getThumbnailUrl(),
            video.getMemo(),
            camera.getId(),
            camera.getAngle(),
            camera.getLatitude(),
            camera.getLongitude(),
            video.getEvent().getEmergencyStatus(),
            List.of()
        );
    }
}
