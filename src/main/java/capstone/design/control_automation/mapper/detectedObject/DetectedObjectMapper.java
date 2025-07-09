package capstone.design.control_automation.mapper.detectedObject;

import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult.FixedObject;
import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult.MobileObject;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DetectedObjectMapper {

    Long findFixedObjectCountByFilterAndIds(String categoryName, String alias, List<Long> ids);

    List<FixedObject> findFixedObjectsByFilterAndIds(String categoryName, String alias, List<Long> ids, Integer pageSize,
        Long offset);

    Long findMobileObjectCountByFilterAndIds(String categoryName, String alias, List<Long> ids);

    List<MobileObject> findMobileObjectsByFilterAndIds(String categoryName, String alias, List<Long> ids, Integer pageSize,
        Long offset);
}
