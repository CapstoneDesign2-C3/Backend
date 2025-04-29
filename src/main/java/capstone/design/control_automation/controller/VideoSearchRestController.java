package capstone.design.control_automation.controller;

import capstone.design.control_automation.dto.VideoRequest;
import capstone.design.control_automation.dto.VideoResponse;
import capstone.design.control_automation.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/video-search")
public class VideoSearchRestController {
    private final VideoService videoService;

    @PostMapping()
    public ResponseEntity<?> saveVideo(@RequestBody VideoRequest videoRequest){
        videoService.saveVideo(videoRequest);

        return ResponseEntity.ok("성공");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteVideo(@RequestBody Long videoId){
        videoService.deleteVideo(videoId);

        return ResponseEntity.ok("성공");
    }

    @PostMapping("/find")
    public ResponseEntity<List<VideoResponse>> findVideos(@RequestBody String keyword){
        return ResponseEntity.ok(videoService.findVideos(keyword));
    }
}
