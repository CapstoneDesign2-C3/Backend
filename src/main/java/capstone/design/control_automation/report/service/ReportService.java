package capstone.design.control_automation.report.service;

import capstone.design.control_automation.detected_object.client.MobileObjectFeatureClient;
import capstone.design.control_automation.detected_object.repository.DetectedObjectRepository;
import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult.MobileObject;
import capstone.design.control_automation.detection.repository.DetectionRepository;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Track;
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

    public byte[] createMobileObjectTrackReport(List<Long> mobileObjectIds) throws Exception {
        MobileObject mobileObject = detectedObjectRepository.findById(mobileObjectIds.get(0));

        List<Track> tracks = detectionRepository.getTracksByMobileObjectId(mobileObjectIds.get(0));

        return reportProvider.createDetectedObjectReport(
            LocalDate.now(),
            "이도훈",
            new MobileObjectInfo(
                mobileObject.mobileObjectUuid(),
                mobileObject.alias(),
                mobileObject.categoryName(),
                mobileObjectFeatureClient.getFeatureByUuid(mobileObject.mobileObjectUuid())
            ),
            tracks
        );
    }
}
