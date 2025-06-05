package capstone.design.control_automation.video.dto;

import capstone.design.control_automation.camera.entity.Address;
import capstone.design.control_automation.camera.entity.Camera;
import capstone.design.control_automation.event.entity.Event;
import capstone.design.control_automation.video.entity.Video;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SimpleVideo {

    private Long videoId;
    private String thumbnailUrl;
    private String summary;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String keyword;
    private Address address;
    private String angle;

    public static SimpleVideo of(Video video) {
        Camera camera = video.getCamera();
        Event event = video.getEvent();
        return new SimpleVideo(
            video.getId(),
            video.getThumbnailUrl(),
            video.getSummary(),
            video.getStartTime(),
            video.getEndTime(),
            event.getKeyword(),
            camera.getAddress(),
            camera.getAngle()
        );
    }
}
