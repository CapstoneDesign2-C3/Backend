package capstone.design.control_automation.report.util;

import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Track;
import capstone.design.control_automation.report.util.hwp.TableDataDto;
import java.time.LocalDate;
import java.util.List;

public interface ReportProvider {

    byte[] createDetectedObjectReport(
        LocalDate date,
        String author,
        TableDataDto.MobileObjectInfo mobileObjectInfo,
        List<Track> trackOfMobileObject
    ) throws Exception;
}
