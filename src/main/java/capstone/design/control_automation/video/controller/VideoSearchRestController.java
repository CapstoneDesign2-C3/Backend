package capstone.design.control_automation.video.controller;

import capstone.design.control_automation.video.dto.SimpleVideo;
import capstone.design.control_automation.video.dto.VideoSearchRequest;
import capstone.design.control_automation.video.service.VideoService;
import capstone.design.control_automation.video.dto.VideoRequest;
import capstone.design.control_automation.video.dto.VideoResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/video-search")
public class VideoSearchRestController {

    private final VideoService videoService;

    @PostMapping()
    public ResponseEntity<?> saveVideo(@RequestBody VideoRequest videoRequest) {
        videoService.saveVideo(videoRequest);

        return ResponseEntity.ok("성공");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteVideo(@RequestBody Long videoId) {
        videoService.deleteVideo(videoId);

        return ResponseEntity.ok("성공");
    }

    @PostMapping("/find")
    public ResponseEntity<Page<SimpleVideo>> findVideos(@RequestBody VideoSearchRequest videoSearchRequest, @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(videoService.findVideo(pageable, videoSearchRequest));
    }
}
