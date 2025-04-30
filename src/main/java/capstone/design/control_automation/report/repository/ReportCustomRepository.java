package capstone.design.control_automation.report.repository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportCustomRepository {

    List<LocalDateTime> getReportDateTimes();


}
