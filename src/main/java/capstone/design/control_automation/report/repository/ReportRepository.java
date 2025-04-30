package capstone.design.control_automation.report.repository;

import capstone.design.control_automation.domain.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long>, ReportCustomRepository {

}
