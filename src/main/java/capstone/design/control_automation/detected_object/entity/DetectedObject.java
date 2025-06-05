package capstone.design.control_automation.detected_object.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import capstone.design.control_automation.camera.entity.Camera;
import capstone.design.control_automation.detected_object.dto.DetectedObjectResponse;
import capstone.design.control_automation.event.entity.Event;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "detected_object")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class DetectedObject {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "detected_object_id")
    private Long id;

    @Column(name = "re_identification")
    private String reId;

    @Column(name = "feature")
    private String feature;

    @Column(name = "start_frame")
    private int startFrame;

    @Column(name = "end_frame")
    private int endFrame;

    @Column(name = "video_url")
    private String videoUrl;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "camera_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Camera camera;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "event_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Event event;

    public DetectedObject(String reId, String feature, int startFrame, int endFrame, String videoUrl, Camera camera, Event event){
        this.reId = reId;
        this.feature = feature;
        this.startFrame = startFrame;
        this.endFrame = endFrame;
        this.videoUrl = videoUrl;
        this.camera = camera;
        this.event = event;
    }

    public DetectedObjectResponse mapToResponse(){
        return new DetectedObjectResponse(this.id,
                this.reId,
                this.feature,
                this.startFrame,
                this.endFrame,
                this.videoUrl,
                this.camera.getId(),
                this.event.getKeyword());
    }
}
