package capstone.design.control_automation.dashboard.repository;

import capstone.design.control_automation.dashboard.repository.dto.DashboardQueryResult.*;

import java.time.LocalDate;
import java.util.List;

public interface DashboardRepository {
    List<Pie> countByEvent(LocalDate date);
    List<Data> countByTime(LocalDate date);
    List<Bar> countByCamera(LocalDate date);
    List<Bar> countByRisk(LocalDate date);
}
