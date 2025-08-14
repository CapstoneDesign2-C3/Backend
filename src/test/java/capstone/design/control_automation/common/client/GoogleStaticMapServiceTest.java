package capstone.design.control_automation.common.client;

import static org.assertj.core.api.Assertions.assertThat;

import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Position;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.StreamUtils;

@SpringBootTest
@AutoConfigureWireMock(port = 0)
@TestPropertySource("classpath:local.env")
class GoogleStaticMapServiceTest {

    @Autowired
    private GoogleStaticMapService googleStaticMapService;

    @Test
    @DisplayName("구글 스태틱 맵 가져오기")
    void googleStaticMapTest() throws IOException {
        //given
        ClassPathResource resource = new ClassPathResource("images/sample-map.png");
        byte[] expected = StreamUtils.copyToByteArray(resource.getInputStream());

        //when
        byte[] actual = googleStaticMapService.getStaticMap(
            new MapRequest(
                List.of(
                    new Position(1L, 37.4740359, 127.1027386),
                    new Position(2L, 37.4750659, 127.1034386),
                    new Position(3L, 37.4760959, 127.1047356),
                    new Position(4L, 37.4770459, 127.1057386),
                    new Position(5L, 37.4780259, 127.1067286)
                )
            )
        );

        //then
        assertThat(actual).isEqualTo(expected);
    }
}
