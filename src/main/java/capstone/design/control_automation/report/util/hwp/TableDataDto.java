package capstone.design.control_automation.report.util.hwp;

public class TableDataDto {

    public record MobileObjectInfo(
        @TableColumn(name = "uuid", order = 1) String uuid,
        @TableColumn(name = "별칭", order = 2) String alias,
        @TableColumn(name = "클래스", order = 3) String className,
        @TableColumn(name = "특징", order = 4) String feature
    ) {

    }
}
