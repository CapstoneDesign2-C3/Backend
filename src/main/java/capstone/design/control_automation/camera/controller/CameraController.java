package capstone.design.control_automation.camera.controller;

import capstone.design.control_automation.camera.dto.CameraRequest;
import capstone.design.control_automation.camera.dto.CameraResponse;
import capstone.design.control_automation.camera.service.CameraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CameraController {
    private final CameraService cameraService;

    @GetMapping("/cameras")
    public String cameras(Model model){
        List<CameraResponse> cameras = cameraService.getAllCamera();

        model.addAttribute("cameras", cameras);
        return "cameras";
    }

    @PostMapping("/api/v1/camera")
    public String createCamera(@ModelAttribute CameraRequest cameraRequest) {
        cameraService.createCamera(cameraRequest);

        return "redirect:/cameras";
    }

    @PostMapping("/api/v1/camera/delete")
    public String deleteCamera(@RequestParam Long id) {
        cameraService.deleteCamera(id);

        return "redirect:/cameras";
    }
}
