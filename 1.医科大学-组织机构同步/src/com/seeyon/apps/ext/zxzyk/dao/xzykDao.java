package com.seeyon.apps.ext.zxzyk.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface xzykDao {

    List<Map<String, Object>> queryAll(String sql) throws SQLException;

    void insertAll(String sql, List<Map<String, Object>> mapList);

    void clearData(String sql);
}
