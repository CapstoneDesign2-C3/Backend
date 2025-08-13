package capstone.design.control_automation.report.service;

import capstone.design.control_automation.common.client.ExternalApiErrorCode;
import capstone.design.control_automation.common.client.ExternalApiException;
import capstone.design.control_automation.common.client.GoogleMapProperties;
import capstone.design.control_automation.common.client.GoogleStaticMapApiClient;
import capstone.design.control_automation.common.client.MapRequest;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleStaticMapService {

    private static final String STATIC_MAP_WARNING_HEADER = "X-Staticmap-API-Warning";

    private final GoogleStaticMapApiClient googleStaticMapApiClient;
    private final GoogleStaticMapUriBuilder uriBuilder;
    private final GoogleMapProperties googleMapProperties;

    public byte[] getStaticMap(MapRequest request) throws ExternalApiException {
        URI uri = uriBuilder.buildUri(request, googleMapProperties.getApiKey());

        ResponseEntity<byte[]> response = googleStaticMapApiClient.exchange(uri);

        HttpStatus statusCode = (HttpStatus) response.getStatusCode();
        if (statusCode.isSameCodeAs(HttpStatus.BAD_REQUEST)) {
            throw new ExternalApiException(ExternalApiErrorCode.GOOGLE_STATIC_MAP_INVALID_PARAM);
        }
        if (statusCode.isSameCodeAs(HttpStatus.FORBIDDEN)) {
            throw new ExternalApiException(ExternalApiErrorCode.GOOGLE_STATIC_MAP_INVALID_KEY);
        }

        HttpHeaders headers = response.getHeaders();
        if (statusCode.is2xxSuccessful() && headers.containsKey(STATIC_MAP_WARNING_HEADER)) {
            log.warn("Google Static Map warning : {}", headers.get(STATIC_MAP_WARNING_HEADER));
        }

        return response.getBody();
    }
}
