package capstone.design.control_automation.detected_object.service;

import capstone.design.control_automation.camera.entity.Camera;
import capstone.design.control_automation.camera.repository.CameraRepository;
import capstone.design.control_automation.common.exception.ErrorCode;
import capstone.design.control_automation.common.exception.ErrorException;
import capstone.design.control_automation.detected_object.dto.DetectedObjectRequest;
import capstone.design.control_automation.detected_object.dto.DetectedObjectResponse;
import capstone.design.control_automation.detected_object.entity.DetectedObject;
import capstone.design.control_automation.detected_object.repository.DetectedObjectRepository;
import capstone.design.control_automation.event.entity.Event;
import capstone.design.control_automation.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetectedObjectService {
    private final DetectedObjectRepository detectedObjectRepository;
    private final EventRepository eventRepository;
    private final CameraRepository cameraRepository;

    @Transactional
    public void createDetectedObject(DetectedObjectRequest detectedObjectRequest){
        Event event = eventRepository.findById(detectedObjectRequest.eventId())
                .orElseThrow(() -> new ErrorException(ErrorCode.CAMERA_NOT_FOUND));
        Camera camera = cameraRepository.findById(detectedObjectRequest.eventId())
                .orElseThrow(() -> new ErrorException(ErrorCode.CAMERA_NOT_FOUND));

        DetectedObject detectedObject = new DetectedObject(detectedObjectRequest.reId(),
                detectedObjectRequest.feature(),
                detectedObjectRequest.startFrame(),
                detectedObjectRequest.endFrame(),
                detectedObjectRequest.videoUrl(),
                camera,
                event);

        detectedObjectRepository.save(detectedObject);
    }

    public List<DetectedObjectResponse> getDetectedObject(){
        return detectedObjectRepository.findAll().stream().map(DetectedObject::mapToResponse).toList();
    }
}
