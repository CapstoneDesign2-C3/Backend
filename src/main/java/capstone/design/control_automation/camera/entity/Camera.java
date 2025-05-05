package capstone.design.control_automation.camera.entity;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import capstone.design.control_automation.camera.dto.CameraResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
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
    @Column(name = "camera_id")
    private Long id;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "angle")
    private String angle; // 카메라가 비추는 장면 ex) 강변 방향

    @Embedded
    private Address address;

    @Column(name = "status")
    @Enumerated(value = STRING)
    private CameraStatus status;

    public Camera(Double latitude, Double longitude, String angle, Address address, CameraStatus status){
        this.latitude = latitude;
        this.longitude = longitude;
        this.angle = angle;
        this.address = address;
        this.status = status;
    }

    public CameraResponse of(){
        return new CameraResponse(latitude, longitude, angle, address.getAddress1(), address.getAddress2(), status);
    }
}
