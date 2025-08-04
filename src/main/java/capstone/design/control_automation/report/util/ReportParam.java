package capstone.design.control_automation.report.util;

import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Track;
import capstone.design.control_automation.report.util.hwp.TableDataDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReportParam {

    public record Track(
        LocalDate date,
        String author,
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
        LocalDateTime startTime,
        LocalDateTime endTime
    ) {

    }
}
