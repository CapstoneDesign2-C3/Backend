package capstone.design.control_automation.detection.repository;

import capstone.design.control_automation.detection.controller.dto.DetectionRequest.Filter;
import capstone.design.control_automation.detection.entity.QDetection;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Position;
import capstone.design.control_automation.detection.repository.dto.QDetectionQueryResult_Position;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public abstract class DetectionQueryDSLRepository implements DetectionRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Position> getPositionsByFilterCondition(Filter filter) {
        return queryFactory.select(new QDetectionQueryResult_Position(
                QDetection.detection.id,
                QDetection.detection.camera.latitude,
                QDetection.detection.camera.longitude
            ))
            .where(QDetection.detection.detectedObject.id.eq(filter.detectedObjectId())
                .and(QDetection.detection.appearedTime.between(filter.startTime(), filter.endTime())
                    .or(QDetection.detection.exitTime.between(filter.startTime(), filter.endTime())
                    )
                )
            )
            .orderBy(QDetection.detection.appearedTime.asc())
            .fetch();
    }
}
