package com.seeyon.apps.ext.zxzyk.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface XzykDao {

    public static boolean debug=true;

    List<Map<String, Object>> queryAll(String sql) throws SQLException;

    void insertAll(String sql, List<Map<String, Object>> mapList) throws SQLException;

    void clearData(String sql);
}
