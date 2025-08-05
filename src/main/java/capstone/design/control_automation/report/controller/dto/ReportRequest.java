package capstone.design.control_automation.report.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ReportRequest {
    public record CreateMobileObjectReport(
        List<Long> mobileObjectIds,
        String author
    ) {}

    public record CreateEventReport(
        LocalDateTime startTime,
        LocalDateTime endTime,
        String author
    ) {

    }
}
