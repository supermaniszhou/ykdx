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


