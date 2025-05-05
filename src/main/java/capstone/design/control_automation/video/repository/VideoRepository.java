package capstone.design.control_automation.video.repository;

import capstone.design.control_automation.video.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    Video findAllById(Long id);
}
