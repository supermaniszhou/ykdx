package com.seeyon.apps.ext.zxzyk.dao;

import com.seeyon.apps.ext.zxzyk.util.SyncConnectionUtil;
import com.seeyon.ctp.util.JDBCAgent;

import java.sql.*;
import java.util.*;
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
            map = new LinkedHashMap<>();
            for (int i = 1; i <= count; i++) {
                String columnName = metaData.getColumnName(i);
                String value = null == rs.getString(i) ? "" : rs.getString(i);
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
    public void insertAll(String tableName, List<Map<String, Object>> mapList) throws SQLException {
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
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JDBCAgent.getRawConnection();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(sb.toString());
            PreparedStatement finalPs = ps;
            Stream.iterate(0, i -> i + 1).limit(mapList.size()).forEach(i -> {
                Map<String, Object> m = mapList.get(i);
                int index = 1;
                for (Map.Entry<String, Object> entry : m.entrySet()) {
                    try {
                        finalPs.setString(index, entry.getValue() + "");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    index++;
                }
                try {
                    finalPs.addBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            ps.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (null != ps) {
                ps.close();
            }
            if (null != connection) {
                connection.close();
            }
        }
    }

    @Override
    public void clearData(String sql) {
        try (Connection connection = JDBCAgent.getRawConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
