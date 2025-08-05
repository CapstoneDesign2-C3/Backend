package capstone.design.control_automation.detected_object.service;

import capstone.design.control_automation.detected_object.client.MobileObjectFeatureClient;
import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectRequest.MobileObjectFilter;
import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectResponse.MobileObject;
import capstone.design.control_automation.detected_object.repository.DetectedObjectRepository;
import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetectedObjectService {

    private final DetectedObjectRepository detectedObjectRepository;
    private final MobileObjectFeatureClient mobileObjectFeatureClient;

    public Page<MobileObject> findMobileObjectByFilter(MobileObjectFilter mobileObjectFilter, Pageable pageable) {
        return detectedObjectRepository
            .findMobileObjectsByFilterAndIds(mobileObjectFilter, pageable)
            .map(mobileObject -> new MobileObject(
                    mobileObject.mobileObjectId(),
                    mobileObject.categoryName(),
                    Base64.getEncoder().encodeToString(mobileObject.cropImg()),
                    mobileObject.alias(),
                    mobileObjectFeatureClient.getFeatureByUuid(mobileObject.mobileObjectUuid())
                )
            );
    }

    @Transactional
    public void aliasDetectedObject(Long detectedObjectId, String alias) {
        detectedObjectRepository.aliasDetectedObject(detectedObjectId, alias);
    }

    public DetectedObjectQueryResult.MobileObject findById(Long id) {
        return detectedObjectRepository.findById(id);
    }

    @Transactional
    public void changeDetectedObjectImage(Long detectedObjectId, byte[] bytes) {
        detectedObjectRepository.changeDetectedObjectImage(detectedObjectId, bytes);
    }
}
