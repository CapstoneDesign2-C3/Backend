package capstone.design.control_automation.detected_object.service;

import capstone.design.control_automation.category.entity.Category;
import capstone.design.control_automation.category.repository.CategoryJpaRepository;
import capstone.design.control_automation.common.exception.ErrorCode;
import capstone.design.control_automation.common.exception.ErrorException;
import capstone.design.control_automation.common.onHold.NaturalLangSearchModel;
import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectRequest.Create;
import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectRequest.MobileObjectFilter;
import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectResponse;
import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectResponse.MobileObject;
import capstone.design.control_automation.detected_object.entity.DetectedObject;
import capstone.design.control_automation.detected_object.entity.factory.DetectedObjectFactory;
import capstone.design.control_automation.detected_object.repository.DetectedObjectJpaRepository;
import capstone.design.control_automation.detected_object.repository.DetectedObjectRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetectedObjectService {

    private final DetectedObjectJpaRepository detectedObjectJpaRepository;
    private final DetectedObjectRepository detectedObjectRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final NaturalLangSearchModel naturalLangSearchModel;
//    private final DetectedObjectElastic detectedObjectElastic;

    public Page<MobileObject> findMobileObjectByFilter(MobileObjectFilter mobileObjectFilter, Pageable pageable) {
        List<Long> mobileObjectIdByFeature = naturalLangSearchModel.findMobileObjectByFeature(mobileObjectFilter.searchInput());

        return detectedObjectRepository
            .findMobileObjectsByFilterAndIds(mobileObjectFilter, mobileObjectIdByFeature, pageable)
            .map(DetectedObjectResponse.MobileObject::from);
    }

    @Transactional
    public Long createDetectedObject(Create create) {
        Category category = categoryJpaRepository.findById(create.categoryId())
            .orElseThrow(() -> new ErrorException(ErrorCode.CATEGORY_NOT_FOUND));

        DetectedObject detectedObject = DetectedObjectFactory.createDetectedObjectWithCategory(
            category.getIsMobile(),
            create,
            category
        );

        detectedObjectJpaRepository.save(detectedObject);

//        DetectedObjectDocument detectedObjectDocument = new DetectedObjectDocument(detectedObject.getId().toString(),
//            detectedObject.getFeature());
//        detectedObjectElastic.save(detectedObjectDocument);

        return detectedObject.getId();
    }

    @Transactional
    public void deleteDetectedObject(Long id) {
        detectedObjectJpaRepository.deleteById(id);
//        detectedObjectElastic.deleteById(id.toString());
    }

    @Transactional
    public void aliasDetectedObject(Long detectedObjectId, String alias) {
        DetectedObject detectedObject = detectedObjectJpaRepository.findById(detectedObjectId)
            .orElseThrow(() -> new ErrorException(ErrorCode.DETECTED_OBJECT_NOT_FOUND));

        detectedObject.changeAlias();
    }

}
