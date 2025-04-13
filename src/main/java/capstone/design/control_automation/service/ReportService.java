package capstone.design.control_automation.service;

import capstone.design.control_automation.repository.ReportRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
// @Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public List<LocalDateTime> getReportDateTimes() {
        return reportRepository.getReportDateTimes();
    }
}
