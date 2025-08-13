package capstone.design.control_automation.common.client;

import static org.assertj.core.api.Assertions.assertThat;

import capstone.design.control_automation.common.properties.GoogleStaticMapStyleProperties;
import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Position;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;

class GoogleStaticMapUriBuilderTest {

    GoogleStaticMapUriBuilder googleStaticMapUriBuilder;

    private final static String MAP_WIDTH = "600";
    private final static String MAP_HEIGHT = "300";
    private final static String COMPONENT_COLOR = "blue";
    private final static Integer PATH_WEIGHT = 5;

    @BeforeEach
    void setUp() {
        googleStaticMapUriBuilder = new GoogleStaticMapUriBuilder(
            new GoogleStaticMapStyleProperties(MAP_WIDTH, MAP_HEIGHT, COMPONENT_COLOR, PATH_WEIGHT)
        );
    }

    @Test
    @DisplayName("2곳의 정보가 담기면 2개의 Marker, 1개의 Path가 URI에 담겨야 한다.")
    void MARKER와_PATH정보가_URI에_잘_담기는_지_확인() {
        //given
        Position position1 = new Position(1L, 37.4740359, 127.1027386);
        Position position2 = new Position(2L, 37.4750659, 127.1034386);
        String apiKey = "myApiKey";

        URI expected = getExpectedURI(apiKey, position1, position2);

        //when
        URI actual = googleStaticMapUriBuilder.buildUri(
            new MapRequest(List.of(
                    position1,
                    position2
            )),
            apiKey
        );
        //then

        assertThat(actual).isEqualTo(expected);
    }

    private URI getExpectedURI(String apiKey, Position position1, Position position2) {
        return UriComponentsBuilder.fromPath("/maps/api/staticmap")
            .queryParam("size", String.format("%sx%s", MAP_WIDTH, MAP_HEIGHT))
            .queryParam("key", apiKey)
            .queryParam("markers", String.format("color:%s|label:%d|%s,%s",
                    COMPONENT_COLOR,
                    1,
                    position1.latitudeY(),
                    position1.longitudeX()
                )
            )
            .queryParam("markers", String.format("color:%s|label:%d|%s,%s",
                    COMPONENT_COLOR,
                    2,
                    position2.latitudeY(),
                    position2.longitudeX()
                )
            )
            .queryParam("path", String.format("color:%s|weight:%d|%s,%s|%s,%s",
                    COMPONENT_COLOR,
                    PATH_WEIGHT,
                    position1.latitudeY(), position1.longitudeX(),
                    position2.latitudeY(), position2.longitudeX()
                )
            )
            .build().toUri();
    }
}
