package com.seeyon.apps.ext.zxzyk.util;

import com.seeyon.client.CTPRestClient;
import com.seeyon.client.CTPServiceClientManager;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.util.JDBCAgent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019-7-29.
 */
public class SyncConnectionUtil {
    private static final Log log = LogFactory.getLog(SyncConnectionUtil.class);


    public SyncConnectionUtil() {
    }

    public static List<Map<String, Object>> doQuery(String sql) {
        try (JDBCAgent jdbcAgent = new JDBCAgent(JDBCAgent.getRawConnection())) {
            jdbcAgent.execute(sql);
            return jdbcAgent.resultSetToList();
        } catch (SQLException e) {
            log.error("zhou:代码SyncConnectionUtil 31 行出错了：" + e.getMessage());
        } catch (BusinessException b) {
            log.error("zhou:代码SyncConnectionUtil 33 行出错了：" + b.getMessage());
        }
        return null;
    }

    /**
     * 删除表数据
     *
     * @param sql
     */
    public static void delete(String sql) {
        try (Connection con = JDBCAgent.getRawConnection();
             PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("zhou:代码SyncConnectionUtil 方法delete 48 行出错了：" + e.getMessage());
        }
    }

    /**
     * 获取rest
     *
     * @return
     */
    public static CTPRestClient getOaRest() {
        ReadConfigTools configTools = new ReadConfigTools();
        String restUrl = configTools.getString("xzyk.restInfo.url");
        String restUser = configTools.getString("xzyk.restInfo.username");
        String restPwd = configTools.getString("xzyk.restInfo.password");
        CTPServiceClientManager clientManager = CTPServiceClientManager.getInstance(restUrl);
        CTPRestClient restClient = clientManager.getRestClient();
        boolean ltFlag = restClient.authenticate(restUser, restPwd);
        return ltFlag ? restClient : null;
    }

    /**
     * 获取连接
     *
     * @return
     */
    public static Connection getMidConnection() {
        ReadConfigTools configTools = new ReadConfigTools();
        String driverName = configTools.getString("xzyk.midDataLink.driver");
        String url = configTools.getString("xzyk.midDataLink.url");
        String username = configTools.getString("xzyk.midDataLink.username");
        String password = configTools.getString("xzyk.midDataLink.password");
        Connection connection = null;
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static int insertResult(String sql) throws SQLException {
        Connection connection = JDBCAgent.getRawConnection();
        Statement statement = null;
        int result = 0;
        try {
            statement = connection.createStatement();
            result = statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return result;
    }

    /**
     * @param connection
     */
    public static void closeConnection(Connection connection) {
        if (null != connection) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closePrepareStatement(PreparedStatement ps) {
        if (null != ps) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResultSet(ResultSet ps) {
        if (null != ps) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeStatement(Statement ps) {
        if (null != ps) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static List<Map<String, Object>> getResultSet(String sql) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> map = null;
        ResultSet rs = null;
        try (Connection connection = getMidConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            rs = ps.executeQuery();
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != rs) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
