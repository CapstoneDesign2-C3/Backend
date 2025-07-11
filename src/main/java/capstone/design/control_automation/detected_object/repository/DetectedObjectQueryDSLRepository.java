package capstone.design.control_automation.detected_object.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public abstract class DetectedObjectQueryDSLRepository implements DetectedObjectRepository {

    private final JPAQueryFactory queryFactory;

}
