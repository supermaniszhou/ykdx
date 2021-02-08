package com.seeyon.apps.ext.zxzyk.manager;

import java.sql.SQLException;

public interface OrgMemberManager {

    void insertOrgMember() throws SQLException;

    void updateOrgMember() throws SQLException;

    void deleteOrgMember() throws SQLException;

    void updateEnableOrgmember() throws SQLException;
}
