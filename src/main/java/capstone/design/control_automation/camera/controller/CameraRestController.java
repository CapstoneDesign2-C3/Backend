package capstone.design.control_automation.camera.controller;

import capstone.design.control_automation.camera.dto.CameraRequest;
import capstone.design.control_automation.camera.dto.CameraResponse;
import capstone.design.control_automation.camera.service.CameraService;
import capstone.design.control_automation.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/camera")
@AllArgsConstructor
public class CameraRestController {
    private final CameraService cameraService;

    @PostMapping()
    public ResponseEntity<?> createCamera(@RequestBody CameraRequest cameraRequest) {
        cameraService.createCamera(cameraRequest);

        return ResponseEntity.ok(ErrorCode.OK);
    }

    @GetMapping
    public ResponseEntity<List<CameraResponse>> getAllCamera(){
        return ResponseEntity.ok(cameraService.getAllCamera());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCamera(@RequestBody Long cameraId) {
        cameraService.deleteCamera(cameraId);

        return ResponseEntity.ok(ErrorCode.OK);
    }
}
