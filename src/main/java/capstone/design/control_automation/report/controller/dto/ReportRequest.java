package capstone.design.control_automation.report.controller.dto;

import java.util.List;

public class ReportRequest {
    public record CreateMobileObject(
        List<Long> mobileObjectIds,
        String author
    ) {}

}
