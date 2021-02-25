package com.seeyon.apps.ext.zxzyk.dao;

import com.seeyon.apps.ext.logRecord.dao.LogRecordDao;
import com.seeyon.apps.ext.logRecord.po.LogRecord;
import com.seeyon.apps.ext.zxzyk.po.CtpOrgUser;
import com.seeyon.apps.ext.zxzyk.po.OrgMember;
import com.seeyon.apps.ext.zxzyk.util.ReadConfigTools;
import com.seeyon.apps.ext.zxzyk.util.SyncConnectionUtil;
import com.seeyon.client.CTPRestClient;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.util.DBAgent;
import com.seeyon.ctp.util.JDBCAgent;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class OrgMemberDaoImpl implements OrgMemberDao {

    private ReadConfigTools configTools = new ReadConfigTools();

    private LogRecordDao logRecordDao = (LogRecordDao) AppContext.getBean("logRecordDao");


    @Override
    public List<OrgMember> queryNoEnableMember() throws SQLException {
        String sql = "select mm.org_department_id,mm.org_post_id,mm.org_level_id,MM.id,mm.code,vm.is_enable,VM.name from V_ORG_MEMBER vm, M_ORG_MEMBER mm where VM.CODE=MM.code ";
        List<OrgMember> memberList = new ArrayList<>();
        Connection connection = JDBCAgent.getRawConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            OrgMember orgMember = null;
            while (rs.next()) {
                orgMember = new OrgMember();
                orgMember.setMemberid(rs.getString("id"));
                orgMember.setOrgAccountId(configTools.getOrgAccountId());
                orgMember.setMembercode(rs.getString("code"));
                orgMember.setMembername(rs.getString("name"));
                if ("1".equals(rs.getString("is_enable"))) {
                    orgMember.setEnabled(true);
                } else {
                    orgMember.setEnabled(false);
                }
                orgMember.setOrgDepartmentId(rs.getString("org_department_id"));
                orgMember.setOrgPostId(rs.getString("org_post_id"));
                orgMember.setOrgLevelId(rs.getString("org_level_id"));
                memberList.add(orgMember);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SyncConnectionUtil.closeResultSet(rs);
            SyncConnectionUtil.closePrepareStatement(ps);
            SyncConnectionUtil.closeConnection(connection);
        }

        return memberList;
    }

    @Override
    public void updateIsEnableOfMember(List<OrgMember> list) {
        CTPRestClient client = SyncConnectionUtil.getOaRest();
        try {
            if (null != list && list.size() > 0) {
                Map memberMap = null;
                for (OrgMember member : list) {
                    memberMap = new HashMap();

                    memberMap.put("id", member.getMemberid());
                    memberMap.put("enabled", member.getEnabled());

                    JSONObject memberJson = client.get("/orgMember/" + member.getMemberid(), JSONObject.class);
                    if (null != memberJson) {
                        boolean flag = memberJson.getBoolean("enabled");
                        if (flag != member.getEnabled()) {
                            JSONObject json = client.put("/orgMember/" + member.getMemberid() + "/enabled/" + member.getEnabled(), memberMap, JSONObject.class);
                            if (null != json) {
                                if (json.getBoolean("success")) {
                                    String sql = "update m_org_member set ";
                                    if (!member.getEnabled()) {
                                        sql = sql + " IS_ENABLE = '0' ";
                                    } else {
                                        sql = sql + " IS_ENABLE = '1' ";
                                    }

                                    sql = sql + " where id = '" + member.getMemberid() + "' ";

                                    SyncConnectionUtil.insertResult(sql);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OrgMember> queryAddOrgMember() throws SQLException {
        //正式
//        String sql = "select DISTINCT C2.is_enable,c2.code,c2.name,c2.id,c2.POSTID,c2.description,c2.mobile,M_ORG_UNIT.id unitId,M_ORG_LEVEL.id levelId ,c2.\"ou\" ou from " +
//                "(select memb.*,M_ORG_POST.id postid from (" +
//                "select * from V_ORG_MEMBER vm where not EXISTS(select * from M_ORG_MEMBER  where vm.code = M_ORG_MEMBER.code  and vm.name = M_ORG_MEMBER.name)) " +
//                "memb,M_ORG_POST  where memb.org_post_id = M_ORG_POST.code) c2 LEFT JOIN M_ORG_UNIT  on nvl(c2.org_account_id,c2.sup_department_id) = M_ORG_UNIT.code " +
//                "LEFT JOIN M_ORG_LEVEL  on c2.org_level_id=M_ORG_LEVEL.code";
        //测试
        String sqlCe = "select id,name,code,is_enable,is_deleted,mobile,DESCRIPTION, " +
                "(select m.id from M_ORG_UNIT m where m.code=VOM.org_department_id) orgDepartmentId, " +
                "(select MOL.id from M_ORG_LEVEL mol where MOL.code=VOM.ORG_LEVEL_ID) orgLevelId, " +
                "(select MOP.id from M_ORG_POST mop where mop.code=vom.ORG_POST_ID) orgPostId from V_ORG_MEMBER vom where not exists (select mom.* from M_ORG_MEMBER mom where mom.code=vom.code)";
        List<OrgMember> memberList = new ArrayList<>();
        Connection connection = JDBCAgent.getRawConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            if (XzykDao.debug) {
                ps = connection.prepareStatement(sqlCe);
            } else {
//                ps = connection.prepareStatement(sql);
            }
            rs = ps.executeQuery();
            OrgMember orgMember = null;
            while (rs.next()) {
                orgMember = new OrgMember();
                orgMember.setMemberid(rs.getString("id"));
                orgMember.setOrgAccountId(configTools.getOrgAccountId());
                orgMember.setMembercode(rs.getString("code"));
                orgMember.setMembername(rs.getString("name"));
                orgMember.setLoginName(rs.getString("code"));
                orgMember.setOrgDepartmentId(rs.getString("orgdepartmentid"));
                orgMember.setOrgLevelId(rs.getString("orglevelid"));
                orgMember.setOrgPostId(rs.getString("orgpostid"));
                orgMember.setDescription(rs.getString("description"));
                orgMember.setTelNumber(rs.getString("mobile"));
//                orgMember.setOu(rs.getString("ou"));
                if ("1".equals(rs.getString("is_enable"))) {
                    orgMember.setEnabled(true);
                } else {
                    orgMember.setEnabled(false);
                }
                memberList.add(orgMember);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SyncConnectionUtil.closeResultSet(rs);
            SyncConnectionUtil.closePrepareStatement(ps);
            SyncConnectionUtil.closeConnection(connection);
        }

        return memberList;
    }

    @Override
    public void insertOrgMember(List<OrgMember> list) {
        CTPRestClient client = SyncConnectionUtil.getOaRest();
        Connection connection = null;
        PreparedStatement ps = null;
        //正式
        String insertSql = "insert into m_org_member(id,code,name,login_name,org_department_id,org_level_id,description,mobile,org_post_id,ou,is_enable) values (?,?,?,?,?,?,?,?,?,?,?)";

        try {
            connection = JDBCAgent.getRawConnection();
            connection.setAutoCommit(false);
            if (XzykDao.debug) {
                ps = connection.prepareStatement(insertSql);
            }

            if (null != list && list.size() > 0) {
                Map memberMap = null;
                for (OrgMember member : list) {
                    memberMap = new HashMap();
                    memberMap.put("name", member.getMembername());
                    memberMap.put("loginName", member.getLoginName());
                    memberMap.put("orgAccountId", member.getOrgAccountId());
                    memberMap.put("orgLevelId", member.getOrgLevelId());
                    memberMap.put("orgPostId", member.getOrgPostId());
                    memberMap.put("orgDepartmentId", member.getOrgDepartmentId());
                    memberMap.put("code", member.getMembercode());
                    memberMap.put("enabled", member.getEnabled());
                    memberMap.put("telNumber", member.getTelNumber());
                    memberMap.put("description", member.getDescription());

                    JSONObject memberJson = client.get("/orgMember?loginName=" + member.getMembercode(), JSONObject.class);
                    if (null == memberJson) {
                        JSONObject json = client.post("/orgMember", memberMap, JSONObject.class);
                        if (null != json) {
                            if (json.getBoolean("success")) {
                                JSONObject ent = json.getJSONArray("successMsgs").getJSONObject(0).getJSONObject("ent");
                                String userid = ent.getString("id");
                                ps.setString(1, userid);
                                ps.setString(2, member.getMembercode());
                                ps.setString(3, member.getMembername());
                                ps.setString(4, member.getLoginName());
                                ps.setString(5, member.getOrgDepartmentId());
                                ps.setString(6, member.getOrgLevelId());
                                ps.setString(7, member.getDescription());
                                ps.setString(8, member.getTelNumber());
                                ps.setString(9, member.getOrgPostId());
                                ps.setString(10, member.getOu());
                                ps.setString(11, member.getEnabled() ? "1" : "0");
                                ps.addBatch();

                                //这是设置ldap账号信息
//                                CtpOrgUser orgUser = new CtpOrgUser();
//                                orgUser.setId(Long.parseLong(userid));
//                                orgUser.setType("ldap.member.openLdap");
//                                orgUser.setLoginName(ent.getString("loginName"));
//                                orgUser.setExLoginName(member.getMembercode());
//                                orgUser.setExPassword("1");
//                                orgUser.setExId(member.getMemberid());
//                                orgUser.setExUserId(member.getMemberid());
//                                orgUser.setMemberId(Long.parseLong(userid));
//                                orgUser.setActionTime(new Date());
//                                orgUser.setDescription("te");
//                                orgUser.setExUnitCode("uid=" + ent.getString("loginName") + ",ou=" + member.getOu());
//                                DBAgent.save(orgUser);
                            } else {
                                JSONArray obj = (JSONArray) json.get("errorMsgInfos");
                                Map<String, Object> map = (Map<String, Object>) obj.get(0);
                                //记录更新了哪些
                                LogRecord logRecord = new LogRecord();
                                logRecord.setId(System.currentTimeMillis());
                                logRecord.setUpdateUser("自动同步");
                                logRecord.setUpdateDate(new Date());
                                logRecord.setOpType("插入");
                                logRecord.setOpContent((String) map.get("msgInfo"));
                                logRecord.setOpResult("失败！");
                                logRecordDao.saveLogRecord(logRecord);
                            }
                        }
                    } else {
                        String userid = memberJson.getString("id");
//                        CtpOrgUser user = DBAgent.get(CtpOrgUser.class, Long.parseLong(userid));

                        ps.setString(1, userid);
                        ps.setString(2, member.getMembercode());
                        ps.setString(3, member.getMembername());
                        ps.setString(4, member.getLoginName());
                        ps.setString(5, "");
                        ps.setString(6, "");
                        ps.setString(7, member.getDescription());
                        ps.setString(8, member.getTelNumber());
                        ps.setString(9, "");
                        ps.setString(10, member.getOu());
                        ps.setString(11, member.getEnabled() ? "1" : "0");

                        ps.addBatch();
                        ps.executeBatch();
                        connection.commit();

//                        CtpOrgUser orgUser = new CtpOrgUser();
//                        orgUser.setId(Long.parseLong(userid));
//                        orgUser.setType("ldap.member.openLdap");
//                        orgUser.setLoginName(memberJson.getString("loginName"));
//                        orgUser.setExLoginName(member.getMembercode());
//                        orgUser.setExPassword("1");
//                        orgUser.setExId(member.getMemberid());
//                        orgUser.setExUserId(member.getMemberid());
//                        orgUser.setMemberId(Long.parseLong(userid));
//                        orgUser.setActionTime(new Date());
//                        orgUser.setDescription("te");
//                        orgUser.setExUnitCode("uid=" + memberJson.getString("loginName") + ",ou=" + member.getOu());
//                        if (null != user) {
//                            DBAgent.update(orgUser);
//                        } else {
//                            DBAgent.save(orgUser);
//                        }
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
    public List<OrgMember> queryUpdateOrgMember() throws SQLException {
        //正方
        String sql = "select t.id,t.code,k.ou,k.is_enable,k.name,k.org_department_id unitid,k.org_level_id levelid,k.org_post_id postid,k.description,k.mobile from  (select distinct m.id,v.code  from m_org_member m,   " +
                "(select v1.\"ou\" ou,v1.IS_ENABLE,v1.code,v1.name,u1.id org_department_id,l1.id org_level_id,p1.id org_post_id,v1.description,v1.mobile    from V_ORG_MEMBER v1,m_org_unit u1,m_org_level l1,m_org_post p1    " +
                "where v1.org_department_id = u1.code and v1.org_level_id = l1.code and v1.org_post_id = p1.code) v   where v.code = m.code   and (       nvl(m.name,'~') <> nvl(v.name,'~') or    " +
                "nvl(m.org_department_id,'~') <> nvl(v.org_department_id,'~') or       nvl(m.org_level_id,'~') <> nvl(v.org_level_id,'~') or       nvl(m.org_post_id,'~') <> nvl(v.org_post_id,'~') or   " +
                "nvl(m.description,'~') <> nvl(v.description,'~') or       nvl(m.mobile,'~') <> nvl(v.mobile,'~')  or nvl(m.ou,'~')!=v.ou   or nvl(m.IS_ENABLE,'1')!=v.IS_ENABLE ) ) t  left join   " +
                "(select v2.\"ou\" ou,v2.IS_ENABLE,v2.code,v2.name,u2.id org_department_id,l2.id org_level_id,p2.id org_post_id,v2.description,v2.mobile    " +
                "from V_ORG_MEMBER v2,m_org_unit u2,m_org_level l2,m_org_post p2   where v2.org_department_id = u2.code and v2.org_level_id = l2.code and v2.org_post_id = p2.code) k   on t.code = k.code ";
        //测试
        String jinZhiSql = "select m.id,v.* from (  " +
                "select name,code,is_enable,is_deleted,mobile,DESCRIPTION,  " +
                "(select m.id from M_ORG_UNIT m where m.code=VOM.org_department_id) orgDepartmentId,  " +
                "(select MOL.id from M_ORG_LEVEL mol where MOL.code=VOM.ORG_LEVEL_ID) orgLevelId,  " +
                "(select MOP.id from M_ORG_POST mop where mop.code=vom.ORG_POST_ID) orgPostId from V_ORG_MEMBER vom ) v,M_ORG_MEMBER m where v.code=m.code and (  " +
                "nvl(m.name,'~') <> nvl(v.name,'~')    " +
                " or nvl(m.org_department_id,'~') <> nvl(v.orgDepartmentId,'~')    " +
                " or nvl(m.org_level_id,'~') <> nvl(v.orgLevelId,'~')    " +
                " or nvl(m.org_post_id,'~') <> nvl(v.orgPostId,'~')    " +
                " or nvl(m.description,'~') <> nvl(v.description,'~')    " +
                " or nvl(m.mobile,'~') <> nvl(v.mobile,'~')    " +
                " or nvl(m.IS_ENABLE,'1')!=v.IS_ENABLE   " +
                ") ";
        List<OrgMember> memberList = new ArrayList<>();
        Connection connection = JDBCAgent.getRawConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            if (XzykDao.debug) {
                ps = connection.prepareStatement(jinZhiSql);
            } else {
                ps = connection.prepareStatement(sql);
            }
            rs = ps.executeQuery();
            OrgMember orgMember = null;
            while (rs.next()) {
                orgMember = new OrgMember();
                orgMember.setMemberid(rs.getString("id"));
                orgMember.setOrgAccountId(configTools.getOrgAccountId());
                orgMember.setMembercode(rs.getString("code"));
                orgMember.setMembername(rs.getString("name"));
                orgMember.setLoginName(rs.getString("code"));
                orgMember.setOrgDepartmentId(rs.getString("orgdepartmentid"));
                orgMember.setOrgLevelId(rs.getString("orglevelid"));
                orgMember.setOrgPostId(rs.getString("orgpostid"));
                orgMember.setDescription(rs.getString("description"));
                orgMember.setTelNumber(rs.getString("mobile"));
//                orgMember.setOu(rs.getString("ou"));
                orgMember.setP1(rs.getString("is_enable"));
                memberList.add(orgMember);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SyncConnectionUtil.closeResultSet(rs);
            SyncConnectionUtil.closePrepareStatement(ps);
            SyncConnectionUtil.closeConnection(connection);
        }

        return memberList;
    }

    @Override
    public void updateOrgMember(List<OrgMember> list) {
        CTPRestClient client = SyncConnectionUtil.getOaRest();
        try {
            if (null != list && list.size() > 0) {
                Map memberMap = null;
                for (OrgMember member : list) {
                    memberMap = new HashMap();
                    memberMap.put("id", member.getMemberid());
                    memberMap.put("name", member.getMembername());
                    memberMap.put("loginName", member.getLoginName());
                    memberMap.put("orgAccountId", member.getOrgAccountId());
                    memberMap.put("orgLevelId", member.getOrgLevelId());
                    memberMap.put("orgPostId", member.getOrgPostId());
                    memberMap.put("orgDepartmentId", member.getOrgDepartmentId());
                    memberMap.put("code", member.getMembercode());
                    if (!"".equals(member.getP1()) && "1".equals(member.getP1())) {
                        memberMap.put("enabled", true);
                    } else if ("0".equals(member.getP1())) {
                        memberMap.put("enabled", false);
                    } else {
                        memberMap.put("enabled", true);
                    }
                    memberMap.put("telNumber", member.getTelNumber() != null ? member.getTelNumber() : "");
                    memberMap.put("description", member.getDescription() != null ? member.getDescription() : "");

                    JSONObject memberJson = client.get("/orgMember?loginName=" + member.getMembercode(), JSONObject.class);
                    if (null != memberJson) {
                        JSONObject json = client.put("/orgMember", memberMap, JSONObject.class);
                        if (null != json) {
                            if (json.getBoolean("success")) {
                                JSONObject ent = json.getJSONArray("successMsgs").getJSONObject(0).getJSONObject("ent");
                                String userid = ent.getString("id");
//                                CtpOrgUser user = DBAgent.get(CtpOrgUser.class, Long.parseLong(userid));
//                                CtpOrgUser orgUser = new CtpOrgUser();
//                                orgUser.setId(Long.parseLong(userid));
//                                orgUser.setType("ldap.member.openLdap");
//                                orgUser.setLoginName(ent.getString("loginName"));
//                                orgUser.setExLoginName(member.getMembercode());
//                                orgUser.setExPassword("1");
//                                orgUser.setExId(member.getMemberid());
//                                orgUser.setExUserId(member.getMemberid());
//                                orgUser.setMemberId(Long.parseLong(userid));
//                                orgUser.setActionTime(new Date());
//                                orgUser.setDescription("te");
//                                orgUser.setExUnitCode("uid=" + ent.getString("loginName") + ",ou=" + member.getOu());
//                                if (null != user) {
//                                    DBAgent.update(orgUser);
//                                } else {
//                                    DBAgent.save(orgUser);
//                                }


                                String sql = "update m_org_member set ";
                                if (member.getMembername() != null && !"".equals(member.getMembername())) {
                                    sql = sql + " name = '" + member.getMembername() + "', ";
                                } else {
                                    sql = sql + " name = '', ";
                                }

                                if (member.getOrgDepartmentId() != null && !"".equals(member.getOrgDepartmentId())) {
                                    sql = sql + " org_department_id = '" + member.getOrgDepartmentId() + "', ";
                                } else {
                                    sql = sql + " org_department_id = '', ";
                                }

                                if (member.getOrgPostId() != null && !"".equals(member.getOrgPostId())) {
                                    sql = sql + " org_post_id = '" + member.getOrgPostId() + "', ";
                                } else {
                                    sql = sql + " org_post_id = '', ";
                                }

                                if (member.getOrgLevelId() != null && !"".equals(member.getOrgLevelId())) {
                                    sql = sql + " org_level_id = '" + member.getOrgLevelId() + "', ";
                                } else {
                                    sql = sql + " org_level_id = '', ";
                                }

                                if (member.getDescription() != null && !"".equals(member.getDescription())) {
                                    sql = sql + " description = '" + member.getDescription() + "', ";
                                } else {
                                    sql = sql + " description = '', ";
                                }

//                                if (member.getOu() != null && !"".equals(member.getOu())) {
//                                    sql = sql + " ou = '" + member.getOu() + "', ";
//                                } else {
//                                    sql = sql + " ou = '', ";
//                                }

                                if (member.getP1() != null && !"".equals(member.getP1())) {
                                    sql = sql + " is_enable = '" + member.getP1() + "', ";
                                } else {
                                    sql = sql + " is_enable = '', ";
                                }

                                if (member.getTelNumber() != null && !"".equals(member.getTelNumber())) {
                                    sql = sql + " mobile = '" + member.getTelNumber() + "' ";
                                } else {
                                    sql = sql + " mobile = '' ";
                                }

                                sql = sql + " where id = '" + member.getMemberid() + "' ";

                                SyncConnectionUtil.insertResult(sql);
                            }
                        }
                    } else {

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OrgMember> queryNotExistOrgMember() throws SQLException {
        String sql = "select mom.id from M_ORG_MEMBER mom where not EXISTS (select * from V_ORG_MEMBER vom where mom.code=vom.code )";
        List<OrgMember> memberList = new ArrayList<>();
        Connection connection = JDBCAgent.getRawConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            OrgMember orgMember = null;
            while (rs.next()) {
                orgMember = new OrgMember();
                orgMember.setMemberid(rs.getString("id"));
                memberList.add(orgMember);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SyncConnectionUtil.closeResultSet(rs);
            SyncConnectionUtil.closePrepareStatement(ps);
            SyncConnectionUtil.closeConnection(connection);
        }
        return memberList;
    }

    @Override
    public void deleteOrgMember(List<OrgMember> list) {
        CTPRestClient client = SyncConnectionUtil.getOaRest();
        try {
            if (null != list && list.size() > 0) {
                Map map = null;
                StringBuffer dsql = new StringBuffer();
                dsql.append("delete from m_org_member where id in (0 ");
                for (OrgMember member : list) {

                    map = new HashMap();
                    map.put("id", member.getMemberid());
                    map.put("enabled", false);
                    JSONObject jsonObject = client.put("/orgMember/" + member.getMemberid() + "/enabled/false", map, JSONObject.class);
                    if (null != jsonObject) {

                        if (jsonObject.getBoolean("success")) {
                            dsql.append(",'" + member.getMemberid() + "'");
                        }
                    } else {
                        dsql.append(",'" + member.getMemberid() + "'");
                    }

                }
                dsql.append(")");
                SyncConnectionUtil.insertResult(dsql.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
