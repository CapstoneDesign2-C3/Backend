package capstone.design.control_automation.report.controller;

import java.util.List;

public class ReportRequest {
    public record CreateMobileObject(
        List<Long> mobileObjectIds,
        String author
    ) {}

}
