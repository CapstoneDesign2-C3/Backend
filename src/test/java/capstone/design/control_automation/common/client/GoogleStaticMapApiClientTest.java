package capstone.design.control_automation.common.client;

import capstone.design.control_automation.detection.repository.dto.DetectionQueryResult.Position;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:local.env")
class GoogleStaticMapApiClientTest {

    @Autowired
    private GoogleStaticMapApiClient googleStaticMapApiClient;

    @Test
    @DisplayName("구글 스태틱 맵 가져오기")
    void googleStaticMapTest() {
        //given
        byte[] bytes = googleStaticMapApiClient.requestStaticMap(new MapRequest(
            List.of(new Position(1L, 33.33, 33.33))
        ));
        //when
        System.out.println(Arrays.toString(bytes));
        //then
    }
}
