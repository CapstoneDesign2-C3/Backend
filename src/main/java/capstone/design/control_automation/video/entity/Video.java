package capstone.design.control_automation.video.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import capstone.design.control_automation.camera.entity.Camera;
import capstone.design.control_automation.event.entity.Event;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "video")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Video {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "video_id")
    private Long id;

    @Column(name = "summary")
    private String summary;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "camera_id")
    private Camera camera;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    public Video(Camera camera, String summary, String videoUrl, LocalDateTime startTime, String thumbnailUrl ,Event event) {
        this.camera = camera;
        this.summary = summary;
        this.videoUrl = videoUrl;
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(5);
        this.thumbnailUrl = thumbnailUrl;
        this.event = event;
    }

}
