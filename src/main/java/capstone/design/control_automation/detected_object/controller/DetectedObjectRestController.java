package capstone.design.control_automation.detected_object.controller;

import capstone.design.control_automation.common.exception.ErrorCode;
import capstone.design.control_automation.detected_object.dto.DetectedObjectRequest;
import capstone.design.control_automation.detected_object.dto.DetectedObjectResponse;
import capstone.design.control_automation.detected_object.dto.DetectedObjectSearchRequest;
import capstone.design.control_automation.detected_object.service.DetectedObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/detected-object")
@RequiredArgsConstructor
public class DetectedObjectRestController {
    private final DetectedObjectService detectedObjectService;

    @PostMapping
    public ResponseEntity<?> createDetectedObject(@RequestBody DetectedObjectRequest detectedObjectRequest){
        detectedObjectService.createDetectedObject(detectedObjectRequest);

        return ResponseEntity.ok(ErrorCode.OK.getMessage());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteDetectedObject(@RequestBody Long detectedObjectId){
        detectedObjectService.deleteDetectedObject(detectedObjectId);

        return ResponseEntity.ok(ErrorCode.OK.getMessage());
    }

    @PostMapping("/find")
    public ResponseEntity<Page<DetectedObjectResponse>> findDetectedObject(@RequestBody DetectedObjectSearchRequest detectedObjectSearchRequest, Pageable pageable){
        return ResponseEntity.ok(detectedObjectService.findDetectedObject(pageable, detectedObjectSearchRequest));
    }
}
