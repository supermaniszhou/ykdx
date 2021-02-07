package com.seeyon.apps.ext.zxzyk.manager;

import com.seeyon.apps.ext.zxzyk.dao.OrgDeptDao;
import com.seeyon.apps.ext.zxzyk.dao.OrgDeptDaoImpl;
import com.seeyon.apps.ext.zxzyk.po.OrgDept;
import com.seeyon.apps.ext.zxzyk.util.ReadConfigTools;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2019-7-29.
 */
public class OrgDeptManagerImpl implements OrgDeptManager {

    private OrgDeptDao orgDeptDao = new OrgDeptDaoImpl();

    private ReadConfigTools configTools = new ReadConfigTools();

    @Override
    public void insertOtherDept() throws SQLException {
//        List<OrgDept> firstDeptlist = orgDeptDao.queryByFirstDept();
//        orgDeptDao.insertFirstDept(firstDeptlist);

        List<OrgDept> list = orgDeptDao.queryByOtherDept(configTools.getOrgAccountId());
        orgDeptDao.insertOrgDept_new(list);
    }

    @Override
    public void deleteOrgDept() {
        orgDeptDao.deleteDept();
    }

    @Override
    public void updateOrgDept() {
        orgDeptDao.updateOrgDept();
    }
}
