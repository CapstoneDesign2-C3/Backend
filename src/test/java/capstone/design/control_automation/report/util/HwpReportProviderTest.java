package capstone.design.control_automation.report.util;

import capstone.design.control_automation.report.util.HwpReportProviderTest.TestHwpReportConfig;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.writer.HWPWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestHwpReportConfig.class)
class HwpReportProviderTest {

    @Autowired
    HwpReportProvider hwpReportProvider;

    @Test
    void createDetectedObjectReport() throws Exception {
        HWPFile hwpFile = hwpReportProvider.createDetectedObjectReport();

        HWPWriter.toFile(hwpFile, "C:/Users/Suhyeon/Desktop/hwpTest/report_sample.hwp");
    }

    @TestConfiguration
    public static class TestHwpReportConfig {

        @Bean
        public HwpReportProvider hwpReportProvider() {
            return new HwpReportProvider(); // @PostConstruct 작동
        }
    }
}
