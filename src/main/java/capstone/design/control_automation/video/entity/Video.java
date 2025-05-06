package capstone.design.control_automation.video.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static lombok.AccessLevel.PUBLIC;

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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "video")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PUBLIC) // memoryVideoRepository 사용용
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

    @Column(name = "memo")
    private String memo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "camera_id")
    private Camera camera;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    public Video(String summary, String videoUrl, LocalDateTime startTime, LocalDateTime endTime) {
        this.summary = summary;
        this.videoUrl = videoUrl;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
