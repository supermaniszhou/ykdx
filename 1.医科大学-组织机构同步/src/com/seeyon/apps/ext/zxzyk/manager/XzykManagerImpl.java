package com.seeyon.apps.ext.zxzyk.manager;

import com.seeyon.apps.ext.zxzyk.dao.XzykDaoImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.seeyon.apps.ext.zxzyk.dao.XzykDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class XzykManagerImpl implements XzykManager {
    private static final Log log = LogFactory.getLog(XzykManagerImpl.class);

    private XzykDao xzykDao = new XzykDaoImpl();

    @Override
    public void insertAll() throws SQLException {
        final String unitSql = "select * from USR_DATA.V_ORG_UNIT";
        List<Map<String, Object>> unitListMap = xzykDao.queryAll(unitSql);
        xzykDao.insertAll("V_ORG_UNIT", unitListMap);

        final String prinSql = "select * from USR_DATA.V_ORG_PRINCVIPAL";
        List<Map<String, Object>> prinListMap = xzykDao.queryAll(prinSql);
        xzykDao.insertAll("V_ORG_PRINCIPAL", prinListMap);

        final String phoneSql = "select * from USR_DATA.V_JZG_PHONE";
        List<Map<String, Object>> phoneListMap = xzykDao.queryAll(phoneSql);
        xzykDao.insertAll("V_JZG_PHONE", phoneListMap);

        final String memberSql = "select * from USR_DATA.V_ORG_MEMBER";
        List<Map<String, Object>> memberListMap = xzykDao.queryAll(memberSql);
        xzykDao.insertAll("V_ORG_MEMBER", memberListMap);

        final String levelSql = "select * from USR_DATA.V_ORG_LEVEL";
        List<Map<String, Object>> levelListMap = xzykDao.queryAll(levelSql);
        xzykDao.insertAll("V_ORG_LEVEL", levelListMap);
    }

    @Override
    public void clearTable() {
        xzykDao.clearData("delete from V_ORG_UNIT");
        xzykDao.clearData("delete from V_ORG_PRINCVIPAL");
        xzykDao.clearData("delete from V_JZG_PHONE");
        xzykDao.clearData("delete from V_ORG_LEVEL");
        xzykDao.clearData("delete from V_ORG_MEMBER");
    }
}
