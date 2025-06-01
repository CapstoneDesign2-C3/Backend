package capstone.design.control_automation.detected_object.service;

import capstone.design.control_automation.camera.entity.Camera;
import capstone.design.control_automation.camera.repository.CameraRepository;
import capstone.design.control_automation.common.exception.ErrorCode;
import capstone.design.control_automation.common.exception.ErrorException;
import capstone.design.control_automation.detected_object.document.DetectedObjectDocument;
import capstone.design.control_automation.detected_object.dto.DetectedObjectRequest;
import capstone.design.control_automation.detected_object.dto.DetectedObjectResponse;
import capstone.design.control_automation.detected_object.entity.DetectedObject;
import capstone.design.control_automation.detected_object.entity.QDetectedObject;
import capstone.design.control_automation.detected_object.repository.DetectedObjectElastic;
import capstone.design.control_automation.detected_object.repository.DetectedObjectRepository;
import capstone.design.control_automation.event.entity.Event;
import capstone.design.control_automation.event.repository.EventRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetectedObjectService {

    private final DetectedObjectRepository detectedObjectRepository;
    private final EventRepository eventRepository;
    private final CameraRepository cameraRepository;
    private final JPAQueryFactory queryFactory;
    private final DetectedObjectElastic detectedObjectElastic;

    @Transactional
    public void createDetectedObject(DetectedObjectRequest.Upsert upsert) {
        Event event = eventRepository.findById(upsert.eventId())
            .orElseThrow(() -> new ErrorException(ErrorCode.CAMERA_NOT_FOUND));
        Camera camera = cameraRepository.findById(upsert.cameraId())
            .orElseThrow(() -> new ErrorException(ErrorCode.CAMERA_NOT_FOUND));

        DetectedObject detectedObject = new DetectedObject(upsert.reId(),
            upsert.feature(),
            upsert.startFrame(),
            upsert.endFrame(),
            upsert.videoUrl(),
            camera,
            event);

        detectedObjectRepository.save(detectedObject);

        DetectedObjectDocument detectedObjectDocument = new DetectedObjectDocument(detectedObject.getId().toString(),
            detectedObject.getFeature());
        detectedObjectElastic.save(detectedObjectDocument);
    }

    @Transactional
    public void deleteDetectedObject(Long id) {
        detectedObjectRepository.deleteById(id);
        detectedObjectElastic.deleteById(id.toString());
    }

    public Page<DetectedObjectResponse> getDetectedObject(Pageable pageable) {
        return detectedObjectRepository.findAll(pageable).map(DetectedObject::mapToResponse);
    }

    public Page<DetectedObjectResponse> findDetectedObject(Pageable pageable,
        DetectedObjectRequest.Search detectedObjectSearchRequest) {
        List<Long> postgresIds = findIdsByQueryFactory(detectedObjectSearchRequest);
        List<Long> documents = (detectedObjectSearchRequest.feature() == null) ?
            new ArrayList<>() : detectedObjectElastic.findByFeatureContaining(detectedObjectSearchRequest.feature()).stream()
            .map(detectedObject -> Long.valueOf(detectedObject.getId()))
            .toList();

        Set<Long> finalIds;
        if (documents.isEmpty()) {
            finalIds = new HashSet<>(postgresIds);
        } else {
            finalIds = postgresIds.stream()
                .filter(documents::contains)
                .collect(Collectors.toSet());
        }

        return detectedObjectRepository.findByIdIn(pageable, finalIds).map(DetectedObject::mapToResponse);
    }

    public List<Long> findIdsByQueryFactory(DetectedObjectRequest.Search detectedObjectSearchRequest) {
        QDetectedObject detectedObject = QDetectedObject.detectedObject;

        return queryFactory
            .select(detectedObject.id)
            .from(detectedObject)
            .where(
                detectedObjectSearchRequest.eventId() != null ? detectedObject.event.id.eq(detectedObjectSearchRequest.eventId())
                    : null,
                detectedObjectSearchRequest.cameraId() != null ? detectedObject.camera.id.eq(
                    detectedObjectSearchRequest.cameraId()) : null,
                detectedObjectSearchRequest.reId() != null ? detectedObject.reId.eq(detectedObjectSearchRequest.reId()) : null
            ).fetch();
    }
}
