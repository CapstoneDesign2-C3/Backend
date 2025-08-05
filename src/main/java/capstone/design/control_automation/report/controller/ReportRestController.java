package capstone.design.control_automation.report.controller;

import capstone.design.control_automation.report.controller.dto.ReportRequest.CreateEventReport;
import capstone.design.control_automation.report.controller.dto.ReportRequest.CreateMobileObjectReport;
import capstone.design.control_automation.report.service.ReportFacade;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    private final ReportFacade reportFacade;

    @PostMapping("/create-mobile-object-track")
    public ResponseEntity<byte[]> createMobileObjectTrackingReport(@RequestBody CreateMobileObjectReport createMobileObjectREport) throws Exception {
        byte[] report = reportFacade.createMobileObjectTrackReport(
            createMobileObjectREport.mobileObjectIds(),
            createMobileObjectREport.author()
        );

        String filename = URLEncoder.encode("track_report.hwp", StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment().filename(filename).build());

        return new ResponseEntity<>(report, headers, HttpStatus.OK);
    }

    @PostMapping("/create-event")
    public ResponseEntity<byte[]> createEventReport(@RequestBody CreateEventReport createEventReport) throws Exception {
        byte[] report = reportFacade.createEventReport(
            createEventReport.startTime(),
            createEventReport.endTime(),
            createEventReport.author()
        );

        String filename = URLEncoder.encode("event_report.hwp", StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment().filename(filename).build());

        return new ResponseEntity<>(report, headers, HttpStatus.OK);
    }
}
