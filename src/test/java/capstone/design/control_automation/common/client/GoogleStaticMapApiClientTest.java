package capstone.design.control_automation.common.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import capstone.design.control_automation.common.exception.ExternalApiErrorCode;
import capstone.design.control_automation.common.exception.ExternalApiException;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import java.io.IOException;
import java.net.URI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

@AutoConfigureWireMock(port = 0)
@SpringBootTest
class GoogleStaticMapApiClientTest {

    @Autowired
    private GoogleStaticMapApiClient googleStaticMapApiClient;

    @Test
    @DisplayName("구글_스태틱_맵_가져오기_성공하는_경우")
    void 구글_스태틱_맵_가져오기_성공하는_경우() throws IOException {
        //given
        ClassPathResource resource = new ClassPathResource("images/sample-map.png");
        byte[] expected = StreamUtils.copyToByteArray(resource.getInputStream());

        //when
        byte[] actual = googleStaticMapApiClient.exchange(URI.create("/maps/api/staticmap"));
        //then

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("구글_스태틱_맵_가져오기_200_with_warning")
    void 구글_스태틱_맵_가져오기_200_with_warning() throws IOException {
        //given
        Logger logger = (Logger) LoggerFactory.getLogger(GoogleStaticMapApiClient.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        ClassPathResource resource = new ClassPathResource("images/sample-map.png");
        byte[] expected = StreamUtils.copyToByteArray(resource.getInputStream());

        //when
        byte[] actual = googleStaticMapApiClient.exchange(URI.create("/maps/api/staticmap/with-warnings"));

        //then
        assertThat(actual).isEqualTo(expected);
        boolean containsWarning = listAppender.list.stream()
            .anyMatch(e -> e.getFormattedMessage().contains("Google Static Map warning"));
        assertThat(containsWarning).isTrue();
    }

    @Test
    @DisplayName("구글_스태틱_맵_가져오기_400_code_return")
    void 구글_스태틱_맵_가져오기_400_code_return() {
        ExternalApiException exception = assertThrows(ExternalApiException.class,
            () -> googleStaticMapApiClient.exchange(URI.create("/maps/api/staticmap/bad-request")));

        assertThat(exception.getErrorCode()).isEqualTo(ExternalApiErrorCode.GOOGLE_STATIC_MAP_INVALID_PARAM);
    }

    @Test
    @DisplayName("구글_스태틱_맵_가져오기_403_code_return")
    void 구글_스태틱_맵_가져오기_403_code_return() {
        ExternalApiException exception = assertThrows(ExternalApiException.class,
            () -> googleStaticMapApiClient.exchange(URI.create("/maps/api/staticmap/forbidden")));

        assertThat(exception.getErrorCode()).isEqualTo(ExternalApiErrorCode.GOOGLE_STATIC_MAP_INVALID_KEY);
    }

    @Test
    @DisplayName("구글_스태틱_맵_가져오기_500_code_return")
    void 구글_스태틱_맵_가져오기_500_code_return() {
        ExternalApiException exception = assertThrows(ExternalApiException.class,
            () -> googleStaticMapApiClient.exchange(URI.create("/maps/api/staticmap/internal-server-error")));

        assertThat(exception.getErrorCode()).isEqualTo(ExternalApiErrorCode.EXTERNAL_SERVER_ERROR);
    }
}
