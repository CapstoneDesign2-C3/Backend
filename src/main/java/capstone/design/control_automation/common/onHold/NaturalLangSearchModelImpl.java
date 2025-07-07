package capstone.design.control_automation.common.onHold;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class NaturalLangSearchModelImpl implements NaturalLangSearchModel {

    @Override
    public List<Long> findFixedObjectByFeature(String feature) {
        return List.of();
    }
}
