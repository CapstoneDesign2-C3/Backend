package capstone.design.control_automation.common.client;

import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Position;
import java.net.URI;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

@Slf4j
@Component
public class GoogleStaticMapApiClient {


    private static final String STATIC_MAP_WARNING_HEADER = "X-Staticmap-API-Warning";

    private final RestClient googleClient;
    private final GoogleMapProperties googleMapProperties;

    public GoogleStaticMapApiClient(@Qualifier("googleClient") RestClient googleClient, GoogleMapProperties googleMapProperties) {
        this.googleClient = googleClient;
        this.googleMapProperties = googleMapProperties;
    }

    public byte[] requestStaticMap(MapRequest request) {
        return googleClient.get()
            .uri(uriBuilder -> buildUri(request, uriBuilder)
            )
            .exchange((req, res) -> {
                if (res.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST)) {
                    throw new ExternalApiException(ExternalApiErrorCode.GOOGLE_STATIC_MAP_INVALID_PARAM);
                }

                if (res.getStatusCode().isSameCodeAs(HttpStatus.FORBIDDEN)) {
                    throw new ExternalApiException(ExternalApiErrorCode.GOOGLE_STATIC_MAP_INVALID_KEY);
                }

                HttpHeaders headers = res.getHeaders();
                if (res.getStatusCode().is2xxSuccessful() && headers.containsKey(STATIC_MAP_WARNING_HEADER)) {
                    log.warn("Google Static Map warning : {}", headers.get(STATIC_MAP_WARNING_HEADER));
                }

                return res.bodyTo(byte[].class);
            });
    }

    private URI buildUri(MapRequest request, UriBuilder uriBuilder) {
        UriBuilder tempUriBuilder = uriBuilder
            .path("/maps/api/staticmap")
            .queryParam("size", "600x300")
            .queryParam("key", googleMapProperties.getApiKey());

        List<Position> positions = request.positions();
        for (int i = 0; i < positions.size(); i++) {
            Position position = positions.get(i);
            tempUriBuilder.queryParam("markers", "color:blue|label:" + (i + 1) + "|" +
                position.latitudeY() + "," + position.longitudeX());

            if (i < positions.size() - 1) {
                Position next = positions.get(i + 1);
                tempUriBuilder.queryParam("path", "color:blue|weight:5|" +
                    position.latitudeY() + "," + position.longitudeX() + "|" +
                    next.latitudeY() + "," + next.longitudeX());
            }
        }
        return tempUriBuilder.build();
    }

}
