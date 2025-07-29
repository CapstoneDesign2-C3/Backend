package capstone.design.control_automation.report.service;

import capstone.design.control_automation.report.util.ReportParam;
import capstone.design.control_automation.report.util.ReportProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportProvider reportProvider;

    public byte[] createDetectedObjectReport(List<ReportParam.Track> reportParams) throws Exception {
        return reportProvider.createDetectedObjectReport(reportParams);
    }
}
