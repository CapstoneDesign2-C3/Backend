package capstone.design.control_automation.detected_object.repository;

import capstone.design.control_automation.detected_object.entity.DetectedObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DetectedObjectRepository extends JpaRepository<DetectedObject, Long> {
    Page<DetectedObject> findByIdIn(Pageable pageable, Set<Long> ids);
}
