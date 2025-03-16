package capstone.design.control_automation.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {

    @GetMapping("/reports")
    public String reports(Model model) {
        List<String> dates = List.of("2025-03-01", "2025-03-02", "2025-03-03", "2025-03-04", "2025-03-05");
        model.addAttribute("dates", dates);
        return "report/reports";
    }

}
