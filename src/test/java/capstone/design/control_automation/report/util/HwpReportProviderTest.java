package capstone.design.control_automation.report.util;

import capstone.design.control_automation.report.util.hwp.HwpReportProvider;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.writer.HWPWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@TestPropertySource("classpath:local.env")
class HwpReportProviderTest {

    @Autowired
    HwpReportProvider hwpReportProvider;

    @Test
    void createDetectedObjectReport() throws Exception {
        HWPFile hwpFile = hwpReportProvider.createDetectedObjectReport();

        HWPWriter.toFile(hwpFile, "../hwptest/report_sample.hwp");
    }

}
