package capstone.design.control_automation.mapper.detectedObject;

import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult.MobileObject;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DetectedObjectMapper {

    Long findMobileObjectCountByFilterAndIds(String categoryName, String alias);

    List<MobileObject> findMobileObjectsByFilterAndIds(String categoryName, String alias, Integer pageSize,
        Long offset);
}
