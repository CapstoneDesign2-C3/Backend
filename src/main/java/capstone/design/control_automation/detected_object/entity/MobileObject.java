package capstone.design.control_automation.detected_object.entity;

import capstone.design.control_automation.category.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("mobile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MobileObject extends DetectedObject {

    @Column
    private String feature;

    public MobileObject(String cropImgUrl, Category category, String feature) {
        super(cropImgUrl, category);
        this.feature = feature;
    }
}
