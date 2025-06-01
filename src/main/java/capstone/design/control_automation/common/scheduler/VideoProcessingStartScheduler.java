package capstone.design.control_automation.common.scheduler;

import capstone.design.control_automation.common.client.VideoClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VideoProcessingStartScheduler {

    private final VideoClient videoClient;

    //    @Scheduled(fixedRate = 5000)
    public void startVideoProcessing() {
        try {
            byte[] fileData = videoClient.getFileData();

            Object cameraInfo = null; // 변경 필요
            videoClient.postVideoProcessing(fileData, cameraInfo);

            log.info("Video processing started");
        } catch (Exception e) {
            System.err.println("전송 실패: " + e.getMessage());
        }
    }

}
