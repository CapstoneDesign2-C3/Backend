package capstone.design.control_automation.common.onHold;

import java.util.List;

public interface NaturalLangSearchModel {

    List<Long> findMobileObjectByFeature(String feature);

    List<Long> findFixedObjectBySearchInput(String searchInput);
}
