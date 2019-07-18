package pers.cr.generator.util.jdbc;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultData {

    public static List<Map<String, Object>> getDataByResult(ResultSet resultSet) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            Integer columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<>();
                for (Integer i = 0; i < columnCount; i++) {
                    map.put(resultSet.getMetaData().getColumnName(i + 1), resultSet.getObject(i + 1));
                }
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
