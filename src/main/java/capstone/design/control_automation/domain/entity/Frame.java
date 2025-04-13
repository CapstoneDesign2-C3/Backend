package capstone.design.control_automation.domain.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "frame")
@NoArgsConstructor(access = PROTECTED)
public class Frame {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "frame_id")
    private Long id;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "time_in_video")
    private Double timeInVideo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "video_id")
    private Video video;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "report_id")
    private Report report;
}
