package capstone.design.control_automation.camera.entity;

import static lombok.AccessLevel.PUBLIC;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor(access = PUBLIC) // memoryVideoRepository 전용
@Getter
public class Address {

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    public String getFullAddress() {
        return address1 + " " + address2;
    }
}
