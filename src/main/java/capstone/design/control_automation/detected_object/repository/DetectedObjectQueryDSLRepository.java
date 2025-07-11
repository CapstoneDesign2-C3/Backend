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
public abstract class DetectedObjectQueryDSLRepository implements DetectedObjectRepository {

    private final JPAQueryFactory queryFactory;

}
