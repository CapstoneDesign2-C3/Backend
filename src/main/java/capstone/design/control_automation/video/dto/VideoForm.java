package capstone.design.control_automation.video.dto;

import capstone.design.control_automation.camera.entity.Camera;
import capstone.design.control_automation.event.entity.Event;
import capstone.design.control_automation.video.entity.Video;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class VideoForm {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String thumbnailUrl;

    private Long cameraId;
    private String cameraRegion;
    private Double cameraLatitude;
    private Double cameraLongitude;

    private String eventKeyword;

    private List<String> detects;

    public static VideoForm of(Video video) {
        Camera camera = video.getCamera();
        return new VideoForm(
            video.getStartTime(),
            video.getEndTime(),
            video.getThumbnailUrl(),
            camera.getId(),
            camera.getAngle(),
            camera.getLatitude(),
            camera.getLongitude(),
            video.getEvent().getKeyword(),
            List.of()
        );
    }
}
