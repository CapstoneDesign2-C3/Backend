package capstone.design.control_automation.report.util.hwp.fragments.table;

import capstone.design.control_automation.report.util.hwp.dto.TableColumn;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HorizontalTableDataExtractor extends HwpTableDataExtractor {

    @Override
    public <T> List<List<String>> extractTableData(List<T> dataToWrite, List<Field> fields, boolean appendHeader)
        throws IllegalAccessException {
        List<List<String>> tableData = new ArrayList<>();

        tableData.add(new ArrayList<>());
        List<String> header = tableData.get(0);
        if (appendHeader) {
            if (dataToWrite.size() == 1) {
                header.add("분류");
            }
            else {
                header.add("번호");
            }
        }

        for (Field f : fields) {
            header.add(f.getAnnotation(TableColumn.class).name());
        }


        for (int row = 0; row < dataToWrite.size(); row++) {
            tableData.add(new ArrayList<>());
            List<String> rowData = tableData.get(row + 1);

            T curRowData = dataToWrite.get(row);
            if (appendHeader && dataToWrite.size() != 1)
                rowData.add(String.valueOf(row + 1));
            if (appendHeader && dataToWrite.size() == 1)
                rowData.add("값");
            for (Field f : fields) {
                rowData.add(f.get(curRowData).toString());
            }
        }

        return tableData;
    }
}
