package capstone.design.control_automation.report.util;

import capstone.design.control_automation.report.util.hwp.ColumnProvider;
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
    @Autowired
    ColumnProvider columnProvider;

    @Test
    void createDetectedObjectReport() throws Exception {
        HWPFile hwpFile = hwpReportProvider.createDetectedObjectReport();

        HWPWriter.toFile(hwpFile, "./hwptest/report_sample.hwp");
    }

    @Test
    void reverse() throws Exception {
        columnProvider.reversing();
    }

    @Test
    void createColumnTest() throws Exception {
        HWPFile hwpFile = columnProvider.test();
        HWPWriter.toFile(hwpFile, "./hwptest/column_sample.hwp");
    }

}
