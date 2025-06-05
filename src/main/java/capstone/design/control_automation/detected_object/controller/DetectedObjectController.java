package capstone.design.control_automation.detected_object.controller;

import capstone.design.control_automation.detected_object.dto.DetectedObjectResponse;
import capstone.design.control_automation.detected_object.service.DetectedObjectService;
import capstone.design.control_automation.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DetectedObjectController {
    private final DetectedObjectService detectedObjectService;
    private final EventService eventService;

    @GetMapping("/detected-object")
    public String detectedObjects(Model model, Pageable pageable){
        Page<DetectedObjectResponse> detectedObjects = detectedObjectService.getDetectedObject(pageable);
        List<String> keywords = eventService.getKeywords();
        model.addAttribute("detectedObjects", detectedObjects);
        model.addAttribute("keywords", keywords);
        return "detected-object";
    }
}
