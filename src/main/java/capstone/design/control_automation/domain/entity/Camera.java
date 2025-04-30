package capstone.design.control_automation.domain.entity;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;
import static lombok.AccessLevel.PUBLIC;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "camera")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PUBLIC) // memoryVideoRepository 전용
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

    @Column(name = "rotatable")
    private Boolean rotatable;

    @Column(name = "status")
    @Enumerated(value = STRING)
    private CameraStatus status;
}
