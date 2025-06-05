package capstone.design.control_automation.camera.repository;

import capstone.design.control_automation.camera.entity.Camera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Long> {
    @Query("SELECT DISTINCT c.angle FROM Camera c WHERE c.angle IS NOT NULL")
    List<String> findDistinctAngles();
}
