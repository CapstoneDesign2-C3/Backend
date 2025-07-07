package capstone.design.control_automation.video.controller;

import capstone.design.control_automation.video.dto.VideoRequest;
import capstone.design.control_automation.video.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/video")
public class VideoSearchRestController {

    private final VideoService videoService;

    @PostMapping()
    public ResponseEntity<?> saveVideo(@RequestBody VideoRequest.Upsert upsert) {
        videoService.saveVideo(upsert);

        return ResponseEntity.ok("성공");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteVideo(@RequestBody Long videoId) {
        videoService.deleteVideo(videoId);

        return ResponseEntity.ok("성공");
    }
}
