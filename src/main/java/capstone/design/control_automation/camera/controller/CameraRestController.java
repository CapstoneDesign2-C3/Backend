package capstone.design.control_automation.camera.controller;

import capstone.design.control_automation.camera.controller.dto.CameraRequest;
import capstone.design.control_automation.camera.controller.dto.CameraRequest.Filter;
import capstone.design.control_automation.camera.controller.dto.CameraResponse;
import capstone.design.control_automation.camera.controller.dto.CameraResponse.Position;
import capstone.design.control_automation.camera.service.CameraService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("api/v1/camera")
@RequiredArgsConstructor
public class CameraRestController {

    private final CameraService cameraService;

    @GetMapping
    public ResponseEntity<List<Position>> getCameraPositionByFilterCondition(
        @ModelAttribute Filter filter
    ) {
        return ResponseEntity.ok(cameraService.getCameraPositionByFilterCondition(filter));
    }

    @GetMapping("/{cameraId}")
    public ResponseEntity<CameraResponse.Info> getCameraInfo(@PathVariable Long cameraId) {
        return ResponseEntity.ok(cameraService.getCameraInfo(cameraId));
    }

}
