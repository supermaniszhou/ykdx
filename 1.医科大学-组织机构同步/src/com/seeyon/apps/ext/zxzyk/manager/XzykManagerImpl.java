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
        String unitSql = "select * from USR_DATA.V_ORG_UNIT";
        List<Map<String, Object>> unitListMap = xzykDao.queryAll(unitSql);
        xzykDao.insertAll("V_ORG_UNIT", unitListMap);
    }


}
