package capstone.design.control_automation.event.repository;

import capstone.design.control_automation.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByKeyword(String keyword);
    @Query("SELECT DISTINCT e.keyword FROM Event e WHERE e.keyword IS NOT NULL")
    List<String> findDistinctKeywords();
}
