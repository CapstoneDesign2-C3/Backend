package capstone.design.control_automation.report.util.hwp.fragments.table;

import capstone.design.control_automation.common.exception.ErrorException;
import capstone.design.control_automation.report.util.hwp.dto.TableColumn;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class DivisionTableDataExtractor extends HwpTableDataExtractor {

    @Override
    public <T> List<List<String>> extractTableData(List<T> dataToWrite, List<Field> fields, boolean appendHeader)
        throws IllegalAccessException {
        if (dataToWrite.size() != 1 || fields.size() % 2 != 0) {
            throw new ErrorException("Division Table로 쓸 수 없습니다.");
        }

        List<List<String>> tableData = new ArrayList<>();

        if (appendHeader) {
            tableData.add(new ArrayList<>());
            List<String> header = tableData.get(0);
            header.add("분류");
            header.add("값");
            header.add("분류");
            header.add("값");
        }

        for (int i = 0; i < fields.size(); i += 2) {
            tableData.add(new ArrayList<>());
            List<String> row = tableData.get(i / 2);
            if (appendHeader) {
                row = tableData.get(i / 2 + 1);
            }
            row.add(fields.get(i).getAnnotation(TableColumn.class).name());
            if (isNotEmptyInField(fields.get(i), dataToWrite.get(0))) {
                row.add(fields.get(i).get(dataToWrite.get(0)).toString());
            } else {
                row.add("-");
            }
            row.add(fields.get(i + 1).getAnnotation(TableColumn.class).name());
            if (isNotEmptyInField(fields.get(i+1), dataToWrite.get(0))) {
                row.add(fields.get(i + 1).get(dataToWrite.get(0)).toString());
            } else {
                row.add("-");
            }
        }

        return tableData;
    }

    private <T> Boolean isNotEmptyInField(Field field, T data) throws IllegalAccessException {
        return !Objects.isNull(field.get(data));
    }
}
