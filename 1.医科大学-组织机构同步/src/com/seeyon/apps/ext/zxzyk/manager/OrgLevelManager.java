package com.seeyon.apps.ext.zxzyk.manager;


import java.sql.SQLException;

public interface OrgLevelManager {

    void insertOrgLevel() throws SQLException;

    void updateOrgLevel() throws SQLException;

    void deleteNotExistLevel() throws SQLException;
}
