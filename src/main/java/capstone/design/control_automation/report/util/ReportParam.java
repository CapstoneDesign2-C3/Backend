package capstone.design.control_automation.report.util;

import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Track;
import capstone.design.control_automation.report.util.hwp.TableDataDto;
import java.time.LocalDate;
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
}
