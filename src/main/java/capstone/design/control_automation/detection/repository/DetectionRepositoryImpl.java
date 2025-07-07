package capstone.design.control_automation.detection.repository;

import capstone.design.control_automation.detection.controller.dto.DetectionRequest.Filter;
import capstone.design.control_automation.detection.entity.QDetection;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Position;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Track;
import capstone.design.control_automation.detection.repository.dto.QDetectionQueryResult_Position;
import capstone.design.control_automation.detection.repository.dto.QDetectionQueryResult_Track;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetectionRepositoryImpl implements DetectionRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Track> getTracksByFilterCondition(Filter filter, Pageable pageable) {
        Long count = queryFactory.select(
                QDetection.detection.id.count()
            )
            .from(QDetection.detection)
            .where(QDetection.detection.detectedObject.id.eq(filter.detectedObjectId())
                .and(QDetection.detection.appearedTime.between(filter.startTime(), filter.endTime()))
                .and(QDetection.detection.discoveredTime.between(filter.startTime(), filter.endTime())))
            .fetchOne();

        if (count == 0L) {
            return Page.empty();
        }

        List<Track> tracks = queryFactory.select(new QDetectionQueryResult_Track(
                QDetection.detection.video.id,
                QDetection.detection.thumbnailUrl,
                QDetection.detection.video.summary,
                QDetection.detection.appearedTime,
                QDetection.detection.discoveredTime
            ))
            .from(QDetection.detection)
            .where(QDetection.detection.detectedObject.id.eq(filter.detectedObjectId())
                .and(QDetection.detection.appearedTime.between(filter.startTime(), filter.endTime()))
                .and(QDetection.detection.discoveredTime.between(filter.startTime(), filter.endTime())))
            .orderBy(QDetection.detection.appearedTime.asc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(tracks, pageable, count);
    }

    @Override
    public List<Position> getPositionsByFilterCondition(Filter filter) {
        return queryFactory.select(new QDetectionQueryResult_Position(
                QDetection.detection.video.id,
                QDetection.detection.camera.latitude,
                QDetection.detection.camera.longitude
            ))
            .where(QDetection.detection.detectedObject.id.eq(filter.detectedObjectId())
                .and(QDetection.detection.appearedTime.between(filter.startTime(), filter.endTime()))
                .and(QDetection.detection.discoveredTime.between(filter.startTime(), filter.endTime())))
            .orderBy(QDetection.detection.appearedTime.asc())
            .fetch();
    }
}
