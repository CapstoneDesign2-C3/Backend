package capstone.design.control_automation.detection.controller;

import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectRequest;
import capstone.design.control_automation.detection.controller.dto.DetectionRequest.Filter;
import capstone.design.control_automation.detection.controller.dto.DetectionResponse.Fixed;
import capstone.design.control_automation.detection.controller.dto.DetectionResponse.Position;
import capstone.design.control_automation.detection.controller.dto.DetectionResponse.Track;
import capstone.design.control_automation.detection.service.DetectionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/detection")
@RequiredArgsConstructor
public class DetectionRestController {

    private final DetectionService detectionService;

    @GetMapping("/tracks")
    public ResponseEntity<Page<Track>> getTracksByFilterCondition(
        @ModelAttribute Filter filter,
        @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok(detectionService.getTracksByFilterCondition(filter, pageable));
    }

    @GetMapping("/positions")
    public ResponseEntity<List<Position>> getPositionsByFilterCondition(
        @ModelAttribute Filter filter
    ) {
        return ResponseEntity.ok(detectionService.getPositionsByFilterCondition(filter));
    }

    @GetMapping("/fixed")
    public ResponseEntity<Page<Fixed>> getFixedDetectionByFilterCondition(
        @ModelAttribute DetectedObjectRequest.FixedObjectFilter fixedObjectFilter,
        @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok(detectionService.getFixedDetectionByFilterCondition(fixedObjectFilter, pageable));
    }
}
