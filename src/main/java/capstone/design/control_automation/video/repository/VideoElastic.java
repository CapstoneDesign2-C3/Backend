package capstone.design.control_automation.video.repository;

import capstone.design.control_automation.domain.document.VideoDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface VideoElastic extends ElasticsearchRepository<VideoDocument, String> {
    List<VideoDocument> findBySummaryContaining(String keyword);
}
