package capstone.design.control_automation.report.util.hwp;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class StyleIdContext {

    private final Map<String, Integer> paraShapeIdMap = new HashMap<>();
    private final Map<String, Integer> charShapeIdMap = new HashMap<>();

    public void putParaShapeId(String key, Integer value) {
        paraShapeIdMap.put(key, value);
    }

    public void putCharShapeId(String key, Integer value) {
        charShapeIdMap.put(key, value);
    }

    public Integer getParaShapeId(String key) {
        return paraShapeIdMap.get(key);
    }

    public Integer getCharShapeId(String key) {
        return charShapeIdMap.get(key);
    }
}
