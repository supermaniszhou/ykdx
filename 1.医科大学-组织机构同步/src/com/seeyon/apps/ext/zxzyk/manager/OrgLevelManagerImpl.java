package com.seeyon.apps.ext.zxzyk.manager;

import com.seeyon.apps.ext.zxzyk.dao.OrgLevelDao;
import com.seeyon.apps.ext.zxzyk.dao.OrgLevelDaoImpl;
import com.seeyon.apps.ext.zxzyk.po.OrgLevel;

import java.sql.SQLException;
import java.util.List;

public class OrgLevelManagerImpl implements OrgLevelManager {

    private OrgLevelDao orgLevelDao = new OrgLevelDaoImpl();

    @Override
    public void insertOrgLevel() throws SQLException {
        List<OrgLevel> list = orgLevelDao.queryOrgLevel();
        orgLevelDao.insertOrgLevel(list);
    }

    @Override
    public void updateOrgLevel() throws SQLException {
        List<OrgLevel> list = orgLevelDao.queryChangerLevel();
        orgLevelDao.updateOrgLevel(list);
    }

    @Override
    public void deleteNotExistLevel() throws SQLException {
        List<OrgLevel> list = orgLevelDao.queryNotExistLevel();
        orgLevelDao.deleteOrgLevel(list);
    }
}
