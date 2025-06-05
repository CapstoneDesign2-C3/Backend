package capstone.design.control_automation.video.controller;

import capstone.design.control_automation.camera.service.CameraService;
import capstone.design.control_automation.event.service.EventService;
import capstone.design.control_automation.video.service.VideoService;
import capstone.design.control_automation.video.dto.SimpleVideo;
import capstone.design.control_automation.video.dto.VideoForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    private final EventService eventService;
    private final CameraService cameraService;

    @GetMapping("/videos")
    public String videos(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<SimpleVideo> videos = videoService.getAllVideos(pageable);
        List<String> keywords = eventService.getKeywords();
        List<String> cameraLocations = cameraService.getAngles();
        model.addAttribute("videos", videos);
        model.addAttribute("keywords", keywords);
        model.addAttribute("cameraLocations", cameraLocations);
        return "videos";
    }

    //비디오 id로 정보 가져오기
    @GetMapping("/videos/{videoId}")
    public String getVideoById(@PathVariable Long videoId, Model model) {
        VideoForm videoForm = videoService.getVideoFormById(videoId);

        model.addAttribute("videoForm", videoForm);
        return "videoDetails";
    }

}
