package capstone.design.control_automation.entity;

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
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detected_object")
@NoArgsConstructor(access = PROTECTED)
public class DetectedObject {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "detected_object_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "feature")
    private String feature;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "frame_id")
    private Frame frame;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "event_id")
    private Event event;
}
