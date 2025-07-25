package capstone.design.control_automation.detected_object.repository;

import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectRequest.MobileObjectFilter;
import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult.MobileObject;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DetectedObjectRepository {

    Page<MobileObject> findMobileObjectsByFilterAndIds(MobileObjectFilter mobileObjectFilter,
        Pageable pageable);

    void aliasDetectedObject(Long detectedObjectId, String alias);
}
