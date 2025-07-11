package capstone.design.control_automation.video.controller;

import capstone.design.control_automation.video.controller.dto.VideoResponse;
import capstone.design.control_automation.video.controller.dto.VideoResponse.Detail;
import capstone.design.control_automation.video.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/video")
public class VideoRestController {

    private final VideoService videoService;

    @GetMapping("/{videoId}")
    public ResponseEntity<VideoResponse.Detail> getVideoDetail(@PathVariable("videoId") Long videoId) {
        Detail videoDetail = videoService.findById(videoId);
        return ResponseEntity.ok(videoDetail);
    }

    @GetMapping("/mobile-detection")
    public ResponseEntity<VideoResponse.SimpleWithMobileObject> getSimpleVideoWithMobileObject(
        @RequestParam Long mobileDetectionId) {
        VideoResponse.SimpleWithMobileObject simpleVideo = videoService.getSimpleVideoByMobileObjectId(mobileDetectionId);
        return ResponseEntity.ok(simpleVideo);
    }

    @GetMapping("/fixed-object")
    public ResponseEntity<VideoResponse.SimpleWithFixedObject> getSimpleVideoWithFixedObject(@RequestParam Long fixedObjectId) {
        VideoResponse.SimpleWithFixedObject simpleVideo = videoService.getSimpleVideoByFixedObjectId(fixedObjectId);
        return ResponseEntity.ok(simpleVideo);
    }

}
