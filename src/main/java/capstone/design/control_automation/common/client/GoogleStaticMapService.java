package capstone.design.control_automation.common.client;

import capstone.design.control_automation.common.exception.ExternalApiErrorCode;
import capstone.design.control_automation.common.exception.ExternalApiException;
import capstone.design.control_automation.common.properties.GoogleStaticMapProperties;
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

    private final GoogleStaticMapApiClient googleStaticMapApiClient;
    private final GoogleStaticMapUriBuilder uriBuilder;
    private final GoogleStaticMapProperties googleStaticMapProperties;

    public byte[] getStaticMap(MapRequest request) throws ExternalApiException {
        URI uri = uriBuilder.buildUri(request, googleStaticMapProperties.apiKey());
        return googleStaticMapApiClient.exchange(uri);
    }
}
