package com.seeyon.apps.ext.zxzyk.dao;

import com.seeyon.apps.ext.zxzyk.util.SyncConnectionUtil;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class xzykDaoImpl implements xzykDao {
    @Override
    public List<Map<String, Object>> queryAll(String sql) throws SQLException {
        ResultSet rs = SyncConnectionUtil.getResultSet(sql);
        ResultSetMetaData metaData = rs.getMetaData();
        int count = metaData.getColumnCount();
        System.out.println(count);
        return null;
    }

    @Override
    public void insertAll(String sql, List<Map<String, Object>> mapList) {

    }

    @Override
    public void clearData(String sql) {

    }
}
