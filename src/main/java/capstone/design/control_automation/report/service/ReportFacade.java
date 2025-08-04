package capstone.design.control_automation.report.service;

import capstone.design.control_automation.common.client.GoogleStaticMapApiClient;
import capstone.design.control_automation.common.client.MapRequest;
import capstone.design.control_automation.detected_object.client.MobileObjectFeatureClient;
import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult.MobileObject;
import capstone.design.control_automation.detected_object.service.DetectedObjectService;
import capstone.design.control_automation.detection.controller.dto.DetectionRequest.Filter;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Position;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Track;
import capstone.design.control_automation.detection.service.DetectionService;
import capstone.design.control_automation.report.util.ReportParam;
import capstone.design.control_automation.report.util.hwp.TableDataDto.MobileObjectInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportFacade {

    private final ReportService reportService;
    private final DetectionService detectionService;
    private final DetectedObjectService detectedObjectService;
    private final MobileObjectFeatureClient mobileObjectFeatureClient;
    private final GoogleStaticMapApiClient googleStaticMapApiClient;

    public byte[] createMobileObjectTrackReport(List<Long> mobileObjectIds, String author) throws Exception {
        List<ReportParam.Track> reportParams = mobileObjectIds.stream().map(id -> {
            MobileObject mobileObject = detectedObjectService.findById(id);
            List<Track> tracks = detectionService.getTracksByMobileObjectId(id);
            List<Position> positions = detectionService.getPositionsByFilterCondition(new Filter(id, null, null));

            return new ReportParam.Track(
                LocalDate.now(),
                author,
                googleStaticMapApiClient.requestStaticMap(new MapRequest(
                    positions
                )),
                mobileObject.cropImg(),
                new MobileObjectInfo(
                    mobileObject.mobileObjectUuid(),
                    mobileObject.alias(),
                    mobileObject.categoryName(),
                    mobileObjectFeatureClient.getFeatureByUuid(mobileObject.mobileObjectUuid())
                ),
                tracks
            );
        }).toList();

        return reportService.createDetectedObjectReport(reportParams);
    }

    private byte[] loadFile() throws IOException {
        File file = new File("./hwptest/crop.png");
        byte[] buffer = new byte[(int) file.length()];
        InputStream ios = null;
        try {
            ios = new FileInputStream(file);
            ios.read(buffer);
        } finally {
            try {
                if (ios != null) {
                    ios.close();
                }
            } catch (IOException e) {
            }
        }
        return buffer;
    }
}
