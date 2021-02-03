package com.seeyon.apps.ext.zxzyk.manager;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface xzykManager {
    List<Map<String, Object>> queryAll(String sql) throws SQLException;

}
