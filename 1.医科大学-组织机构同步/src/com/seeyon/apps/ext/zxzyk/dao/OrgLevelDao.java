package com.seeyon.apps.ext.zxzyk.dao;

import com.seeyon.apps.ext.zxzyk.po.OrgLevel;

import java.sql.SQLException;
import java.util.List;

public interface OrgLevelDao {
    List<OrgLevel> queryOrgLevel() throws SQLException;

    List<OrgLevel> queryChangerLevel() throws SQLException;

    List<OrgLevel> queryNotExistLevel() throws SQLException;


    void insertOrgLevel(List<OrgLevel> list);

    void updateOrgLevel(List<OrgLevel> list);

    void deleteOrgLevel(List<OrgLevel> list);
}
