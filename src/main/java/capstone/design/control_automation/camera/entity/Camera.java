package capstone.design.control_automation.camera.entity;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "camera")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Camera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "scenery")
    private String scenery; // 카메라가 비추는 장면 ex) 강변 방향

    public Camera(Double latitude, Double longitude, String scenery) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.scenery = scenery;
    }

    public void updateInfo(Double latitude, Double longitude, String scenery) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.scenery = scenery;
    }
}
