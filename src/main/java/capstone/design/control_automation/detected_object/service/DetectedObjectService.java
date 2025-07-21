package capstone.design.control_automation.detected_object.service;

import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectRequest.MobileObjectFilter;
import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectResponse;
import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectResponse.MobileObject;
import capstone.design.control_automation.detected_object.repository.DetectedObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetectedObjectService {

    private final DetectedObjectRepository detectedObjectRepository;

    public Page<MobileObject> findMobileObjectByFilter(MobileObjectFilter mobileObjectFilter, Pageable pageable) {
        return detectedObjectRepository
            .findMobileObjectsByFilterAndIds(mobileObjectFilter, pageable)
            .map(DetectedObjectResponse.MobileObject::from);
    }

    @Transactional
    public void aliasDetectedObject(Long detectedObjectId, String alias) {
        //TODO
    }

}
