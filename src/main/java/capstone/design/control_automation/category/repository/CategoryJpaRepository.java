package capstone.design.control_automation.category.repository;

import capstone.design.control_automation.category.entity.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    List<Category> findAll();
}
