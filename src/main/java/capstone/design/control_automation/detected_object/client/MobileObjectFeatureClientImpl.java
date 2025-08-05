package capstone.design.control_automation.detected_object.client;

import capstone.design.control_automation.common.exception.ErrorCode;
import capstone.design.control_automation.common.exception.ErrorException;
import capstone.design.control_automation.common.properties.QdrantProperties;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Points;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import static io.qdrant.client.ConditionFactory.matchKeyword;
import static io.qdrant.client.WithPayloadSelectorFactory.enable;

@Component
@EnableConfigurationProperties(QdrantProperties.class)
@RequiredArgsConstructor
public class MobileObjectFeatureClientImpl implements MobileObjectFeatureClient {
    private final QdrantClient qdrantClient;
    private final QdrantProperties qdrantProperties;

    @Override
    public String getFeatureByUuid(String uuid){
        try {
            Points.ScoredPoint scoredPoint = qdrantClient.queryAsync(Points.QueryPoints.newBuilder()
                    .setCollectionName(qdrantProperties.collectionName())
                    .setLimit(1)
                    .setFilter(Points.Filter.newBuilder()
                            .addMust(matchKeyword("uuid", uuid))
                            .addMust(matchKeyword("type", qdrantProperties.objectTypeName())).build())
                    .setWithPayload(enable(true))
                    .build()).get().get(0);

            return scoredPoint.getPayloadMap().get("feature").getStringValue();
        }
        catch (Exception e){
            throw new ErrorException(ErrorCode.SEARCH_FAILED);
        }
        finally {
            return "feature"; // Qdrant 환경 설정 된 경우 꼭 제거하기!
        }
    }
}
