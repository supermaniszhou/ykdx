package com.seeyon.apps.ext.zxzyk.dao;

import com.seeyon.apps.ext.zxzyk.util.SyncConnectionUtil;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XzykDaoImpl implements XzykDao {
    @Override
    public List<Map<String, Object>> queryAll(String sql) throws SQLException {
        List<Map<String, Object>> mapList = new ArrayList<>();

        ResultSet rs = SyncConnectionUtil.getResultSet(sql);
        Map<String, Object> map = null;

        while (rs.next()) {
            ResultSetMetaData metaData = rs.getMetaData();
            int count = metaData.getColumnCount();
            map = new HashMap<>();
            for (int i = 1; i <= count; i++) {
                String columnName = metaData.getColumnName(i);
                String value = rs.getString(i);
                if (null == value && "".equals(value)) {
                    map.put("columnName", "");
                } else {
                    map.put("columnName", value);
                }
                System.out.println(columnName + "==" + value);
            }
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public void insertAll(String sql, List<Map<String, Object>> mapList) {

    }

    @Override
    public void clearData(String sql) {

    }
}
