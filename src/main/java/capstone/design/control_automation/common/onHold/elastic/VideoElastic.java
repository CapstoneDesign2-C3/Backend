package capstone.design.control_automation.common.onHold.elastic;

import capstone.design.control_automation.common.onHold.elastic.document.VideoDocument;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoElastic extends ElasticsearchRepository<VideoDocument, String> {
    List<VideoDocument> findBySummaryContaining(String keyword);
}
