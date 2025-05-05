package capstone.design.control_automation.video.document;

import capstone.design.control_automation.video.dto.VideoResponse;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "video")
@Getter
public class VideoDocument {

    @Id
    private String id;

    private String summary;

    public VideoDocument(String id, String summary) {
        this.id = id;
        this.summary = summary;
    }

    public VideoResponse mapToResponse() {
        return new VideoResponse(id, summary);
    }
}
