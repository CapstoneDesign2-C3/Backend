package capstone.design.control_automation.repository;

import capstone.design.control_automation.domain.entity.Address;
import capstone.design.control_automation.domain.entity.Camera;
import capstone.design.control_automation.domain.entity.CameraStatus;
import capstone.design.control_automation.domain.entity.EmergencyStatus;
import capstone.design.control_automation.domain.entity.Event;
import capstone.design.control_automation.domain.entity.Video;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class VideoCustomRepositoryImpl implements VideoCustomRepository {

    @Override
    public Video getVideoById(Long videoId) {
        return null;
    }

    @Override
    public Page<Video> getAllVideos(Pageable pageable) {
        return new PageImpl<Video>(
            List.of(
                new Video(
                    1L,
                    "summary",
                    "videoUrl",
                    LocalDateTime.now().minusHours(2L),
                    LocalDateTime.now(),
                    "thumbnailUrl",
                    "memo",
                    new Camera(
                        1L,
                        0.0,
                        0.0,
                        "angle",
                        new Address(
                            "address1",
                            "address2"
                        ),
                        false,
                        CameraStatus.NORMAL
                    ),
                    new Event(1L, EmergencyStatus.FIRE)
                )
            )
        );
    }
}
