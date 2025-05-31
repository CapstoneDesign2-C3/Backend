package capstone.design.control_automation.detected_object.repository;

import capstone.design.control_automation.detected_object.document.DetectedObjectDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetectedObjectElastic extends ElasticsearchRepository<DetectedObjectDocument, String> {
    List<DetectedObjectDocument> findByFeatureContaining(String feature);
}
