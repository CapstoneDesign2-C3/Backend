package capstone.design.control_automation.event.client;

import capstone.design.control_automation.common.exception.ErrorCode;
import capstone.design.control_automation.common.exception.ErrorException;
import capstone.design.control_automation.common.properties.QdrantProperties;
import io.qdrant.client.QdrantClient;

import static io.qdrant.client.ConditionFactory.matchKeyword;
import static io.qdrant.client.grpc.Points.Filter;
import static io.qdrant.client.WithPayloadSelectorFactory.enable;

import io.qdrant.client.grpc.Points.ScoredPoint;
import io.qdrant.client.grpc.Points.QueryPoints;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(QdrantProperties.class)
@RequiredArgsConstructor
public class EventSummaryClientImpl implements EventSummaryClient {
    private final QdrantClient qdrantClient;
    private final QdrantProperties qdrantProperties;

    @Override
    public String getSummaryByUuid(String uuid){
        try{
            ScoredPoint scoredPoint = qdrantClient.queryAsync(QueryPoints.newBuilder()
                            .setCollectionName(qdrantProperties.collectionName())
                            .setLimit(1)
                            .setFilter(Filter.newBuilder()
                                    .addMust(matchKeyword("uuid",uuid))
                                    .addMust(matchKeyword("type", qdrantProperties.eventTypeName())).build())
                            .setWithPayload(enable(true))
                        .build()).get().get(0);

            return scoredPoint.getPayloadMap().get("summary").getStringValue();
        }
        catch (Exception e){
            throw new ErrorException(ErrorCode.SEARCH_FAILED);
        }
    }
}
