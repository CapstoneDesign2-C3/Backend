package capstone.design.control_automation.report.service;

import capstone.design.control_automation.common.client.GoogleStaticMapService;
import capstone.design.control_automation.common.client.MapRequest;
import capstone.design.control_automation.detected_object.client.MobileObjectFeatureClient;
import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult.MobileObject;
import capstone.design.control_automation.detected_object.service.DetectedObjectService;
import capstone.design.control_automation.detection.controller.dto.DetectionRequest.Filter;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Position;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Track;
import capstone.design.control_automation.detection.service.DetectionService;
import capstone.design.control_automation.event.service.EventService;
import capstone.design.control_automation.report.util.ReportParam;
import capstone.design.control_automation.report.util.ReportParam.DetectionTimeRange;
import capstone.design.control_automation.report.util.ReportParam.Event;
import capstone.design.control_automation.report.util.ReportParam.PublishInfo;
import capstone.design.control_automation.report.util.hwp.dto.TableDataDto;
import capstone.design.control_automation.report.util.hwp.dto.TableDataDto.MobileObjectInfo;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final GoogleStaticMapService googleStaticMapService;
    private final EventService eventService;

    public byte[] createMobileObjectTrackReport(List<Long> mobileObjectIds, String author) throws Exception {
        List<ReportParam.Track> reportParams = mobileObjectIds.stream().map(id -> {
            MobileObject mobileObject = detectedObjectService.findById(id);
            List<Track> tracks = detectionService.getRecent10TracksByMobileObjectId(id);
            List<Position> positions = detectionService.getPositionsByFilterCondition(new Filter(id, null, null, 10));

            return new ReportParam.Track(
                new PublishInfo(
                    LocalDate.now(),
                    author
                ),
                googleStaticMapService.getStaticMap(
                    new MapRequest(
                        positions
                    )
                ),
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

    public byte[] createEventReport(LocalDateTime startTime, LocalDateTime endTime, String author) throws Exception {
        List<TableDataDto.EventInfo> events = eventService.findEventsByTimeRange(startTime, endTime);
        List<TableDataDto.EventCount> counts = eventService.findEventCountsByTimeRange(startTime, endTime);

        return reportService.createEventReport(
            new Event(
                new PublishInfo(
                    LocalDate.now(),
                    author
                ),
                new DetectionTimeRange(
                    startTime,
                    endTime
                ),
                events,
                counts
            )
        );
    }
}
