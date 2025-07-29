package capstone.design.control_automation.report.service;

import capstone.design.control_automation.common.client.GoogleStaticMapApiClient;
import capstone.design.control_automation.common.client.MapRequest;
import capstone.design.control_automation.detected_object.client.MobileObjectFeatureClient;
import capstone.design.control_automation.detected_object.repository.DetectedObjectRepository;
import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult.MobileObject;
import capstone.design.control_automation.detection.controller.dto.DetectionRequest;
import capstone.design.control_automation.detection.controller.dto.DetectionRequest.Filter;
import capstone.design.control_automation.detection.repository.DetectionRepository;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Position;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Track;
import capstone.design.control_automation.report.util.ReportParam;
import capstone.design.control_automation.report.util.ReportProvider;
import capstone.design.control_automation.report.util.hwp.TableDataDto.MobileObjectInfo;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

    private final DetectedObjectRepository detectedObjectRepository;
    private final DetectionRepository detectionRepository;
    private final ReportProvider reportProvider;
    private final MobileObjectFeatureClient mobileObjectFeatureClient;
    private final GoogleStaticMapApiClient googleStaticMapApiClient;

    public byte[] createMobileObjectTrackReport(List<Long> mobileObjectIds, String author) throws Exception {
        List<ReportParam.Track> reportParams = mobileObjectIds.stream().map(id -> {
            MobileObject mobileObject = detectedObjectRepository.findById(id);
            List<Track> tracks = detectionRepository.getTracksByMobileObjectId(id);
            List<Position> positions = detectionRepository.getPositionsByFilterCondition(new Filter(id, null, null));

            return new ReportParam.Track(
                LocalDate.now(),
                author,
                googleStaticMapApiClient.requestStaticMap(new MapRequest(
                    positions
                )),
                new MobileObjectInfo(
                    mobileObject.mobileObjectUuid(),
                    mobileObject.alias(),
                    mobileObject.categoryName(),
                    mobileObjectFeatureClient.getFeatureByUuid(mobileObject.mobileObjectUuid())
                ),
                tracks
            );
        }).toList();

        return reportProvider.createDetectedObjectReport(reportParams);
    }
}
