package capstone.design.control_automation.video.repository;

import capstone.design.control_automation.video.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface VideoJpaRepository extends JpaRepository<Video, Long> {
    Page<Video> findByIdIn(Pageable pageable, Set<Long> finalIds);
}
