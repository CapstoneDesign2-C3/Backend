package capstone.design.control_automation.report.util.hwp.fragments.table;

import capstone.design.control_automation.report.util.hwp.dto.TableColumn;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class HwpTableDataExtractor {

    private static final Map<Class<?>, List<Field>> fieldCache = new ConcurrentHashMap<>();

    public <T> List<List<String>> convertToTableData(List<T> dataToWrite, boolean appendHeader) throws IllegalAccessException {
        List<Field> fields = getFieldsToMakeTable(dataToWrite.get(0));
        return extractTableData(dataToWrite, fields, appendHeader);
    }

    private <T> List<Field> getFieldsToMakeTable(T tableData) {
        if (!fieldCache.containsKey(tableData.getClass())) {
            Class<?> tableDataClass = tableData.getClass();
            Field[] fields = tableDataClass.getDeclaredFields();
            List<Field> sorted = Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(TableColumn.class))
                .peek(field -> field.setAccessible(true))
                .sorted(Comparator.comparingInt(field -> field.getAnnotation(TableColumn.class).order()))
                .toList();
            fieldCache.put(tableData.getClass(), sorted);
            return sorted;
        }
        return fieldCache.get(tableData.getClass());
    }

    abstract <T> List<List<String>> extractTableData(List<T> dataToWrite, List<Field> fields, boolean appendHeader)
        throws IllegalAccessException;

}
