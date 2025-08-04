package capstone.design.control_automation.report.util;

import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult;
import capstone.design.control_automation.report.util.hwp.dto.TableColumn;
import capstone.design.control_automation.report.util.hwp.dto.TableDataDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReportParam {

    public record Track(
        PublishInfo publishInfo,
        byte[] mapImage,
        byte[] cropImage,
        TableDataDto.MobileObjectInfo mobileObjectInfo,
        List<DetectionQueryResult.Track> trackOfMobileObject
    ) {

    }

    public record Event(
        PublishInfo publishInfo,
        DetectionTimeRange timeRange,
        List<TableDataDto.EventInfo> eventInfos,
        List<TableDataDto.EventCount> eventCounts
    ) {

    }

    public record PublishInfo(
        LocalDate publishDate,
        String author
    ) {

    }

    public record DetectionTimeRange(
        @TableColumn(name = "탐지 시작 시간", order = 1) LocalDateTime startTime,
        @TableColumn(name = "탐지 종료 시간", order = 2) LocalDateTime endTime
    ) {

    }
}
