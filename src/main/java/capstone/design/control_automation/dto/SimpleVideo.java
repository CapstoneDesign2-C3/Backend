package capstone.design.control_automation.dto;

import capstone.design.control_automation.domain.entity.Address;
import capstone.design.control_automation.domain.entity.Camera;
import capstone.design.control_automation.domain.entity.Video;
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

    private Address address;
    private String angle;

    // private boolean checked
    // private boolean bookmarked

    public static SimpleVideo of(Video video) {
        Camera camera = video.getCamera();
        return new SimpleVideo(
            video.getId(),
            video.getThumbnailUrl(),
            video.getSummary(),
            video.getStartTime(),
            video.getEndTime(),
            camera.getAddress(),
            camera.getAngle()
        );
    }
}
