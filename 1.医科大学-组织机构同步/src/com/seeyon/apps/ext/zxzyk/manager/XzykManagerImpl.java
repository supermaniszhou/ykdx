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

    private XzykDao demoDao = new XzykDaoImpl();

    @Override
    public List<Map<String, Object>> queryAll(String sql) throws SQLException {
        return demoDao.queryAll(sql);
    }


}
