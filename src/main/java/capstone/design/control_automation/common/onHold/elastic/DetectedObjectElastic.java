package capstone.design.control_automation.common.onHold.elastic;

import capstone.design.control_automation.common.onHold.elastic.document.DetectedObjectDocument;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectedObjectElastic extends ElasticsearchRepository<DetectedObjectDocument, String> {

    List<DetectedObjectDocument> findByFeatureContaining(String feature);
}
