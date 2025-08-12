package capstone.design.control_automation.qdrant;

import capstone.design.control_automation.common.properties.QdrantProperties;
import capstone.design.control_automation.detected_object.client.MobileObjectFeatureClient;
import capstone.design.control_automation.event.client.EventSummaryClient;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import io.qdrant.client.grpc.Collections.*;
import io.qdrant.client.grpc.Points.UpdateResult;
import io.qdrant.client.grpc.Points.PointStruct;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.qdrant.QdrantContainer;

import static org.assertj.core.api.Assertions.assertThat;
import static io.qdrant.client.PointIdFactory.id;
import static io.qdrant.client.ValueFactory.value;
import static io.qdrant.client.VectorsFactory.vectors;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@SpringBootTest
@EnableConfigurationProperties(QdrantProperties.class)
@Testcontainers
public class QdrantClientTest {
    @Container
    private static final QdrantContainer QDRANT_CONTAINER = new QdrantContainer("qdrant/qdrant");

    @Autowired
    private EventSummaryClient eventSummaryClient;
    @Autowired
    private MobileObjectFeatureClient mobileObjectFeatureClient;
    @Autowired
    private QdrantClient qdrantClient;
    @Autowired
    private QdrantProperties qdrantProperties;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public QdrantClient qdrantClient() {
            ManagedChannel channel = Grpc.newChannelBuilder(QDRANT_CONTAINER.getGrpcHostAddress(), InsecureChannelCredentials.create())
                    .build();
            QdrantGrpcClient grpcClient = QdrantGrpcClient.newBuilder(channel).build();
            return new QdrantClient(grpcClient);
        }
    }

    @BeforeEach
    public void setup() throws ExecutionException, InterruptedException {
        qdrantClient.createCollectionAsync(qdrantProperties.collectionName(),
                VectorParams.newBuilder()
                        .setDistance(Distance.Cosine)
                        .setSize(4)
                        .build())
                .get();

        List<PointStruct> points =
                List.of(
                        PointStruct.newBuilder()
                                .setId(id(1))
                                .setVectors(vectors(0.32f, 0.52f, 0.21f, 0.52f))
                                .putAllPayload(
                                        Map.of(
                                                "uuid", value("12345"),
                                                "type", value(qdrantProperties.eventTypeName()),
                                                "summary", value("correct_summary")
                                        ))
                                .build(),
                        PointStruct.newBuilder()
                                .setId(id(2))
                                .setVectors(vectors(0.42f, 0.52f, 0.67f, 0.632f))
                                .putAllPayload(
                                        Map.of(
                                                "uuid", value("54321"),
                                                "type", value(qdrantProperties.eventTypeName()),
                                                "summary", value("incorrect_summary")
                                        ))
                                .build(),
                        PointStruct.newBuilder()
                                .setId(id(3))
                                .setVectors(vectors(0.42f, 0.52f, 0.67f, 0.632f))
                                .putAllPayload(
                                        Map.of(
                                                "uuid", value("54321"),
                                                "type", value(qdrantProperties.objectTypeName()),
                                                "feature", value("incorrect_feature")
                                        ))
                                .build(),
                        PointStruct.newBuilder()
                                .setId(id(4))
                                .setVectors(vectors(0.32f, 0.52f, 0.21f, 0.52f))
                                .putAllPayload(
                                        Map.of(
                                                "uuid", value("12345"),
                                                "type", value(qdrantProperties.objectTypeName()),
                                                "feature", value("correct_feature")
                                        ))
                                .build()
                        )
                ;

        UpdateResult updateResult = qdrantClient.upsertAsync("event", points).get();
    }

    @AfterEach
    public void teardown() throws Exception{
        List<String> collectionNames = qdrantClient.listCollectionsAsync().get();
        for (String collectionName : collectionNames) {
            qdrantClient.deleteCollectionAsync(collectionName).get();
        }
        qdrantClient.close();
    }

    @Test
    @DisplayName("getSummaryByUuid 테스트")
    public void getSummaryByUuidTest() {
        String expected = eventSummaryClient.getSummaryByUuid("12345");

        assertThat(expected).isEqualTo("summary");
    }

    @Test
    @DisplayName("getFeatureByUuid 테스트")
    public void getFeatureByUuidTest() {
        String expected = mobileObjectFeatureClient.getFeatureByUuid("12345");

        assertThat(expected).isEqualTo("feature");
    }
}
