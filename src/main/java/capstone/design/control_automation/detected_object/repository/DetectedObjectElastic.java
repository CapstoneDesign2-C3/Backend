package capstone.design.control_automation.detected_object.repository;

import capstone.design.control_automation.detected_object.document.DetectedObjectDocument;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectedObjectElastic extends ElasticsearchRepository<DetectedObjectDocument, String> {

    List<DetectedObjectDocument> findByFeatureContaining(String feature);

}
