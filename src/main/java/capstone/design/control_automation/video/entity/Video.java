package capstone.design.control_automation.video.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
    @Column(name = "id")
    private Long id;

    @Column(name = "video_url", nullable = false)
    private String videoUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Lob
    @Column(name = "summary", nullable = false)
    private String summary;

    public Video(String videoUrl, String summary) {
        this.videoUrl = videoUrl;
        this.summary = summary;
    }
}
