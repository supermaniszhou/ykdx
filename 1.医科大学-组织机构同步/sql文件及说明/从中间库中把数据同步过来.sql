--部门表
drop table v_org_unit;
create table v_org_unit(
    ID VARCHAR2(100)  ,
    NAME VARCHAR2(100) ,
    SECOND_NAME VARCHAR2(100) ,
    CODE VARCHAR2(100) ,
    UINT VARCHAR2(100) ,
    SHORT_NAME VARCHAR2(100) ,
    SORT_ID VARCHAR2(100) ,
    IS_ENABLE VARCHAR2(1) ,
    IS_DELETED VARCHAR2(1) ,
    STATUS VARCHAR2(1) ,
    CREATE_TIME VARCHAR2(100) ,
    UPDATE_TIME VARCHAR2(100)
);
--用户登陆信息表
drop table v_org_principal;
create table v_org_principal(
    ID  VARCHAR2(100) ,
    LOGIN_NAME VARCHAR2(100),
    CREDENTIAL_VALUE VARCHAR2(100),
    CLASS_NAME VARCHAR2(100),
    EXPIRATION_DATE VARCHAR2(100),
    MEMBER_ID VARCHAR2(100),
    IS_ENABLE VARCHAR2(100),
    CREATE_TIME VARCHAR2(100),
    UPDATE_TIME VARCHAR2(100)
);

--用户表信息
drop table V_ORG_MEMBER;
create table V_ORG_MEMBER(
    ID VARCHAR2(100) ,
    NAME VARCHAR2(100) ,
    CODE VARCHAR2(100) ,
    IS_INTERNAL VARCHAR2(100) ,
    IS_LOGINABLE VARCHAR2(100) ,
    IS_VIRTUAL VARCHAR2(100) ,
    IS_ADMIN VARCHAR2(100) ,
    IS_ASSIGNED VARCHAR2(100) ,
    TYPE VARCHAR2(100) ,
    STATE VARCHAR2(100) ,
    IS_ENABLE VARCHAR2(100) ,
    IS_DELETED VARCHAR2(100) ,
    STATUS VARCHAR2(100) ,
    SORT_ID VARCHAR2(100) ,
    ORG_DEPARTMENT_ID VARCHAR2(100) ,
    SUP_DEPARTMENT_ID VARCHAR2(100) ,
    ORG_POST_ID VARCHAR2(100) ,
    ORG_LEVEL_ID VARCHAR2(100) ,
    DESCRIPTION VARCHAR2(100) ,
    CREATE_TIME VARCHAR2(100),
    UPDATE_TIME VARCHAR2(100),
    MOBILE VARCHAR2(100)
);

--职级表
drop table v_org_level;
CREATE TABLE v_org_level (
	ID VARCHAR2 (100) ,
	NAME VARCHAR2 (100),
	CODE VARCHAR2 (100),
	IS_ENABLE VARCHAR2 (100),
	LEVEL_ID VARCHAR2 (100),
	GROUP_LEVEL_ID VARCHAR2 (100),
	SORT_ID VARCHAR2 (100),
	CREATE_TIME VARCHAR2(100),
	UPDATE_TIME VARCHAR2(100),
	DESCRIPTION VARCHAR2 (100),
	ORG_ACCOUNT_ID VARCHAR2 (100),
	IS_DELETED VARCHAR2 (100),
	STATUS VARCHAR2 (100)
);
--用户电话表
drop table v_jzg_phone;
CREATE TABLE v_jzg_phone (
	GH VARCHAR2 (100) ,
	XM VARCHAR2 (100),
	MOBILE VARCHAR2 (100)
);

