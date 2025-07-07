package capstone.design.control_automation.camera.repository;

import capstone.design.control_automation.camera.entity.Camera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CameraJpaRepository extends JpaRepository<Camera, Long> {
}
