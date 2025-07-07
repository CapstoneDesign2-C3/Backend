package capstone.design.control_automation.mapper;

import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectRequest.FixedObjectFilter;
import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult.FixedObject;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;

@Mapper
public interface DetectedObjectMapper {

    List<FixedObject> findFixedObjectsByFilterAndIds(FixedObjectFilter fixedObjectFilter, List<Long> fixedObjectIdByFeature,
        Pageable pageable);
}
