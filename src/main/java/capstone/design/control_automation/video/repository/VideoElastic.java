package capstone.design.control_automation.video.repository;

import capstone.design.control_automation.video.document.VideoDocument;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface VideoElastic extends ElasticsearchRepository<VideoDocument, String> {

    List<VideoDocument> findBySummaryContaining(String keyword);
}
