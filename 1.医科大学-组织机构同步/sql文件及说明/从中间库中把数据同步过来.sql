--部门表
drop table v_org_unit;
create table v_org_unit(
    ID VARCHAR2(50)  ,
    NAME VARCHAR2(50) ,
    SECOND_NAME VARCHAR2(50) ,
    CODE VARCHAR2(50) ,
    UINT VARCHAR2(50) ,
    SHORT_NAME VARCHAR2(50) ,
    SORT_ID VARCHAR2(50) ,
    IS_ENABLE VARCHAR2(1) ,
    IS_DELETED VARCHAR2(1) ,
    STATUS VARCHAR2(1) ,
    CREATE_TIME VARCHAR2(50) ,
    UPDATE_TIME VARCHAR2(50)
);
--用户登陆信息表
drop table v_org_principal;
create table v_org_principal(
    ID  VARCHAR2(50) ,
    LOGIN_NAME VARCHAR2(50),
    CREDENTIAL_VALUE VARCHAR2(50),
    CLASS_NAME VARCHAR2(50),
    EXPIRATION_DATE VARCHAR2(50),
    MEMBER_ID VARCHAR2(50),
    IS_ENABLE VARCHAR2(50),
    CREATE_TIME VARCHAR2(50),
    UPDATE_TIME VARCHAR2(50)
);

--用户表信息
drop table V_ORG_MEMBER;
create table V_ORG_MEMBER(
    ID VARCHAR2(50) ,
    NAME VARCHAR2(50) ,
    CODE VARCHAR2(50) ,
    IS_INTERNAL VARCHAR2(50) ,
    IS_LOGINABLE VARCHAR2(50) ,
    IS_VIRTUAL VARCHAR2(50) ,
    IS_ADMIN VARCHAR2(50) ,
    IS_ASSIGNED VARCHAR2(50) ,
    TYPE VARCHAR2(50) ,
    STATE VARCHAR2(50) ,
    IS_ENABLE VARCHAR2(50) ,
    IS_DELETED VARCHAR2(50) ,
    STATUS VARCHAR2(50) ,
    SORT_ID VARCHAR2(50) ,
    ORG_DEPARTMENT_ID VARCHAR2(50) ,
    SUP_DEPARTMENT_ID VARCHAR2(50) ,
    ORG_POST_ID VARCHAR2(50) ,
    ORG_LEVEL_ID VARCHAR2(50) ,
    DESCRIPTION VARCHAR2(50) ,
    CREATE_TIME VARCHAR2(50),
    UPDATE_TIME VARCHAR2(50),
    MOBILE VARCHAR2(50)
);

--职级表
drop table v_org_level;
CREATE TABLE v_org_level (
	ID VARCHAR2 (50) ,
	NAME VARCHAR2 (50),
	CODE VARCHAR2 (50),
	IS_ENABLE VARCHAR2 (50),
	LEVEL_ID VARCHAR2 (50),
	GROUP_LEVEL_ID VARCHAR2 (50),
	SORT_ID VARCHAR2 (50),
	CREATE_TIME VARCHAR2(50),
	UPDATE_TIME VARCHAR2(50),
	DESCRIPTION VARCHAR2 (50),
	ORG_ACCOUNT_ID VARCHAR2 (50),
	IS_DELETED VARCHAR2 (50),
	STATUS VARCHAR2 (50)
);
--用户电话表
drop table v_jzg_phone;
CREATE TABLE v_jzg_phone (
	GH VARCHAR2 (50) ,
	XM VARCHAR2 (50),
	MOBILE VARCHAR2 (50)
);

