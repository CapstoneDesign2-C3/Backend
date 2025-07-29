package capstone.design.control_automation.dashboard.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

public class DashboardResponse {
    public record Pie(
            String id,
            int value
    ){}

    public record Line(
            String id,
            List<Data> data
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
