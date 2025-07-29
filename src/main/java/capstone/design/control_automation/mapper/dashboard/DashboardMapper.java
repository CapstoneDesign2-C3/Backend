package capstone.design.control_automation.mapper.dashboard;

import capstone.design.control_automation.dashboard.repository.dto.DashboardQueryResult.*;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DashboardMapper {
    List<Pie> countByEvent(LocalDate date);
    List<Data> countByTime(LocalDate date);
    List<Bar> countByCamera(LocalDate date);
    List<Bar> countByRisk(LocalDate date);
}
