/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50152
Source Host           : 127.0.0.1:3306
Source Database       : sample

Target Server Type    : MYSQL
Target Server Version : 50152
File Encoding         : 65001

Date: 2016-02-26 17:00:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for act_evt_log
-- ----------------------------
DROP TABLE IF EXISTS `act_evt_log`;
CREATE TABLE `act_evt_log` (
  `LOG_NR_` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TIME_STAMP_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DATA_` longblob,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp NULL DEFAULT NULL,
  `IS_PROCESSED_` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`LOG_NR_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_evt_log
-- ----------------------------

-- ----------------------------
-- Table structure for act_ge_bytearray
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_bytearray`;
CREATE TABLE `act_ge_bytearray` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTES_` longblob,
  `GENERATED_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_BYTEARR_DEPL` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_BYTEARR_DEPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_ge_bytearray
-- ----------------------------

-- ----------------------------
-- Table structure for act_ge_property
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_property`;
CREATE TABLE `act_ge_property` (
  `NAME_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `VALUE_` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  PRIMARY KEY (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_ge_property
-- ----------------------------
INSERT INTO `act_ge_property` VALUES ('next.dbid', '1', '1');
INSERT INTO `act_ge_property` VALUES ('schema.history', 'create(5.17.0.2)', '1');
INSERT INTO `act_ge_property` VALUES ('schema.version', '5.17.0.2', '1');

-- ----------------------------
-- Table structure for act_hi_actinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_actinst`;
CREATE TABLE `act_hi_actinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin NOT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ACT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime NOT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_ACT_INST_START` (`START_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_PROCINST` (`PROC_INST_ID_`,`ACT_ID_`),
  KEY `ACT_IDX_HI_ACT_INST_EXEC` (`EXECUTION_ID_`,`ACT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_hi_actinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_attachment
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_attachment`;
CREATE TABLE `act_hi_attachment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `URL_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CONTENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_hi_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_comment
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_comment`;
CREATE TABLE `act_hi_comment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime NOT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MESSAGE_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `FULL_MSG_` longblob,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_hi_comment
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_detail
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_detail`;
CREATE TABLE `act_hi_detail` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TIME_` datetime NOT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_DETAIL_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_ACT_INST` (`ACT_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_TIME` (`TIME_`),
  KEY `ACT_IDX_HI_DETAIL_NAME` (`NAME_`),
  KEY `ACT_IDX_HI_DETAIL_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_hi_detail
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_identitylink`;
CREATE TABLE `act_hi_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_TASK` (`TASK_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_hi_identitylink
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_procinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_procinst`;
CREATE TABLE `act_hi_procinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `START_TIME_` datetime NOT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `START_USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `END_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `PROC_INST_ID_` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PRO_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_PRO_I_BUSKEY` (`BUSINESS_KEY_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_hi_procinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_taskinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_taskinst`;
CREATE TABLE `act_hi_taskinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime NOT NULL,
  `CLAIM_TIME_` datetime DEFAULT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `DUE_DATE_` datetime DEFAULT NULL,
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_hi_taskinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_varinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_varinst`;
CREATE TABLE `act_hi_varinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime DEFAULT NULL,
  `LAST_UPDATED_TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_PROCVAR_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PROCVAR_NAME_TYPE` (`NAME_`,`VAR_TYPE_`),
  KEY `ACT_IDX_HI_PROCVAR_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_hi_varinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_group
-- ----------------------------
DROP TABLE IF EXISTS `act_id_group`;
CREATE TABLE `act_id_group` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_id_group
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_info
-- ----------------------------
DROP TABLE IF EXISTS `act_id_info`;
CREATE TABLE `act_id_info` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `VALUE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PASSWORD_` longblob,
  `PARENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_id_info
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_membership
-- ----------------------------
DROP TABLE IF EXISTS `act_id_membership`;
CREATE TABLE `act_id_membership` (
  `USER_ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `GROUP_ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`USER_ID_`,`GROUP_ID_`),
  KEY `ACT_FK_MEMB_GROUP` (`GROUP_ID_`),
  CONSTRAINT `ACT_FK_MEMB_GROUP` FOREIGN KEY (`GROUP_ID_`) REFERENCES `act_id_group` (`ID_`),
  CONSTRAINT `ACT_FK_MEMB_USER` FOREIGN KEY (`USER_ID_`) REFERENCES `act_id_user` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_id_membership
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_user
-- ----------------------------
DROP TABLE IF EXISTS `act_id_user`;
CREATE TABLE `act_id_user` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `FIRST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LAST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PWD_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PICTURE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_id_user
-- ----------------------------

-- ----------------------------
-- Table structure for act_re_deployment
-- ----------------------------
DROP TABLE IF EXISTS `act_re_deployment`;
CREATE TABLE `act_re_deployment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOY_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_re_deployment
-- ----------------------------

-- ----------------------------
-- Table structure for act_re_model
-- ----------------------------
DROP TABLE IF EXISTS `act_re_model`;
CREATE TABLE `act_re_model` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp NULL DEFAULT NULL,
  `LAST_UPDATE_TIME_` timestamp NULL DEFAULT NULL,
  `VERSION_` int(11) DEFAULT NULL,
  `META_INFO_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_EXTRA_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_MODEL_SOURCE` (`EDITOR_SOURCE_VALUE_ID_`),
  KEY `ACT_FK_MODEL_SOURCE_EXTRA` (`EDITOR_SOURCE_EXTRA_VALUE_ID_`),
  KEY `ACT_FK_MODEL_DEPLOYMENT` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_MODEL_DEPLOYMENT` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE` FOREIGN KEY (`EDITOR_SOURCE_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE_EXTRA` FOREIGN KEY (`EDITOR_SOURCE_EXTRA_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_re_model
-- ----------------------------

-- ----------------------------
-- Table structure for act_re_procdef
-- ----------------------------
DROP TABLE IF EXISTS `act_re_procdef`;
CREATE TABLE `act_re_procdef` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `HAS_START_FORM_KEY_` tinyint(4) DEFAULT NULL,
  `HAS_GRAPHICAL_NOTATION_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_PROCDEF` (`KEY_`,`VERSION_`,`TENANT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_re_procdef
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_event_subscr
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_event_subscr`;
CREATE TABLE `act_ru_event_subscr` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `EVENT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EVENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTIVITY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CONFIGURATION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EVENT_SUBSCR_CONFIG_` (`CONFIGURATION_`),
  KEY `ACT_FK_EVENT_EXEC` (`EXECUTION_ID_`),
  CONSTRAINT `ACT_FK_EVENT_EXEC` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_ru_event_subscr
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_execution
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_execution`;
CREATE TABLE `act_ru_execution` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_EXEC_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_ACTIVE_` tinyint(4) DEFAULT NULL,
  `IS_CONCURRENT_` tinyint(4) DEFAULT NULL,
  `IS_SCOPE_` tinyint(4) DEFAULT NULL,
  `IS_EVENT_SCOPE_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `CACHED_ENT_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EXEC_BUSKEY` (`BUSINESS_KEY_`),
  KEY `ACT_FK_EXE_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_EXE_PARENT` (`PARENT_ID_`),
  KEY `ACT_FK_EXE_SUPER` (`SUPER_EXEC_`),
  KEY `ACT_FK_EXE_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_EXE_PARENT` FOREIGN KEY (`PARENT_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ACT_FK_EXE_SUPER` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_ru_execution
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_identitylink`;
CREATE TABLE `act_ru_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_IDENT_LNK_GROUP` (`GROUP_ID_`),
  KEY `ACT_IDX_ATHRZ_PROCEDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_TSKASS_TASK` (`TASK_ID_`),
  KEY `ACT_FK_IDL_PROCINST` (`PROC_INST_ID_`),
  CONSTRAINT `ACT_FK_ATHRZ_PROCEDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_IDL_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TSKASS_TASK` FOREIGN KEY (`TASK_ID_`) REFERENCES `act_ru_task` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_ru_identitylink
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_job`;
CREATE TABLE `act_ru_job` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_JOB_EXCEPTION` (`EXCEPTION_STACK_ID_`),
  CONSTRAINT `ACT_FK_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_ru_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_task
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_task`;
CREATE TABLE `act_ru_task` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DELEGATION_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `CREATE_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `DUE_DATE_` datetime DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_TASK_CREATE` (`CREATE_TIME_`),
  KEY `ACT_FK_TASK_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_TASK_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_TASK_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_ru_task
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_variable
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_variable`;
CREATE TABLE `act_ru_variable` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_VARIABLE_TASK_ID` (`TASK_ID_`),
  KEY `ACT_FK_VAR_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_VAR_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_VAR_BYTEARRAY` (`BYTEARRAY_ID_`),
  CONSTRAINT `ACT_FK_VAR_BYTEARRAY` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_ru_variable
-- ----------------------------

-- ----------------------------
-- Table structure for b_area
-- ----------------------------
DROP TABLE IF EXISTS `b_area`;
CREATE TABLE `b_area` (
  `id` varchar(36) NOT NULL COMMENT '区域ID',
  `name` varchar(255) DEFAULT NULL COMMENT '区域名称',
  `parentId` varchar(36) DEFAULT NULL,
  `phoneCode` varchar(255) DEFAULT NULL,
  `zipId` varchar(50) DEFAULT NULL,
  `areaCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='区域表';

-- ----------------------------
-- Records of b_area
-- ----------------------------

-- ----------------------------
-- Table structure for b_logistics
-- ----------------------------
DROP TABLE IF EXISTS `b_logistics`;
CREATE TABLE `b_logistics` (
  `id` varchar(36) NOT NULL COMMENT '物流园Id',
  `name` varchar(255) DEFAULT NULL COMMENT '物流园名称',
  `areaId` varchar(36) DEFAULT NULL COMMENT '区域ID',
  `address` varchar(255) DEFAULT NULL COMMENT '物流园地址',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='物流园表';

-- ----------------------------
-- Records of b_logistics
-- ----------------------------

-- ----------------------------
-- Table structure for c_fleet
-- ----------------------------
DROP TABLE IF EXISTS `c_fleet`;
CREATE TABLE `c_fleet` (
  `id` varchar(36) NOT NULL COMMENT '车队详情ID',
  `name` varchar(255) DEFAULT NULL COMMENT '车队名称',
  `type` bit(1) DEFAULT NULL COMMENT '车队类型1、车队；2、个人',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='车队详情表';

-- ----------------------------
-- Records of c_fleet
-- ----------------------------

-- ----------------------------
-- Table structure for c_fleet_user
-- ----------------------------
DROP TABLE IF EXISTS `c_fleet_user`;
CREATE TABLE `c_fleet_user` (
  `id` varchar(36) NOT NULL COMMENT '车队用户ID',
  `userName` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码MD5加密',
  `realName` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `fleetId` varchar(36) DEFAULT NULL COMMENT '车队ID',
  `trackId` varchar(36) DEFAULT NULL COMMENT '车辆ID',
  `roleId` varchar(36) DEFAULT NULL COMMENT '角色ID',
  `areaId` varchar(36) DEFAULT NULL COMMENT '区域ID',
  `status` bit(1) DEFAULT NULL COMMENT '状态：1启用、0关闭',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='车队用户表';

-- ----------------------------
-- Records of c_fleet_user
-- ----------------------------

-- ----------------------------
-- Table structure for c_track
-- ----------------------------
DROP TABLE IF EXISTS `c_track`;
CREATE TABLE `c_track` (
  `id` varchar(36) NOT NULL COMMENT '车辆详情Id',
  `type` bit(1) DEFAULT NULL COMMENT '车辆类型',
  `c_long` double(11,3) DEFAULT NULL COMMENT '车辆长度',
  `capcity` double(11,3) NOT NULL COMMENT '车辆载重',
  `licenseId` varchar(50) DEFAULT NULL COMMENT '行驶证编号',
  `licenseImg` varchar(255) DEFAULT NULL COMMENT '行驶证图片',
  `bandId` varchar(36) DEFAULT NULL COMMENT '车辆品牌Id',
  `engineTypeId` varchar(36) DEFAULT NULL COMMENT '发动机类型Id',
  `hp` double DEFAULT NULL COMMENT '马力',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `axleNumber` int(11) DEFAULT NULL COMMENT '车轴数',
  `tagTime` date DEFAULT NULL COMMENT '上牌时间',
  `plateNumber` varchar(20) DEFAULT NULL COMMENT '车牌号',
  `fleetid` int(11) DEFAULT NULL COMMENT '引用车队ID',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='车辆详情表';

-- ----------------------------
-- Records of c_track
-- ----------------------------

-- ----------------------------
-- Table structure for d_daler
-- ----------------------------
DROP TABLE IF EXISTS `d_daler`;
CREATE TABLE `d_daler` (
  `id` varchar(36) NOT NULL COMMENT '经销商详情ID',
  `logisticsld` varchar(36) NOT NULL COMMENT '物流园ID',
  `idcard` varchar(25) NOT NULL COMMENT '身份证ID',
  `idcardImg` varchar(255) NOT NULL COMMENT '身份证图片',
  `businessLicenseId` varchar(255) DEFAULT NULL COMMENT '营业执照编码',
  `businessLicenseImg` varchar(255) DEFAULT NULL COMMENT '营业执照图片',
  `taxlicenseId` varchar(255) DEFAULT NULL COMMENT '税务登记编码',
  `taxlicenseImg` varchar(255) DEFAULT NULL COMMENT '税务登记图片',
  `seal` varchar(255) DEFAULT NULL COMMENT '公章图片',
  `areaId` varchar(36) DEFAULT NULL COMMENT '区域ID',
  `address` varchar(255) DEFAULT NULL COMMENT '经销商地址',
  `status` bit(1) DEFAULT NULL COMMENT '状态：1启用、0关闭',
  `type` bit(1) DEFAULT NULL COMMENT '经销商类型1、商家；2、个人',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='经销商详情表';

-- ----------------------------
-- Records of d_daler
-- ----------------------------

-- ----------------------------
-- Table structure for d_daler_user
-- ----------------------------
DROP TABLE IF EXISTS `d_daler_user`;
CREATE TABLE `d_daler_user` (
  `id` varchar(36) NOT NULL COMMENT '经销商用户ID',
  `userName` varchar(50) DEFAULT NULL COMMENT '用户ID',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `realName` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(18) DEFAULT NULL COMMENT '联系电话',
  `dealerId` varchar(36) DEFAULT NULL COMMENT '经销商ID',
  `roleId` varchar(36) DEFAULT NULL COMMENT '角色ID',
  `status` bit(1) DEFAULT NULL COMMENT '1启用、0关闭',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='经销商用户表';

-- ----------------------------
-- Records of d_daler_user
-- ----------------------------

-- ----------------------------
-- Table structure for s_dept
-- ----------------------------
DROP TABLE IF EXISTS `s_dept`;
CREATE TABLE `s_dept` (
  `id` varchar(32) NOT NULL,
  `leaf` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pid` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of s_dept
-- ----------------------------

-- ----------------------------
-- Table structure for s_menu
-- ----------------------------
DROP TABLE IF EXISTS `s_menu`;
CREATE TABLE `s_menu` (
  `id` varchar(36) NOT NULL,
  `text` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `iconCls` varchar(20) DEFAULT NULL COMMENT '图标',
  `pid` varchar(36) DEFAULT NULL COMMENT '父级ID',
  `expanded` int(4) DEFAULT NULL COMMENT '是否展开',
  `leaf` int(4) DEFAULT NULL COMMENT '是否是叶子',
  `iframe` int(4) DEFAULT NULL COMMENT '是否框架显示',
  `resourceId` varchar(36) DEFAULT NULL COMMENT '资源文件ID',
  `soft` int(11) DEFAULT NULL COMMENT '排序',
  `type` int(4) DEFAULT NULL COMMENT '菜单类型，1为模块，2为菜单，3为操作',
  `param` varchar(100) DEFAULT NULL COMMENT 'url参数',
  `state` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `s_menu_ibfk_1` (`resourceId`),
  KEY `pid` (`pid`),
  CONSTRAINT `s_menu_ibfk_1` FOREIGN KEY (`resourceId`) REFERENCES `s_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of s_menu
-- ----------------------------
INSERT INTO `s_menu` VALUES ('0', '系统菜单', null, '-1', null, null, null, '211dc8b3-c451-48f1-831d-b81a502afdbb', null, null, null, 'closed');
INSERT INTO `s_menu` VALUES ('053eb6e8-f65d-4bf2-bf20-f884015f56ca', '角色管理', 'icon-006', '1', null, '1', null, '373b75aa-e880-4ac8-a47c-bdba0b3d116c', '12', '2', null, null);
INSERT INTO `s_menu` VALUES ('1', '系统管理', 'fa fa-wrench', '0', null, null, null, '211dc8b3-c451-48f1-831d-b81a502afdbb', '10', '1', '', 'closed');
INSERT INTO `s_menu` VALUES ('3', '用户管理', 'fa fa-user', '1', '1', null, '0', '71550322-8266-42b6-9b7a-0046252e38bc', '11', '2', null, 'closed');
INSERT INTO `s_menu` VALUES ('402880e6531caa7901531caca47f0001', '数据库表列管理', null, '40289781527cb97c01527cbd0d980000', null, '1', null, '402880e6531caa7901531cab9be60000', '2', '2', null, null);
INSERT INTO `s_menu` VALUES ('40289781527cb97c01527cbd0d980000', '数据库管理', 'fa fa-database', '0', null, null, null, '211dc8b3-c451-48f1-831d-b81a502afdbb', '22', '1', null, 'closed');
INSERT INTO `s_menu` VALUES ('40289781527cb97c01527cbd52ed0001', '数据库表管理', '', '40289781527cb97c01527cbd0d980000', null, '1', null, '402880e75312385b0153123abba80000', null, '2', null, null);
INSERT INTO `s_menu` VALUES ('4028978152ee41850152ee4379730001', '请假管理', null, '1', null, '1', null, '4028978152ee41850152ee42bcc30000', null, '2', null, null);
INSERT INTO `s_menu` VALUES ('6', '资源管理', 'fa fa-unlink', '1', '0', null, '0', 'c85c3f37-1f53-4353-965e-d61b942f23cc', '16', '2', '', '');
INSERT INTO `s_menu` VALUES ('7', '菜单管理', 'icon-008', '1', null, '1', null, '52447b0f-6409-4185-9db0-130e213189f1', '14', '2', null, null);

-- ----------------------------
-- Table structure for s_resource
-- ----------------------------
DROP TABLE IF EXISTS `s_resource`;
CREATE TABLE `s_resource` (
  `id` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  `url` varchar(200) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `isLog` tinyint(4) DEFAULT NULL COMMENT '是否记录操作',
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
-- Records of s_resource
-- ----------------------------
INSERT INTO `s_resource` VALUES ('211dc8b3-c451-48f1-831d-b81a502afdbb', ' 空操作', '#', '非链接菜单，一般用于父节点。', null);
INSERT INTO `s_resource` VALUES ('2c92fd81520b84a901520b95eb7c0006', '月考勤分析', '/system/checkinginanalyze/view/index.do?type=month', '', '1');
INSERT INTO `s_resource` VALUES ('2c92fd81520b84a901520b96e6970008', '季考勤分析', '/system/checkinginanalyze/view/index.do?type=season', '', '1');
INSERT INTO `s_resource` VALUES ('2c92fd81520b84a901520b971db10009', '年考勤分析', '/system/checkinginanalyze/view/index.do?type=year', '', '1');
INSERT INTO `s_resource` VALUES ('373b75aa-e880-4ac8-a47c-bdba0b3d116c', '角色管理 - 列表', '/system/role/view/index.do', '', '0');
INSERT INTO `s_resource` VALUES ('402880e6531caa7901531cab9be60000', '数据库表列管理-列表', '/system/column/view/index.do', '', '0');
INSERT INTO `s_resource` VALUES ('402880e75312385b0153123abba80000', '数据库表管理-列表', '/system/table/view/index.do', '', '0');
INSERT INTO `s_resource` VALUES ('402881065214a5fd015214aa684f0000', '考勤报表', '/report/monthly/view/index.do', '', '0');
INSERT INTO `s_resource` VALUES ('402881065214a5fd015214b0715e0002', '原始考勤记录', '/report/checkinout/view/index.do', '', '0');
INSERT INTO `s_resource` VALUES ('40288106523dfdab01523dff46bf0001', '考勤统计', '/report/statistics/view/index.do', '', '0');
INSERT INTO `s_resource` VALUES ('4028818c520b864701520b87cd260001', '考勤参数设置', '/system/positions/view/setparameter.do', '', '0');
INSERT INTO `s_resource` VALUES ('4028818c5214b738015214b9569c0001', '上下班时间管理-列表', '/system/positions/view/workdate.do', '', '0');
INSERT INTO `s_resource` VALUES ('4028818c522ebe1b01522ebfe2470000', '出勤率统计-图表', '/report/attendance/view/index.do', '', '0');
INSERT INTO `s_resource` VALUES ('4028818c523e1ff001523e2b10ab0000', '出勤率排名-列表', '/report/attendance/view/orderindex.do', '', '0');
INSERT INTO `s_resource` VALUES ('4028978152ee41850152ee42bcc30000', '请假管理-列表', '/system/leave/view/index.do', '', '0');
INSERT INTO `s_resource` VALUES ('4b667aa8-a797-4a53-a544-55aa86bf4984', '用户日志 - 列表', '/system/userlog/view/index.do', '', null);
INSERT INTO `s_resource` VALUES ('52447b0f-6409-4185-9db0-130e213189f1', '菜单管理 - 列表', '/system/menu/view/index.do', '', '0');
INSERT INTO `s_resource` VALUES ('5270154b-86a7-4688-8b33-6c199772d9bb', '用户管理 - 授权', '/system/user/acc.do', '', null);
INSERT INTO `s_resource` VALUES ('547eae1a-f372-4a4a-a1ef-731f58173d9f', '测试管理', '/test/test/view/index.do', '', null);
INSERT INTO `s_resource` VALUES ('71550322-8266-42b6-9b7a-0046252e38bc', '用户管理 - 列表', '/system/user/view/index.do', '', '0');
INSERT INTO `s_resource` VALUES ('c85c3f37-1f53-4353-965e-d61b942f23cc', '资源管理 - 列表', '/system/resource/view/index.do', '', '0');

-- ----------------------------
-- Table structure for s_role
-- ----------------------------
DROP TABLE IF EXISTS `s_role`;
CREATE TABLE `s_role` (
  `id` varchar(36) NOT NULL,
  `name` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态，0正常，1停用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of s_role
-- ----------------------------
INSERT INTO `s_role` VALUES ('45265734-0b9a-4dd3-876e-a1a6abac986b', '部门经理', '1');
INSERT INTO `s_role` VALUES ('d33fd268-8bf5-4deb-a809-b4e39f2e0d3a', '管理员', '1');

-- ----------------------------
-- Table structure for s_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `s_role_menu`;
CREATE TABLE `s_role_menu` (
  `roleId` varchar(36) DEFAULT NULL COMMENT '角色ID',
  `menuId` varchar(36) DEFAULT NULL,
  KEY `s_role_menu_ibfk_2` (`roleId`),
  KEY `s_role_menu_ibfk_1` (`menuId`),
  CONSTRAINT `s_role_menu_ibfk_1` FOREIGN KEY (`menuId`) REFERENCES `s_menu` (`id`) ON DELETE CASCADE,
  CONSTRAINT `s_role_menu_ibfk_2` FOREIGN KEY (`roleId`) REFERENCES `s_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与角色关联表';

-- ----------------------------
-- Records of s_role_menu
-- ----------------------------
INSERT INTO `s_role_menu` VALUES ('45265734-0b9a-4dd3-876e-a1a6abac986b', '053eb6e8-f65d-4bf2-bf20-f884015f56ca');
INSERT INTO `s_role_menu` VALUES ('45265734-0b9a-4dd3-876e-a1a6abac986b', '7');
INSERT INTO `s_role_menu` VALUES ('45265734-0b9a-4dd3-876e-a1a6abac986b', '6');
INSERT INTO `s_role_menu` VALUES ('d33fd268-8bf5-4deb-a809-b4e39f2e0d3a', '4028978152ee41850152ee4379730001');
INSERT INTO `s_role_menu` VALUES ('d33fd268-8bf5-4deb-a809-b4e39f2e0d3a', '3');
INSERT INTO `s_role_menu` VALUES ('d33fd268-8bf5-4deb-a809-b4e39f2e0d3a', '053eb6e8-f65d-4bf2-bf20-f884015f56ca');
INSERT INTO `s_role_menu` VALUES ('d33fd268-8bf5-4deb-a809-b4e39f2e0d3a', '7');
INSERT INTO `s_role_menu` VALUES ('d33fd268-8bf5-4deb-a809-b4e39f2e0d3a', '6');
INSERT INTO `s_role_menu` VALUES ('d33fd268-8bf5-4deb-a809-b4e39f2e0d3a', '40289781527cb97c01527cbd52ed0001');
INSERT INTO `s_role_menu` VALUES ('d33fd268-8bf5-4deb-a809-b4e39f2e0d3a', '1');
INSERT INTO `s_role_menu` VALUES ('d33fd268-8bf5-4deb-a809-b4e39f2e0d3a', '402880e6531caa7901531caca47f0001');
INSERT INTO `s_role_menu` VALUES ('d33fd268-8bf5-4deb-a809-b4e39f2e0d3a', '40289781527cb97c01527cbd0d980000');

-- ----------------------------
-- Table structure for s_user
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user` (
  `id` varchar(36) NOT NULL COMMENT '用户ID',
  `account` varchar(50) NOT NULL COMMENT '帐号',
  `name` varchar(150) NOT NULL COMMENT '姓名',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `pass` varchar(36) NOT NULL COMMENT '密码',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `status` int(4) DEFAULT NULL COMMENT '状态，0正常，1停用，2注销',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `roleNames` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `areaId` varchar(255) DEFAULT NULL COMMENT '区域ID',
  `areaName` varchar(255) DEFAULT NULL COMMENT '区域名称',
  `user_start` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of s_user
-- ----------------------------
INSERT INTO `s_user` VALUES ('2c92fd8151f6c15c0151f6d88d7e0000', '44444', '44', null, 'DBC4D84BFCFE2284BA11BEFFB853A8C4', '', '0', '2015-12-31 15:02:45', null, null, null, null, null);
INSERT INTO `s_user` VALUES ('2c92fd8151f6c15c0151f6d8ca5f0002', '333', '33', null, '182BE0C5CDCD5072BB1864CDEE4D3D6E', '', '0', '2015-12-31 15:03:01', null, null, null, null, null);
INSERT INTO `s_user` VALUES ('2c92fd8151f6c15c0151f6d8df2a0003', '44', '44', null, 'F7177163C833DFF4B38FC8D2872F1EC6', '', '0', '2015-12-31 15:03:06', null, null, null, null, null);
INSERT INTO `s_user` VALUES ('2c92fd8151f6c15c0151f6d8feab0004', '55', '55', '55', 'B53B3A3D6AB90CE0268229151C9BDE11', '', '0', '2015-12-31 15:03:14', null, null, null, null, null);
INSERT INTO `s_user` VALUES ('2c92fd8151f6c15c0151f6d919260005', '66', '66', null, '3295C76ACBF4CAAED33C36B1B5FC2CB1', '', '0', '2015-12-31 15:03:21', null, null, null, null, null);
INSERT INTO `s_user` VALUES ('2c92fd81520b84a901520b8912320000', 'admin', 'admin', '1', 'C4CA4238A0B923820DCC509A6F75849B', '12345678911', '0', '2016-01-04 15:27:58', '2016-01-15 16:38:05', '管理员', '1,2', '区域名称,金阳高新区', '');
INSERT INTO `s_user` VALUES ('88837d08-a02d-454f-8840-1930c05c44a6', 'X0001', 'xx', null, 'C4CA4238A0B923820DCC509A6F75849B', '', '1', '2013-04-09 16:17:29', '2013-04-23 10:45:51', null, null, null, null);
INSERT INTO `s_user` VALUES ('88aa9964-5e06-4a19-99c1-86f49a0b4b14', 'xl', '小龙', null, 'C4CA4238A0B923820DCC509A6F75849B', '', '0', '2013-04-09 16:18:06', '2015-12-29 16:01:37', '管理员', null, null, null);
INSERT INTO `s_user` VALUES ('d3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '1', '1', null, 'C4CA4238A0B923820DCC509A6F75849B', '', '0', '2012-08-31 11:59:06', '2013-04-03 11:37:20', null, null, null, null);
INSERT INTO `s_user` VALUES ('f7e56da3-81f8-4704-bef6-dbe89e717841', 'X00051', 'TT', null, 'C4CA4238A0B923820DCC509A6F75849B', '', '0', '2013-04-09 16:18:53', '2015-12-31 09:26:10', '部门经理', null, null, null);

-- ----------------------------
-- Table structure for s_user_log
-- ----------------------------
DROP TABLE IF EXISTS `s_user_log`;
CREATE TABLE `s_user_log` (
  `id` varchar(36) NOT NULL,
  `userId` varchar(36) DEFAULT NULL COMMENT '用户ID',
  `resourceId` varchar(36) DEFAULT NULL COMMENT '资源ID',
  `date` datetime DEFAULT NULL COMMENT '操作时间',
  `ip` varchar(20) DEFAULT NULL COMMENT 'IP地址',
  `account` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户日志表';

-- ----------------------------
-- Records of s_user_log
-- ----------------------------
INSERT INTO `s_user_log` VALUES ('02cec126-3aed-44b2-869a-169af0a704bd', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-01-25 13:04:11', null, null);
INSERT INTO `s_user_log` VALUES ('02dd77b0-4b25-4467-9103-bc9daf07d852', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:30:13', null, null);
INSERT INTO `s_user_log` VALUES ('0ebc9223-fa72-44c8-85b6-f13da51d534b', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-28 11:06:08', null, null);
INSERT INTO `s_user_log` VALUES ('190c3567-c887-43ba-ad56-4b5e81c970da', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:37:33', null, null);
INSERT INTO `s_user_log` VALUES ('1ac31440-defc-459d-9f53-70c5a0fd0bd4', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:29:56', null, null);
INSERT INTO `s_user_log` VALUES ('2015456a-d26c-418f-8e35-ae8b79a58be6', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:54:08', null, null);
INSERT INTO `s_user_log` VALUES ('445755ee-1aa2-403a-8432-9ec3e37b5d73', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:25:22', null, null);
INSERT INTO `s_user_log` VALUES ('4ddba388-0461-49b9-8dee-4e496848e204', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:47:09', null, null);
INSERT INTO `s_user_log` VALUES ('53c4a8cb-24ee-4528-ba00-c5e11a9c519d', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:51:33', null, null);
INSERT INTO `s_user_log` VALUES ('5faedb01-57f8-424a-abe3-5439d88d3151', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:25:53', null, null);
INSERT INTO `s_user_log` VALUES ('619de51e-8036-495b-968f-4e44d25357e0', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:48:42', null, null);
INSERT INTO `s_user_log` VALUES ('777bed9b-ad1d-4369-90c6-19c1a267b2de', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:55:45', null, null);
INSERT INTO `s_user_log` VALUES ('796a49ad-f289-49df-b297-3eb8d164e615', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:28:18', null, null);
INSERT INTO `s_user_log` VALUES ('86ad5b31-204b-448e-964f-21558a7213dc', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:38:00', null, null);
INSERT INTO `s_user_log` VALUES ('896acc22-8f1c-4c8d-acdf-2270bf1a684c', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-27 09:01:20', null, null);
INSERT INTO `s_user_log` VALUES ('904806b2-e1df-467a-8e18-9c60bfbb8fde', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:46:55', null, null);
INSERT INTO `s_user_log` VALUES ('965a9d78-95c8-4ddf-9974-bc2853d70161', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:52:33', null, null);
INSERT INTO `s_user_log` VALUES ('9bec6f1c-0c7a-4a75-a5c4-3e78b24956f7', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:47:30', null, null);
INSERT INTO `s_user_log` VALUES ('a5539f05-d247-4bb3-bc4a-88d634107597', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:37:50', null, null);
INSERT INTO `s_user_log` VALUES ('a6b84d5e-42a4-4c62-9fa9-cf5983e17bf8', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:55:15', null, null);
INSERT INTO `s_user_log` VALUES ('a7927d9e-150e-436c-b6a9-eef1e147e966', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:57:09', null, null);
INSERT INTO `s_user_log` VALUES ('ae472098-0166-4f6d-878e-56b2e253ede9', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-27 09:00:44', null, null);
INSERT INTO `s_user_log` VALUES ('aefe45aa-fd60-49e9-87b6-7a75e1fa2fc8', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:38:10', null, null);
INSERT INTO `s_user_log` VALUES ('c788a53b-8df1-44ce-814b-b0421309b482', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:48:33', null, null);
INSERT INTO `s_user_log` VALUES ('ce9f4ad0-0b06-409b-bcd9-6970cdfec755', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:29:21', null, null);
INSERT INTO `s_user_log` VALUES ('d5cb811d-6c41-42d1-ad3c-45b1e67d10bb', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-01-25 13:36:46', null, null);
INSERT INTO `s_user_log` VALUES ('d9bcdc7d-fabb-405e-960f-aa5f4481cd26', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:34:28', null, null);
INSERT INTO `s_user_log` VALUES ('daa4df94-67ed-427a-9bff-d720a882103a', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-28 13:47:50', null, null);
INSERT INTO `s_user_log` VALUES ('e247d1a6-81bf-4ea0-955a-dffd465b805b', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-27 09:55:40', null, null);
INSERT INTO `s_user_log` VALUES ('e3462b7c-0d55-40d4-bbeb-25903eb3e6c7', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-28 14:20:31', null, null);
INSERT INTO `s_user_log` VALUES ('ea1f2479-cd78-4f9c-a070-b2df8ffab229', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-01-25 13:31:32', null, null);
INSERT INTO `s_user_log` VALUES ('edfcaf4a-b5dc-45f6-b746-b167a1073bf3', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:53:11', null, null);
INSERT INTO `s_user_log` VALUES ('f14edee1-4b31-4b85-995e-b35d2ad604d8', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:26:34', null, null);
INSERT INTO `s_user_log` VALUES ('f618efbe-e629-4912-95c0-80082540c04b', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-01-25 13:04:02', null, null);
INSERT INTO `s_user_log` VALUES ('fdff6391-32c7-414d-a42b-98c7cb370984', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-26 18:50:13', null, null);
INSERT INTO `s_user_log` VALUES ('fed20cd8-9127-4ac8-a0f8-b25fe6c587a7', 'd3f7f9ea-f621-44df-b97b-42bfe7f5ed78', '52447b0f-6409-4185-9db0-130e213189f1', '2013-03-27 09:56:32', null, null);

-- ----------------------------
-- Table structure for s_user_role
-- ----------------------------
DROP TABLE IF EXISTS `s_user_role`;
CREATE TABLE `s_user_role` (
  `userId` varchar(36) DEFAULT NULL COMMENT '用户ID',
  `roleId` varchar(36) DEFAULT NULL COMMENT '角色ID',
  KEY `roleId` (`roleId`),
  KEY `userId` (`userId`),
  CONSTRAINT `s_user_role_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `s_role` (`id`),
  CONSTRAINT `s_user_role_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `s_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与角色关联表';

-- ----------------------------
-- Records of s_user_role
-- ----------------------------
INSERT INTO `s_user_role` VALUES ('d3f7f9ea-f621-44df-b97b-42bfe7f5ed78', 'd33fd268-8bf5-4deb-a809-b4e39f2e0d3a');
INSERT INTO `s_user_role` VALUES ('88aa9964-5e06-4a19-99c1-86f49a0b4b14', 'd33fd268-8bf5-4deb-a809-b4e39f2e0d3a');
INSERT INTO `s_user_role` VALUES ('f7e56da3-81f8-4704-bef6-dbe89e717841', '45265734-0b9a-4dd3-876e-a1a6abac986b');
INSERT INTO `s_user_role` VALUES ('2c92fd81520b84a901520b8912320000', 'd33fd268-8bf5-4deb-a809-b4e39f2e0d3a');

-- ----------------------------
-- Table structure for t_column
-- ----------------------------
DROP TABLE IF EXISTS `t_column`;
CREATE TABLE `t_column` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `t_column_name` varchar(255) DEFAULT NULL COMMENT '列英文名',
  `t_column_comment` varchar(255) DEFAULT NULL COMMENT '列中文名',
  `t_column_data_type` varchar(255) DEFAULT NULL COMMENT '列数据类型',
  `t_column_data_length` int(11) DEFAULT NULL COMMENT '列数据类型长度',
  `t_is_nullable` varchar(50) DEFAULT NULL COMMENT '是否为空',
  `t_column_default` varchar(255) DEFAULT NULL COMMENT '列默认值',
  `t_column_key` varchar(255) DEFAULT NULL COMMENT '是主键还是外键',
  `t_column_type` varchar(255) DEFAULT NULL COMMENT '数据类型',
  `t_table_id` varchar(36) DEFAULT NULL COMMENT '所属数据库表，T_Table-id',
  PRIMARY KEY (`id`),
  KEY `FK_d8ki83eotdxyiunowgevl10i4` (`t_table_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='表-列表';

-- ----------------------------
-- Records of t_column
-- ----------------------------

-- ----------------------------
-- Table structure for t_table
-- ----------------------------
DROP TABLE IF EXISTS `t_table`;
CREATE TABLE `t_table` (
  `id` varchar(36) NOT NULL COMMENT '表ID',
  `s_table_name` varchar(255) NOT NULL COMMENT '表明',
  `s_table_comment` varchar(255) NOT NULL COMMENT '表注释',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='系统表明表';

-- ----------------------------
-- Records of t_table
-- ----------------------------
INSERT INTO `t_table` VALUES ('402880e6531ba6f201531c422bae0014', 'b_area', '区域表');
INSERT INTO `t_table` VALUES ('402880e6531ba6f201531c422bae0015', 'b_logistics', '物流园表');
INSERT INTO `t_table` VALUES ('402880e6531ba6f201531c422bae0016', 'c_fleet', '车队详情表');
INSERT INTO `t_table` VALUES ('402880e6531ba6f201531c422bae0017', 'c_fleet_user', '车队用户表');
INSERT INTO `t_table` VALUES ('402880e6531ba6f201531c422bae0018', 'c_track', '车辆详情表');
INSERT INTO `t_table` VALUES ('402880e6531ba6f201531c422bae0019', 'd_daler', '经销商详情表');
INSERT INTO `t_table` VALUES ('402880e6531ba6f201531c422baf001a', 'd_daler_user', '经销商用户表');
INSERT INTO `t_table` VALUES ('402880e6531ba6f201531c422baf001b', 's_dept', '部门表');
INSERT INTO `t_table` VALUES ('402880e6531ba6f201531c422baf001c', 's_menu', '菜单表');
INSERT INTO `t_table` VALUES ('402880e6531ba6f201531c422baf001d', 's_resource', '资源表');
INSERT INTO `t_table` VALUES ('402880e6531ba6f201531c422baf001e', 's_role', '角色表');
INSERT INTO `t_table` VALUES ('402880e6531ba6f201531c422baf001f', 's_role_menu', '用户与角色关联表');
INSERT INTO `t_table` VALUES ('402880e6531ba6f201531c422baf0020', 's_user', '用户信息表');
INSERT INTO `t_table` VALUES ('402880e6531ba6f201531c422baf0021', 's_user_log', '用户日志表');
INSERT INTO `t_table` VALUES ('402880e6531ba6f201531c422baf0022', 's_user_role', '用户与角色关联表');
INSERT INTO `t_table` VALUES ('402880e6531ba6f201531c422baf0023', 't_column', '表-列表');
INSERT INTO `t_table` VALUES ('402880e6531ba6f201531c422baf0024', 't_table', '系统表明表');
INSERT INTO `t_table` VALUES ('402880e6531ba6f201531c422baf0025', 'w_wfleave', '');

-- ----------------------------
-- Table structure for w_wfleave
-- ----------------------------
DROP TABLE IF EXISTS `w_wfleave`;
CREATE TABLE `w_wfleave` (
  `id` varchar(50) NOT NULL,
  `leaveid` varchar(50) DEFAULT NULL COMMENT '请假人ID',
  `name` varchar(255) DEFAULT NULL COMMENT '请假人姓名',
  `leaveStartDate` date DEFAULT NULL COMMENT '开始请假时间',
  `leaveEndDate` date DEFAULT NULL COMMENT '结束请假时间',
  `leaveCount` int(11) DEFAULT NULL COMMENT '请假天数',
  `leaveReason` varchar(255) DEFAULT NULL COMMENT '请假内容',
  `instanceid` varchar(255) DEFAULT NULL COMMENT '流程实例ID',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of w_wfleave
-- ----------------------------
