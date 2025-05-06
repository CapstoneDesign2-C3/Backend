package capstone.design.control_automation.camera.service;

import capstone.design.control_automation.camera.dto.CameraRequest;
import capstone.design.control_automation.camera.dto.CameraResponse;
import capstone.design.control_automation.camera.entity.Camera;
import capstone.design.control_automation.camera.repository.CameraRepository;
import capstone.design.control_automation.camera.entity.Address;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CameraService {
    private final CameraRepository cameraRepository;

    @Transactional
    public void createCamera(CameraRequest cameraRequest){
        Camera camera = new Camera(cameraRequest.latitude(), cameraRequest.longitude(), cameraRequest.angle(),
                new Address(cameraRequest.address1(), cameraRequest.address2()),
                cameraRequest.status());

        cameraRepository.save(camera);
    }

    @Transactional
    public void deleteCamera(Long id){
        cameraRepository.deleteById(id);
    }

    public List<CameraResponse> getAllCamera(){
        List<Camera> cameras = cameraRepository.findAll();

        return cameras.stream().map(Camera::of).toList();
    }
}
