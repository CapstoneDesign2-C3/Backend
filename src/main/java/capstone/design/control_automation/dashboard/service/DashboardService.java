package capstone.design.control_automation.dashboard.service;

import capstone.design.control_automation.dashboard.repository.DashboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import capstone.design.control_automation.dashboard.controller.dto.DashboardResponse.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DashboardService {
    private final DashboardRepository dashboardRepository;

    public List<Pie> countByEvent(LocalDate date){
        return dashboardRepository.countByEvent(date).stream()
                .map(p -> new Pie(p.id(), p.value()))
                .toList();
    }

    public Line countByTime(LocalDate date) {
        return new Line("data",
                dashboardRepository.countByTime(date).stream()
                        .map(p -> new Data(p.x(), p.y()))
                        .toList());
    }

    public List<Bar> countByCamera(LocalDate date){
        return dashboardRepository.countByCamera(date).stream()
                .map(p -> new Bar(p.id(), p.data()))
                .toList();
    }

    public List<Bar> countByRisk(LocalDate date){
        return dashboardRepository.countByRisk(date).stream()
                .map(p -> new Bar(p.id(), p.data()))
                .toList();
    }
}
