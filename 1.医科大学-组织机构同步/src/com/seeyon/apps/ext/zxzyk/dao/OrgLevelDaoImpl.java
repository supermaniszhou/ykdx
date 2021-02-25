package com.seeyon.apps.ext.zxzyk.dao;

//import com.alibaba.fastjson.JSONArray;

import com.seeyon.apps.ext.logRecord.dao.LogRecordDao;
import com.seeyon.apps.ext.logRecord.po.LogRecord;
import com.seeyon.apps.ext.zxzyk.po.OrgLevel;
import com.seeyon.apps.ext.zxzyk.util.ReadConfigTools;
import com.seeyon.apps.ext.zxzyk.util.SyncConnectionUtil;
import com.seeyon.client.CTPRestClient;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.util.JDBCAgent;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class OrgLevelDaoImpl implements OrgLevelDao {

    private ReadConfigTools configTools = new ReadConfigTools();

    private LogRecordDao logRecordDao = (LogRecordDao) AppContext.getBean("logRecordDao");


    @Override
    public List<OrgLevel> queryOrgLevel() throws SQLException {
        List<OrgLevel> levelList = new ArrayList<>();
        String sql = "select vl.id,vl.name,vl.code,vl.is_enable from V_ORG_LEVEL vl where not exists (select * from M_ORG_LEVEL ml where ml.code=vl.code)";
        Connection connection = JDBCAgent.getRawConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            OrgLevel orgLevel = null;
            while (rs.next()) {
                orgLevel = new OrgLevel();
                orgLevel.setOrgAccountId(configTools.getOrgAccountId());
                orgLevel.setLevelcode(rs.getString("code"));
                orgLevel.setLevelname(rs.getString("name"));
                levelList.add(orgLevel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SyncConnectionUtil.closeResultSet(rs);
            SyncConnectionUtil.closePrepareStatement(ps);
            SyncConnectionUtil.closeConnection(connection);
        }

        return levelList;
    }

    @Override
    public void insertOrgLevel(List<OrgLevel> list) {
        CTPRestClient client = SyncConnectionUtil.getOaRest();
        Connection connection = null;
        PreparedStatement ps = null;
        String insertSql = "insert into M_ORG_LEVEL(id,name,code,description) values (?,?,?,?)";
        try {
            connection = JDBCAgent.getRawConnection();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(insertSql);
            if (null != list && list.size() > 0) {
                Map map = null;
                for (OrgLevel orgLevel : list) {
                    map = new HashMap();
                    map.put("orgAccountId", configTools.getOrgAccountId());
                    map.put("code", orgLevel.getLevelcode());
                    map.put("name", orgLevel.getLevelname());
                    JSONObject json = client.post("/orgLevel", map, JSONObject.class);
                    if (null != json) {
                        if (json.getBoolean("success")) {
                            JSONObject ent = json.getJSONArray("successMsgs").getJSONObject(0).getJSONObject("ent");
                            String deptid = ent.getString("id");
                            ps.setString(1, deptid);
                            ps.setString(2, orgLevel.getLevelname());
                            ps.setString(3, orgLevel.getLevelcode());
                            ps.setString(4, "");
                            ps.addBatch();
                        } else {
                            net.sf.json.JSONArray obj = (net.sf.json.JSONArray) json.get("errorMsgs");
                            Map<String, Object> m = (Map<String, Object>) obj.get(0);
                            Map<String, Object> ment = (Map<String, Object>) m.get("ent");
                            //记录更新了哪些
                            LogRecord logRecord = new LogRecord();
                            logRecord.setId(System.currentTimeMillis());
                            logRecord.setUpdateUser("自动同步");
                            logRecord.setUpdateDate(new Date());
                            logRecord.setOpType("插入");
                            logRecord.setOpModule("职务级别");
                            logRecord.setOpContent(ment.get("name") + "（" + ment.get("code") + "）:" + (String) m.get("code"));
                            logRecord.setOpResult("失败！");
                            logRecordDao.saveLogRecord(logRecord);
                            String code = (String) m.get("code");
                            if (code.equals("LEVEL_REPEAT_NAME")) {
                                String deptid = ment.get("id") + "";
                                ps.setString(1, deptid);
                                ps.setString(2, orgLevel.getLevelname());
                                ps.setString(3, orgLevel.getLevelcode());
                                ps.setString(4, "");
                                ps.addBatch();
                            }
                        }
                    }
                }
            }
            ps.executeBatch();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SyncConnectionUtil.closePrepareStatement(ps);
            SyncConnectionUtil.closeConnection(connection);
        }
    }

    @Override
    public List<OrgLevel> queryChangerLevel() throws SQLException {
        List<OrgLevel> levelList = new ArrayList<>();
        String sql = "select VL.CODE,VL.name,ML.ID from V_ORG_LEVEL vl,M_ORG_LEVEL ml where VL.code =ML.code and VL.name <> ML.NAME";
        ResultSet rs = null;
        Connection connection = JDBCAgent.getRawConnection();

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            OrgLevel orgLevel = null;
            while (rs.next()) {
                orgLevel = new OrgLevel();
                orgLevel.setOrgAccountId(configTools.getOrgAccountId());
                orgLevel.setLevelcode(rs.getString("code"));
                orgLevel.setLevelname(rs.getString("name"));
                orgLevel.setLevelid(rs.getString("id"));
                levelList.add(orgLevel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SyncConnectionUtil.closeResultSet(rs);
            SyncConnectionUtil.closePrepareStatement(ps);
            SyncConnectionUtil.closeResultSet(rs);
        }

        return levelList;
    }

    @Override
    public void updateOrgLevel(List<OrgLevel> list) {
        CTPRestClient client = SyncConnectionUtil.getOaRest();
        String sql = "update M_ORG_LEVEL set ";
        try {
            if (null != list && list.size() > 0) {
                Map map = null;
                for (OrgLevel orgLevel : list) {
                    map = new HashMap();
                    map.put("id", orgLevel.getLevelid());
                    map.put("name", orgLevel.getLevelname());

                    JSONObject json = client.put("/orgLevel", map, JSONObject.class);
                    if (null != json) {
                        if (json.getBoolean("success")) {
                            sql = sql.concat("name='" + orgLevel.getLevelname() + "' where id=" + orgLevel.getLevelid());
                        } else {
                            JSONArray obj = (JSONArray) json.get("errorMsgInfos");
                            Map<String, Object> m = (Map<String, Object>) obj.get(0);
                            //记录更新了哪些
                            LogRecord logRecord = new LogRecord();
                            logRecord.setId(System.currentTimeMillis());
                            logRecord.setUpdateUser("自动同步");
                            logRecord.setUpdateDate(new Date());
                            logRecord.setOpType("修改");
                            logRecord.setOpModule("职务级别");
                            logRecord.setOpContent((String) m.get("msgInfo"));
                            logRecord.setOpResult("失败！");
                            logRecordDao.saveLogRecord(logRecord);
                        }
                    }
                    SyncConnectionUtil.insertResult(sql);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Override
    public List<OrgLevel> queryNotExistLevel() throws SQLException {
        List<OrgLevel> levelList = new ArrayList<>();
        String sql = "select * from M_ORG_LEVEL ml where not EXISTS (select * from V_ORG_LEVEL vl where VL.CODE =ML.code)";
        ResultSet rs = null;
        Connection connection = JDBCAgent.getRawConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            OrgLevel orgLevel = null;
            while (rs.next()) {
                orgLevel = new OrgLevel();
                orgLevel.setOrgAccountId(configTools.getOrgAccountId());
                orgLevel.setLevelcode(rs.getString("code"));
                orgLevel.setLevelname(rs.getString("name"));
                orgLevel.setLevelid(rs.getString("id"));
                levelList.add(orgLevel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SyncConnectionUtil.closeResultSet(rs);
            SyncConnectionUtil.closePrepareStatement(ps);
            SyncConnectionUtil.closeResultSet(rs);
        }

        return levelList;
    }

    @Override
    public void deleteOrgLevel(List<OrgLevel> list) {
        CTPRestClient client = SyncConnectionUtil.getOaRest();
        String sql = "delete from M_ORG_LEVEL where ";
        try {
            if (null != list && list.size() > 0) {
                Map map = null;
                for (OrgLevel orgLevel : list) {
                    map = new HashMap();
                    map.put("id", orgLevel.getLevelid());

                    JSONObject json = client.delete("/orgLevel/" + orgLevel.getLevelid(), map, JSONObject.class);
                    if (null != json) {
                        if (json.getBoolean("success")) {
                            sql = sql.concat("id='" + orgLevel.getLevelid() + "'");
                            SyncConnectionUtil.insertResult(sql);

                        } else {
                            JSONArray obj = (JSONArray) json.get("errorMsgInfos");
                            Map<String, Object> m = (Map<String, Object>) obj.get(0);
                            //记录更新了哪些
                            LogRecord logRecord = new LogRecord();
                            logRecord.setId(System.currentTimeMillis());
                            logRecord.setUpdateUser("自动同步");
                            logRecord.setUpdateDate(new Date());
                            logRecord.setOpType("删除");
                            logRecord.setOpModule("职务级别");
                            logRecord.setOpContent((String) m.get("msgInfo"));
                            logRecord.setOpResult("失败！");
                            logRecordDao.saveLogRecord(logRecord);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}
