package capstone.design.control_automation.detected_object.repository;

import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectRequest.FixedObjectFilter;
import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectRequest.MobileObjectFilter;
import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult.FixedObject;
import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult.MobileObject;
import capstone.design.control_automation.mapper.detectedObject.DetectedObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Primary
@Repository
@RequiredArgsConstructor
public class DetectedObjectMyBatisRepository implements DetectedObjectRepository {

    private final DetectedObjectMapper detectedObjectMapper;

    @Override
    public Page<MobileObject> findMobileObjectsByFilterAndIds(MobileObjectFilter filter,
        List<Long> mobileObjectIds, Pageable pageable) {
        Long count = detectedObjectMapper.findMobileObjectCountByFilterAndIds(
            filter.categoryName(),
            filter.alias(),
            mobileObjectIds
        );

        if (count == 0) {
            return Page.empty();
        }

        List<MobileObject> mobileObjects = detectedObjectMapper.findMobileObjectsByFilterAndIds(
            filter.categoryName(),
            filter.alias(),
            mobileObjectIds,
            pageable.getPageSize(),
            pageable.getOffset()
        );

        return new PageImpl<>(mobileObjects, pageable, mobileObjects.size());
    }
}
