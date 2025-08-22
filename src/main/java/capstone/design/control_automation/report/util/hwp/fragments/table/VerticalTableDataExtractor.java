package capstone.design.control_automation.report.util.hwp.fragments.table;

import capstone.design.control_automation.report.util.hwp.dto.TableColumn;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class VerticalTableDataExtractor extends HwpTableDataExtractor {

    @Override
    public <T> List<List<String>> extractTableData(List<T> dataToWrite, List<Field> fields, boolean appendHeader)
        throws IllegalAccessException {
        List<List<String>> tableData = new ArrayList<>();

        if (appendHeader) {
            tableData.add(new ArrayList<>());
            List<String> header = tableData.get(0);
            if (dataToWrite.size() == 1) {
                header.add("분류");
                header.add("값");
            }
            else {
                header.add("번호");
                for (int i = 0; i < dataToWrite.size(); i++) {
                    header.add(String.valueOf(i + 1));
                }
            }
        }

        for (int row = 0; row < fields.size(); row++) {
            tableData.add(new ArrayList<>());
            List<String> rowData = tableData.get(row);
            if (appendHeader)
                rowData = tableData.get(row + 1);
            Field field = fields.get(row);
            rowData.add(field.getAnnotation(TableColumn.class).name());
            for (T data : dataToWrite) {
                if (isNotEmptyInField(field, data)) {
                    rowData.add(field.get(data).toString());
                }
                else {
                    rowData.add("-");
                }
            }
        }

        return tableData;

    }

    private <T> Boolean isNotEmptyInField(Field field, T data) throws IllegalAccessException {
        return !Objects.isNull(field.get(data));
    }
}
