package capstone.design.control_automation.detected_object.repository;

import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectRequest.FixedObjectFilter;
import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectRequest.MobileObjectFilter;
import capstone.design.control_automation.detected_object.entity.QDetectedObject;
import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult.FixedObject;
import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult.MobileObject;
import capstone.design.control_automation.detected_object.repository.dto.QDetectedObjectQueryResult_FixedObject;
import capstone.design.control_automation.detection.entity.QDetection;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetectedObjectQueryDSLRepository implements DetectedObjectRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FixedObject> findFixedObjectsByFilterAndIds(FixedObjectFilter fixedObjectFilter,
        List<Long> fixedObjectIdByFeature, Pageable pageable) {
        Long count = queryFactory.select(
                QDetectedObject.detectedObject.id.count()
            )
            .from(QDetectedObject.detectedObject)
            .where(QDetectedObject.detectedObject.category.name.eq(fixedObjectFilter.categoryName())
                .and(QDetectedObject.detectedObject.alias.eq(fixedObjectFilter.alias()))
                .and(QDetectedObject.detectedObject.id.in(fixedObjectIdByFeature)))
            .fetchOne();

        if (count == 0) {
            return Page.empty();
        }

        List<FixedObject> fixedObjects = queryFactory.select(new QDetectedObjectQueryResult_FixedObject(
                QDetectedObject.detectedObject.id,
                QDetectedObject.detectedObject.category.name,
                QDetectedObject.detectedObject.alias,
                QDetection.detection.video.summary
            )).from(QDetectedObject.detectedObject)
            .innerJoin(QDetection.detection)
            .where(QDetectedObject.detectedObject.category.name.eq(fixedObjectFilter.categoryName())
                .and(QDetectedObject.detectedObject.alias.eq(fixedObjectFilter.alias()))
                .and(QDetectedObject.detectedObject.id.in(fixedObjectIdByFeature)))
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        return new PageImpl<>(fixedObjects, pageable, fixedObjects.size());
    }

    @Override
    public Page<MobileObject> findMobileObjectsByFilterAndIds(MobileObjectFilter mobileObjectFilter,
        List<Long> mobileObjectIdByFeature, Pageable pageable) {
        // 미구현
        return null;
    }
}
