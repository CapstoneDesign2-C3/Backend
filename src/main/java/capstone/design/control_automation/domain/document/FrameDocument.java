package capstone.design.control_automation.domain.document;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "frame")
public class FrameDocument {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Date)
    private LocalDateTime eventTime;

    @Field(type = FieldType.Object)
    private CameraInfo cameraInfo;
}
