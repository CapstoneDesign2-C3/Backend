package capstone.design.control_automation.camera.repository;

import capstone.design.control_automation.camera.controller.dto.CameraRequest.Filter;
import capstone.design.control_automation.camera.entity.QCamera;
import capstone.design.control_automation.camera.repository.dto.CameraQueryResult.Position;
import capstone.design.control_automation.camera.repository.dto.QCameraQueryResult_Position;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CameraQueryDSLRepository implements CameraRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Position> findAllByFilterCondition(Filter filter) {
        return queryFactory.select(new QCameraQueryResult_Position(
                QCamera.camera.id,
                QCamera.camera.latitude,
                QCamera.camera.longitude
            ))
            .from(QCamera.camera)
            .where(QCamera.camera.latitude.between(filter.bottomRightLatitude(), filter.topLeftLatitude())
                .and(QCamera.camera.longitude.between(filter.topLeftLongitude(), filter.bottomRightLongitude())))
            .fetch();
    }

}
