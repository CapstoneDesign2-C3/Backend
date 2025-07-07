package capstone.design.control_automation.detected_object.entity;

import capstone.design.control_automation.category.entity.Category;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("fixed")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FixedObject extends DetectedObject {

    public FixedObject(String cropImgUrl, Category category) {
        super(cropImgUrl, category);
    }
}
