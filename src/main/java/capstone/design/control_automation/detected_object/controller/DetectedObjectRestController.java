package capstone.design.control_automation.detected_object.controller;

import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectRequest.Create;
import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectRequest.FixedObjectFilter;
import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectResponse.FixedObject;
import capstone.design.control_automation.detected_object.service.DetectedObjectService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("api/v1/detected-object")
@RequiredArgsConstructor
public class DetectedObjectRestController {

    private final DetectedObjectService detectedObjectService;

    @GetMapping
    public ResponseEntity<Page<FixedObject>> findFixedObjectByFilter(
        @RequestParam FixedObjectFilter fixedObjectFilter,
        @PageableDefault Pageable pageable
    ) {
        Page<FixedObject> detectedObjects = detectedObjectService.findFixedObjectByFilter(
            fixedObjectFilter, pageable);

        return ResponseEntity.ok(detectedObjects);
    }

    @PostMapping
    public ResponseEntity<Void> createDetectedObject(@RequestBody Create create) {
        Long detectedObjectId = detectedObjectService.createDetectedObject(create);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{detectedObjectId}")
            .build(detectedObjectId);

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{detectedObjectId}")
    public ResponseEntity<Void> aliasDetectedObject(
        @PathVariable Long detectedObjectId,
        @RequestBody String alias
    ) {
        detectedObjectService.aliasDetectedObject(detectedObjectId, alias);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{detectedObjectId}")
    public ResponseEntity<Void> deleteDetectedObject(@PathVariable Long detectedObjectId) {
        detectedObjectService.deleteDetectedObject(detectedObjectId);

        return ResponseEntity.noContent().build();
    }
}
