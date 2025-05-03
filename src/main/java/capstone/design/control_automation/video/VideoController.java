package capstone.design.control_automation.video;

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

@Controller
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/videos")
    public String videos(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<SimpleVideo> videos = videoService.getAllVideos(pageable);

        model.addAttribute("videos", videos);
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
