package capstone.design.control_automation.dashboard.controller;

import capstone.design.control_automation.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import capstone.design.control_automation.dashboard.controller.dto.DashboardResponse.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/dashboard")
public class DashboardRestController {
    private final DashboardService dashboardService;

    @GetMapping("/event")
    public ResponseEntity<List<Pie>> countByEvent(@RequestParam LocalDate date){
        return ResponseEntity.ok(dashboardService.countByEvent(date));
    }

    @GetMapping("/time")
    public ResponseEntity<Line  > countByTime(@RequestParam LocalDate date) {
        return ResponseEntity.ok(dashboardService.countByTime(date));
    }

    @GetMapping("/camera")
    public ResponseEntity<List<Bar>> countByCamera(@RequestParam LocalDate date){
        return ResponseEntity.ok(dashboardService.countByCamera(date));
    }

    @GetMapping("/risk")
    public ResponseEntity<List<Bar>> countByRisk(@RequestParam LocalDate date){
        return ResponseEntity.ok(dashboardService.countByRisk(date));
    }
}
