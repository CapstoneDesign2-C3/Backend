package capstone.design.control_automation.camera.controller;

import capstone.design.control_automation.camera.controller.dto.CameraRequest;
import capstone.design.control_automation.camera.controller.dto.CameraRequest.Filter;
import capstone.design.control_automation.camera.controller.dto.CameraResponse;
import capstone.design.control_automation.camera.controller.dto.CameraResponse.Position;
import capstone.design.control_automation.camera.service.CameraService;
import java.util.List;

import capstone.design.control_automation.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/cameras")
    public ResponseEntity<List<CameraResponse.Camera>> getCameras() {
        return ResponseEntity.ok(cameraService.getCameras());
    }

    @PostMapping()
    public ResponseEntity<String> insertCamera(@RequestBody CameraRequest.Camera camera){
        cameraService.insertCamera(camera);

        return ResponseEntity.ok(ErrorCode.OK.getMessage());
    }
}
