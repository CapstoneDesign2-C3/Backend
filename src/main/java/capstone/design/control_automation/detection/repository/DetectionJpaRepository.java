package capstone.design.control_automation.detection.repository;

import capstone.design.control_automation.detection.entity.Detection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectionJpaRepository extends JpaRepository<Detection, Long> {

}
