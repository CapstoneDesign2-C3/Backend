package capstone.design.control_automation.detected_object.client;

import capstone.design.control_automation.common.exception.ErrorCode;
import capstone.design.control_automation.common.exception.ErrorException;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Points;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static io.qdrant.client.ConditionFactory.matchKeyword;
import static io.qdrant.client.WithPayloadSelectorFactory.enable;

@Component
@RequiredArgsConstructor
public class MobileObjectFeatureClientImpl implements MobileObjectFeatureClient {
    private final QdrantClient qdrantClient;

    @Override
    public String getFeatureByUuid(String uuid){
        try {
            Points.ScoredPoint scoredPoint = qdrantClient.queryAsync(Points.QueryPoints.newBuilder()
                    .setCollectionName("event") //TODO 실제 collection 이름으로 변경
                    .setLimit(1)
                    .setFilter(Points.Filter.newBuilder()
                            .addMust(matchKeyword("uuid", uuid))
                            .addMust(matchKeyword("type", "object")).build()) //TODO 실제 키워드로 변경
                    .setWithPayload(enable(true))
                    .build()).get().get(0);

            return scoredPoint.getPayloadMap().get("feature").getStringValue();
        }
        catch (Exception e){
            throw new ErrorException(ErrorCode.SEARCH_FAILED);
        }
    }
}
