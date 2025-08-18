package capstone.design.control_automation.detection.repository.dto;

import capstone.design.control_automation.report.util.hwp.dto.TableColumn;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;

public class DetectionQueryResult {

    public record Track(
        Long detectionId,
        @TableColumn(name = "출현 장소", order = 1) String cameraScenery,
        byte[] detectionCropImg,
        @TableColumn(name = "출현 시간", order = 2) LocalDateTime appearedTime,
        @TableColumn(name = "퇴장 시간", order = 3) LocalDateTime exitTime
    ) {

        @QueryProjection
        public Track {

        }
    }

    public record Position(
        Long detectionId,
        @TableColumn(name = "위도", order = 1) double latitudeY,
        @TableColumn(name = "경도", order = 2) double longitudeX
    ) {

        @QueryProjection
        public Position {

        }
    }

}
