package com.seeyon.apps.ext.zxzyk.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.seeyon.apps.ext.zxzyk.dao.xzykDao;
import com.seeyon.ctp.common.AppContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class xzykManagerImpl implements xzykManager {
    private static final Log log = LogFactory.getLog(xzykManagerImpl.class);

    private xzykDao demoDao;

    @Override
    public List<Map<String, Object>> queryAll(String sql) throws SQLException {
        return demoDao.queryAll(sql);
    }

    public xzykDao getxzykDao() {
        if (demoDao == null) {
            demoDao = (xzykDao) AppContext.getBean("xzykDaoDemo");
        }
        return demoDao;
    }


}
