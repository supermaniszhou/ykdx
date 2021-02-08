--职务
create table M_ORG_LEVEL(
    id VARCHAR2(100),
    name VARCHAR2(100),
    code VARCHAR2(100),
    description VARCHAR2(100)
);
--单位
CREATE TABLE M_ORG_UNIT (
    ID VARCHAR2(100),
	NAME VARCHAR2(100), 
	SECOND_NAME VARCHAR2(100), 
	CODE VARCHAR2(100), 
	UINT VARCHAR2(100), 
	SHORT_NAME VARCHAR2(100), 
	SORT_ID VARCHAR2(100), 
	IS_ENABLE VARCHAR2(1), 
	IS_DELETED VARCHAR2(1), 
	STATUS VARCHAR2(1), 
	CREATE_TIME VARCHAR2(100), 
	UPDATE_TIME VARCHAR2(100)
) ;

--人员
drop table M_ORG_MEMBER;
CREATE TABLE M_ORG_MEMBER (
	ID varchar2(200),
	code varchar2(200),
	NAME varchar2(200),
	login_name varchar2(200),
	org_department_id varchar2(200),
	org_level_id varchar2(200),
	description varchar2(200),
	mobile varchar2(200),
	org_post_id varchar2(200),
	ou varchar2(200),
	is_enable varchar2(200)
);

