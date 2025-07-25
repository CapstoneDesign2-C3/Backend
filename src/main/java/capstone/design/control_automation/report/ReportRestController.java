package capstone.design.control_automation.report;

import capstone.design.control_automation.report.service.ReportService;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/report")
@RequiredArgsConstructor
public class ReportRestController {

    private final ReportService reportService;

    @PostMapping("/create-mobile-object-track")
    public ResponseEntity<byte[]> createMobileObjectTrackingReport(@RequestBody List<Long> mobileObjectIds) throws Exception {
        byte[] report = reportService.createMobileObjectTrackReport(mobileObjectIds);

        String filename = URLEncoder.encode("sample.hwp", StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment().filename(filename).build());

        return new ResponseEntity<>(report, headers, HttpStatus.OK);
    }
}
