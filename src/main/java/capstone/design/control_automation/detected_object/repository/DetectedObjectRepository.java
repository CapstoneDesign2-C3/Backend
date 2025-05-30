package capstone.design.control_automation.detected_object.repository;

import capstone.design.control_automation.detected_object.entity.DetectedObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectedObjectRepository extends JpaRepository<DetectedObject, Long> {
}
