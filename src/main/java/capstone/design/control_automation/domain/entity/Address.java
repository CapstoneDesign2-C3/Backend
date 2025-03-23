package capstone.design.control_automation.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;
}
