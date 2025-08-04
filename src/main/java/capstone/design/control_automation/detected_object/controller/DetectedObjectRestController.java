package capstone.design.control_automation.detected_object.controller;

import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectRequest.MobileObjectFilter;
import capstone.design.control_automation.detected_object.controller.dto.DetectedObjectResponse.MobileObject;
import capstone.design.control_automation.detected_object.service.DetectedObjectService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/detected-object")
@RequiredArgsConstructor
public class DetectedObjectRestController {

    private final DetectedObjectService detectedObjectService;

    @GetMapping
    public ResponseEntity<Page<MobileObject>> findMobileObjectByFilter(
        @ModelAttribute MobileObjectFilter mobileObjectFilter,
        @PageableDefault Pageable pageable
    ) {
        Page<MobileObject> detectedObjects = detectedObjectService.findMobileObjectByFilter(
            mobileObjectFilter, pageable);

        return ResponseEntity.ok(detectedObjects);
    }

    @PutMapping("/{detectedObjectId}")
    public ResponseEntity<Void> aliasDetectedObject(
        @PathVariable Long detectedObjectId,
        @RequestBody String alias
    ) {
        detectedObjectService.aliasDetectedObject(detectedObjectId, alias);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/images/{detectedObjectId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> changeDetectedObjectImage(
        @PathVariable Long detectedObjectId,
        @RequestParam("image") MultipartFile image
    ) throws IOException {
        detectedObjectService.changeDetectedObjectImage(detectedObjectId, image.getBytes());

        return ResponseEntity.ok().build();
    }
}
