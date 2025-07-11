package capstone.design.control_automation.common.onHold;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class NaturalLangSearchModelImpl implements NaturalLangSearchModel {

    @Override
    public List<Long> findMobileObjectByFeature(String feature) {
        return List.of();
    }

    @Override
    public List<Long> findFixedObjectBySearchInput(String searchInput) {
        return List.of();
    }
}
