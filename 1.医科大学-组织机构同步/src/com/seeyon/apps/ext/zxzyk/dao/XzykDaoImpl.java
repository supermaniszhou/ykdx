package com.seeyon.apps.ext.zxzyk.dao;

import com.seeyon.apps.ext.zxzyk.util.SyncConnectionUtil;
import com.seeyon.ctp.util.JDBCAgent;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
                    map.put(columnName, "");
                } else {
                    map.put(columnName, value);
                }
            }
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public void insertAll(String tableName, List<Map<String, Object>> mapList) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into " + tableName + "(");
        Map<String, Object> col = mapList.get(0);
        int count = 0;
        StringBuilder valCol = new StringBuilder();
        valCol.append("(");
        for (Map.Entry<String, Object> entry : col.entrySet()) {
            sb.append(entry.getKey());
            valCol.append("?");
            if (count != (col.size() - 1)) {
                sb.append(",");
                valCol.append(",");
            }
            count++;
        }
        sb.append(") values ");
        sb.append(valCol.toString() + ")");
        System.out.println(sb.toString());
        try (Connection connection = JDBCAgent.getRawConnection();
             PreparedStatement ps = connection.prepareStatement(sb.toString());) {
            connection.setAutoCommit(false);
            Stream.iterate(0, i -> i + 1).limit(mapList.size()).forEach(i -> {
                Map<String, Object> m = mapList.get(i);
                int index = 1;
                for (Map.Entry<String, Object> entry : m.entrySet()) {
                    try {
                        ps.setString(index, entry.getValue() + "");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    index++;
                }
                try {
                    ps.addBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearData(String sql) {

    }
}
