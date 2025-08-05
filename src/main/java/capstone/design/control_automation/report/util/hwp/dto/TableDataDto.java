package capstone.design.control_automation.report.util.hwp.dto;

import capstone.design.control_automation.event.repository.dto.EventQueryResult.InfoForTable;
import java.time.LocalDateTime;

public class TableDataDto {

    public record MobileObjectInfo(
        @TableColumn(name = "uuid", order = 1) String uuid,
        @TableColumn(name = "별칭", order = 2) String alias,
        @TableColumn(name = "클래스", order = 3) String className,
        @TableColumn(name = "특징", order = 4) String feature
    ) {

    }

    public record EventInfo(
        @TableColumn(name = "UUID", order = 1) String uuid,
        @TableColumn(name = "이벤트 종류", order = 2) String eventType,
        @TableColumn(name = "출현 장소", order = 3) String detectedPlace,
        @TableColumn(name = "탐지 시작 시간", order = 4) LocalDateTime startTime,
        @TableColumn(name = "탐지 종료 시간", order = 5) LocalDateTime endTime
    ) {

        public static EventInfo from(InfoForTable infoForTable) {
            return new EventInfo(
                infoForTable.uuid(),
                infoForTable.eventType(),
                infoForTable.detectedPlace(),
                infoForTable.startFrameAt(),
                infoForTable.endFrameAt()
            );
        }
    }

    public record EventCount(
        @TableColumn(name = "이벤트 종류", order = 1) String eventType,
        @TableColumn(name = "발생 건 수", order = 2) Integer count
    ) {

    }

    public record Note(
        @TableColumn(name = "비고") String content
    ) {

    }
}
