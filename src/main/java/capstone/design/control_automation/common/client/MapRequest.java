package capstone.design.control_automation.common.client;

import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Position;
import java.util.List;

public record MapRequest(
    List<Position> positions
) {

}
