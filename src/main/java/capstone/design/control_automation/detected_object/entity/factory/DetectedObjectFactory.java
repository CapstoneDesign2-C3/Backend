package capstone.design.control_automation.detected_object.entity.factory;

import capstone.design.control_automation.category.entity.Category;
import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectRequest.Create;
import capstone.design.control_automation.detected_object.entity.DetectedObject;
import capstone.design.control_automation.detected_object.entity.FixedObject;
import capstone.design.control_automation.detected_object.entity.MobileObject;

public class DetectedObjectFactory {

    public static DetectedObject createDetectedObjectWithCategory(Boolean isMobile, Create create, Category category) {
        if (isMobile) {
            return new MobileObject(
                create.cropImgUrl(),
                category,
                create.feature()
            );
        }

        return new FixedObject(
            create.cropImgUrl(),
            category
        );
    }
}
