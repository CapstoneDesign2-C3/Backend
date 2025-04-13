package capstone.design.control_automation.repository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportRepository {

    List<LocalDateTime> getReportDateTimes();


}
