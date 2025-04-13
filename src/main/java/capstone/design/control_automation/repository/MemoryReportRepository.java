package capstone.design.control_automation.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryReportRepository implements ReportRepository {


    @Override
    public List<LocalDateTime> getReportDateTimes() {
        return List.of(
            LocalDateTime.of(2025, 3, 1, 10, 0),
            LocalDateTime.of(2025, 3, 2, 14, 30),
            LocalDateTime.of(2025, 3, 3, 9, 15),
            LocalDateTime.of(2025, 3, 4, 16, 45),
            LocalDateTime.of(2025, 3, 5, 11, 0)
        );
    }
}
