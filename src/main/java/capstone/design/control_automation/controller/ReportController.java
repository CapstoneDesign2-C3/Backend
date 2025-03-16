package capstone.design.control_automation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {

    @GetMapping("/reports")
    public String reports() {
        return "reports";
    }

}
