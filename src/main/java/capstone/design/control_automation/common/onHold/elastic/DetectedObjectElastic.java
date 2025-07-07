package capstone.design.control_automation.common.onHold.elastic;

import capstone.design.control_automation.common.onHold.elastic.document.DetectedObjectDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetectedObjectElastic extends ElasticsearchRepository<DetectedObjectDocument, String> {
    List<DetectedObjectDocument> findByFeatureContaining(String feature);
}
