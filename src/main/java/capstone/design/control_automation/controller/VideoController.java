package capstone.design.control_automation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VideoController {

    @GetMapping("/videos")
    public String videos() {
        return "videos";
    }
}
