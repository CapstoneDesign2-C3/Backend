package capstone.design.control_automation.report.util;

import java.util.List;

public interface ReportProvider {

    byte[] createDetectedObjectReport(
        List<ReportParam.Track> tracks
    ) throws Exception;

}
