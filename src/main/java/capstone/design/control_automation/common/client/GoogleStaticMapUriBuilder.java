package capstone.design.control_automation.common.client;

import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Position;
import java.net.URI;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GoogleStaticMapUriBuilder {

    public URI buildUri(MapRequest request, String apiKey) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/maps/api/staticmap")
            .queryParam("size", "600x300")
            .queryParam("key", apiKey);

        List<Position> positions = request.positions();
        for (int i = 0; i < positions.size(); i++) {
            Position position = positions.get(i);
            addMarkerToQueryParam(builder, i, position);

            if (i < positions.size() - 1) {
                Position next = positions.get(i + 1);
                addPathToQueryParam(builder, position, next);
            }
        }

        return builder.build().toUri();
    }

    private void addMarkerToQueryParam(UriComponentsBuilder builder, int num, Position position) {
        builder.queryParam("markers", "color:blue|label:" + (num + 1) + "|" +
            position.latitudeY() + "," + position.longitudeX());
    }

    private void addPathToQueryParam(UriComponentsBuilder builder, Position position, Position next) {
        builder.queryParam("path", "color:blue|weight:5|" +
            position.latitudeY() + "," + position.longitudeX() + "|" +
            next.latitudeY() + "," + next.longitudeX());
    }
}
