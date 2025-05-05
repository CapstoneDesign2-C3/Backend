package capstone.design.control_automation.report.controller;

import java.util.List;

import capstone.design.control_automation.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/reports")
    public String reports(Model model) {
        List<String> dates = List.of("2025-03-01", "2025-03-02", "2025-03-03", "2025-03-04", "2025-03-05");
        model.addAttribute("dates", dates);
        String recentDate = dates.get(dates.size() - 1);
        model.addAttribute("report", recentDate);
        return "report/reports";
    }

    @GetMapping("/reports/{date}")
    public String getReport(Model model, @PathVariable String date) {
        List<String> dates = List.of("2025-03-01", "2025-03-02", "2025-03-03", "2025-03-04", "2025-03-05");
        model.addAttribute("dates", dates);
        model.addAttribute("report", date);
        return "report/reports";
    }
}
