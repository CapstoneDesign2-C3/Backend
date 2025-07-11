package capstone.design.control_automation.video.repository;

import capstone.design.control_automation.video.entity.Video;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoJpaRepository extends JpaRepository<Video, Long> {

    Page<Video> findByIdIn(Pageable pageable, Set<Long> finalIds);
}
