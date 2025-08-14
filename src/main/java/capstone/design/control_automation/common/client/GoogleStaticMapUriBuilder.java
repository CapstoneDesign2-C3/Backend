package capstone.design.control_automation.common.client;

import capstone.design.control_automation.common.properties.GoogleStaticMapStyleProperties;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Position;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class GoogleStaticMapUriBuilder {

    private final GoogleStaticMapStyleProperties styleProperties;

    public URI buildUri(MapRequest request, String apiKey) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/maps/api/staticmap")
            .queryParam("size", styleProperties.mapSize())
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
        String markerValue = String.format("color:%s|label:%d|%s,%s",
            styleProperties.color(),
            num + 1,
            position.latitudeY(),
            position.longitudeX());
        builder.queryParam("markers", markerValue);
    }

    private void addPathToQueryParam(UriComponentsBuilder builder, Position cur, Position next) {
        String pathValue = String.format("color:%s|weight:%d|%s,%s|%s,%s",
            styleProperties.color(),
            styleProperties.weight(),
            cur.latitudeY(), cur.longitudeX(),
            next.latitudeY(), next.longitudeX()
        );

        builder.queryParam("path", pathValue);
    }
}
