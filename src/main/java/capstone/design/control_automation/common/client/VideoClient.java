package capstone.design.control_automation.common.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class VideoClient {

    @Value("${video.process.src-url}")
    private String videoSrcUrl;

    @Value("${video.process.dest-url}")
    private String videoDestUrl;

    private final RestClient restClient;

    public byte[] getFileData() {
        return restClient.get()
            .uri(videoSrcUrl)
            .retrieve()
            .body(byte[].class);
    }

    public void postVideoProcessing(byte[] fileData, Object cameraInfo) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileData);
        body.add("cameraInfo", cameraInfo);

        restClient.post()
            .uri(videoDestUrl)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(body)
            .retrieve()
            .toBodilessEntity();
    }
}
