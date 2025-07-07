package capstone.design.control_automation.common.onHold.elastic.document;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "detected_object")
@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class DetectedObjectDocument {
    @Id
    private String id;
    private String feature;
}
