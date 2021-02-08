package com.seeyon.apps.ext.zxzyk.dao;

import com.seeyon.apps.ext.zxzyk.po.OrgMember;

import java.sql.SQLException;
import java.util.List;

public interface OrgMemberDao {

    List<OrgMember> queryAddOrgMember() throws SQLException;

    void insertOrgMember(List<OrgMember> list);

    List<OrgMember> queryUpdateOrgMember() throws SQLException;

    void updateOrgMember(List<OrgMember> list);

    List<OrgMember> queryNotExistOrgMember() throws SQLException;

    void deleteOrgMember(List<OrgMember> list);
    List<OrgMember> queryNoEnableMember() throws SQLException;

    void updateIsEnableOfMember(List<OrgMember> list);
}
