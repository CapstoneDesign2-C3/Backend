package capstone.design.control_automation.dashboard.repository;

import capstone.design.control_automation.dashboard.repository.dto.DashboardQueryResult.*;
import capstone.design.control_automation.mapper.dashboard.DashboardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DashboardMyBatisRepository implements DashboardRepository{
    private final DashboardMapper dashboardMapper;

    @Override
    public List<Pie> countByEvent(LocalDate date){
        return dashboardMapper.countByEvent(date);
    }

    @Override
    public List<Data> countByTime(LocalDate date) {
        return dashboardMapper.countByTime(date);
    }

    @Override
    public List<Bar> countByCamera(LocalDate date){
        return dashboardMapper.countByCamera(date);
    }

    @Override
    public List<Bar> countByRisk(LocalDate date){
        return dashboardMapper.countByRisk(date);
    }
}
