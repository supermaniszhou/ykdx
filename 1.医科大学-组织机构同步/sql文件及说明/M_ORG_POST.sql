/*
Navicat Oracle Data Transfer
Oracle Client Version : 11.2.0.1.0

Source Server         : a871sp1_qiye
Source Server Version : 110200
Source Host           : 127.0.0.1:1521
Source Schema         : A871SP1_QIYE

Target Server Type    : ORACLE
Target Server Version : 110200
File Encoding         : 65001

Date: 2021-02-25 11:40:41
*/


-- ----------------------------
-- Table structure for M_ORG_POST
-- ----------------------------
DROP TABLE "M_ORG_POST";
CREATE TABLE "M_ORG_POST" (
"ID" VARCHAR2(200 BYTE) NULL ,
"CODE" VARCHAR2(200 BYTE) NULL ,
"NAME" VARCHAR2(200 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of M_ORG_POST
-- ----------------------------
INSERT INTO "M_ORG_POST" VALUES ('-7827244643849385202', '1000', '教职工');
INSERT INTO "M_ORG_POST" VALUES ('-5332188854714021415', '1001', '辅导员');
INSERT INTO "M_ORG_POST" VALUES ('5587954234315391407', '1002', '双肩挑(教学为主）');
INSERT INTO "M_ORG_POST" VALUES ('-8965193118173542928', '1003', '其他专业技术（实验）');
INSERT INTO "M_ORG_POST" VALUES ('-8333040461505607295', '1004', '其他专业技术（图书）');
INSERT INTO "M_ORG_POST" VALUES ('-7198937292775404309', '1005', '教师（教学科研并重）');
INSERT INTO "M_ORG_POST" VALUES ('-1435081585993691230', '1006', '其他专业技术（会计）');
INSERT INTO "M_ORG_POST" VALUES ('-4731516005809043543', '1007', '其他专业技术（档案）');
INSERT INTO "M_ORG_POST" VALUES ('-6441942651341681258', '1008', '管理');
INSERT INTO "M_ORG_POST" VALUES ('-7617059948539066742', '1009', '教师（科研为主）');
INSERT INTO "M_ORG_POST" VALUES ('1630645980326350014', '1010', '其他专业技术（工程）');
INSERT INTO "M_ORG_POST" VALUES ('4484245496553423381', '1011', '其他专业技术（编辑）');
INSERT INTO "M_ORG_POST" VALUES ('2579633998398046843', '1012', '工勤技能');
INSERT INTO "M_ORG_POST" VALUES ('1823712848597371966', '1013', '其他专业技术（审计）');
INSERT INTO "M_ORG_POST" VALUES ('4656554783352376116', '1014', '教师（教学为主）');
INSERT INTO "M_ORG_POST" VALUES ('-3959184408638504431', '1015', '双肩挑(教学科研并重）');
INSERT INTO "M_ORG_POST" VALUES ('9074453877699464611', '1016', '其他专业技术（医疗）');
INSERT INTO "M_ORG_POST" VALUES ('-7081833988560894467', '1017', '双肩挑(科研为主）');
