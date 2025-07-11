package capstone.design.control_automation.detection.entity;

import capstone.design.control_automation.camera.entity.Camera;
import capstone.design.control_automation.detected_object.entity.DetectedObject;
import capstone.design.control_automation.video.entity.Video;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Detection {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "appeared_time", nullable = false)
    private LocalDateTime appearedTime;

    @Column(name = "exit_time", nullable = false)
    private LocalDateTime exitTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camera_id", nullable = false)
    private Camera camera;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detected_object_id", nullable = false)
    private DetectedObject detectedObject;

    public Detection(String thumbnailUrl, LocalDateTime appearedTime, LocalDateTime exitTime, Camera camera, Video video,
        DetectedObject detectedObject) {
        this.thumbnailUrl = thumbnailUrl;
        this.appearedTime = appearedTime;
        this.exitTime = exitTime;
        this.camera = camera;
        this.video = video;
        this.detectedObject = detectedObject;
    }
}
