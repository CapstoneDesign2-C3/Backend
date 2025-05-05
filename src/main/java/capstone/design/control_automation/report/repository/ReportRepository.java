package capstone.design.control_automation.report.repository;

import capstone.design.control_automation.report.entity.Report;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("select r.createdAt from Report r")
    List<LocalDateTime> getReportDataTime();
}
