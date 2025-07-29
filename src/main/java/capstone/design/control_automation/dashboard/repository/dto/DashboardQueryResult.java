package capstone.design.control_automation.dashboard.repository.dto;

import java.time.LocalDateTime;

public class DashboardQueryResult {
    public record Pie(
            String id,
            int value
    ){}

    public record Bar(
            String id,
            int data
    ){}

    public record Data(
            LocalDateTime x,
            int y
    ){}
}
