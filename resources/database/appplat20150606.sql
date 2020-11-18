/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : appplat

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2015-06-06 15:26:43
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `node`
-- ----------------------------
DROP TABLE IF EXISTS `node`;
CREATE TABLE `node` (
  `NODE_ID` varchar(40) NOT NULL,
  `NODE_NAME` varchar(1000) DEFAULT NULL,
  `NODE_TYPE` varchar(40) DEFAULT NULL,
  `NODE_ORDER` int(11) DEFAULT NULL,
  `PARENT_ID` varchar(40) DEFAULT NULL,
  `ROOT_ID` varchar(40) DEFAULT NULL,
  `IS_ROOT` tinyint(1) DEFAULT NULL,
  `IS_LEAF` tinyint(1) DEFAULT NULL,
  `DATA_ID` varchar(40) DEFAULT NULL,
  `NODE_FILE_PATH` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`NODE_ID`),
  KEY `FK_Reference_9` (`DATA_ID`),
  KEY `FK_Reference_7` (`ROOT_ID`),
  KEY `FK_Reference_8` (`PARENT_ID`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`ROOT_ID`) REFERENCES `node` (`NODE_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`PARENT_ID`) REFERENCES `node` (`NODE_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of node
-- ----------------------------
INSERT INTO node VALUES ('solution08477cd4411e48c49b53603', '方案1', 'Solution', '0', 'task1234408fffa864ec4f0b812ae59', 'task1234408fffa864ec4f0b812ae59', '0', '0', null, 'task1234408fffa864ec4f0b812ae59/solution08477cd4411e48c49b53603');
INSERT INTO node VALUES ('solution72299b02ec95492594e62ae', '方案1', 'Solution', '0', 'task12343d236c37b77f4faeaa63372', 'task12343d236c37b77f4faeaa63372', '0', '0', null, 'task12343d236c37b77f4faeaa63372/solution72299b02ec95492594e62ae');
INSERT INTO node VALUES ('task12343d236c37b77f4faeaa63372', '任务一', 'Task', '0', 'task12343d236c37b77f4faeaa63372', 'task12343d236c37b77f4faeaa63372', '1', '0', '402883e54dc78ff0014dc7911ded0000', 'task12343d236c37b77f4faeaa63372');
INSERT INTO node VALUES ('task1234408fffa864ec4f0b812ae59', '杜鹏宇任务', 'Task', '0', 'task1234408fffa864ec4f0b812ae59', 'task1234408fffa864ec4f0b812ae59', '1', '0', '402883e54dc7ad52014dc7ae74bb0004', 'task1234408fffa864ec4f0b812ae59');
INSERT INTO node VALUES ('task12345c62a374cfce4516a9fa247', '任务1', 'Task', '0', 'task12345c62a374cfce4516a9fa247', 'task12345c62a374cfce4516a9fa247', '1', '0', '402883e54dc7ad52014dc7adffae0002', '333bbbe18cd23317f008f333/CoverAnalysis/task12345c62a374cfce4516a9fa247');
INSERT INTO node VALUES ('task123470e74a028f784ce1a988c54', '任务1', 'Task', '0', 'task123470e74a028f784ce1a988c54', 'task123470e74a028f784ce1a988c54', '1', '0', '402883e54dc7ad52014dc7ae14240003', '333bbbe18cd23317f008f333/DataTransAnalysis/task123470e74a028f784ce1a988c54');
INSERT INTO node VALUES ('task1234eae0d581e24f4df19bbc706', '任务1', 'Task', '0', 'task1234eae0d581e24f4df19bbc706', 'task1234eae0d581e24f4df19bbc706', '0', '0', '402883e54dc7ad52014dc7ada2360000', '');

-- ----------------------------
-- Table structure for `personal_task`
-- ----------------------------
DROP TABLE IF EXISTS `personal_task`;
CREATE TABLE `personal_task` (
  `PER_TASK_ID` varchar(40) NOT NULL,
  `PER_TASK_NAME` varchar(1000) DEFAULT NULL,
  `TASK_TYPE` varchar(40) DEFAULT NULL,
  `RECENTLY_MODIFIED` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `IS_SAVED` tinyint(1) DEFAULT NULL,
  `USER_ID` varchar(40) DEFAULT NULL,
  `TASK_DIR_PATH` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`PER_TASK_ID`),
  KEY `FK_Reference_9` (`USER_ID`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of personal_task
-- ----------------------------
INSERT INTO personal_task VALUES ('402883e54dc7ad52014dc7ada2360000', '任务1', '轨道设计', '2015-06-06 15:02:35', '1', '333bbbe18cd23317f008f333', '333bbbe18cd23317f008f333/OrbitDesign/task1234eae0d581e24f4df19bbc706');
INSERT INTO personal_task VALUES ('402883e54dc7ad52014dc7adebaf0001', '111', '结构设计', '2015-06-06 15:02:53', '1', '333bbbe18cd23317f008f333', '333bbbe18cd23317f008f333/StructDesign/111');
INSERT INTO personal_task VALUES ('402883e54dc7ad52014dc7adffae0002', '任务1', '覆盖分析', '2015-06-06 15:02:58', '0', '333bbbe18cd23317f008f333', '333bbbe18cd23317f008f333/CoverAnalysis/task12345c62a374cfce4516a9fa247');
INSERT INTO personal_task VALUES ('402883e54dc7ad52014dc7ae14240003', '任务1', '数传分析', '2015-06-06 15:03:53', '0', '333bbbe18cd23317f008f333', '333bbbe18cd23317f008f333/DataTransAnalysis/task123470e74a028f784ce1a988c54');

-- ----------------------------
-- Table structure for `pre_task_user`
-- ----------------------------
DROP TABLE IF EXISTS `pre_task_user`;
CREATE TABLE `pre_task_user` (
  `PRE_TASK_USER_ID` varchar(40) NOT NULL,
  `TASK_ID` varchar(40) NOT NULL,
  `USER_MAILBOX` varchar(100) DEFAULT NULL,
  `IS_PASSED` int(11) DEFAULT NULL,
  PRIMARY KEY (`PRE_TASK_USER_ID`),
  KEY `FK_Reference_5` (`TASK_ID`),
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`TASK_ID`) REFERENCES `task` (`TASK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pre_task_user
-- ----------------------------

-- ----------------------------
-- Table structure for `process`
-- ----------------------------
DROP TABLE IF EXISTS `process`;
CREATE TABLE `process` (
  `PROCESS_ID` varchar(40) NOT NULL,
  `PROCESS_NAME` varchar(1000) NOT NULL,
  PRIMARY KEY (`PROCESS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of process
-- ----------------------------
INSERT INTO process VALUES ('402883e54dc30d07014dc32b05c80022', '轨道设计');
INSERT INTO process VALUES ('402883e54dc30d07014dc32b05c80023', '结构设计');
INSERT INTO process VALUES ('402883e54dc30d07014dc32b05c80024', '覆盖分析');
INSERT INTO process VALUES ('402883e54dc30d07014dc32b05c80025', '数传分析');

-- ----------------------------
-- Table structure for `task`
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `TASK_ID` varchar(40) NOT NULL,
  `TASK_NAME` varchar(1000) NOT NULL,
  `START_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `SIMULATION_START_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `SIMULATION_END_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `DESCRIPTION` text,
  `STEP` int(11) DEFAULT NULL,
  `RECENTLY_MODIFIED` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`TASK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO task VALUES ('402883e54dc78ff0014dc7911ded0000', '任务一', '2015-06-02 14:31:02', '2015-06-01 14:30:58', '2015-07-03 14:31:01', '任务一', '111', '2015-06-02 14:31:02');
INSERT INTO task VALUES ('402883e54dc7ad52014dc7ae74bb0004', '杜鹏宇任务', '2015-06-08 15:03:24', '2015-06-01 15:03:20', '2015-07-04 15:03:22', '杜鹏宇任务', '111', '2015-06-08 15:03:24');

-- ----------------------------
-- Table structure for `task_process`
-- ----------------------------
DROP TABLE IF EXISTS `task_process`;
CREATE TABLE `task_process` (
  `TASK_PROCESS_ID` varchar(40) NOT NULL,
  `TASK_ID` varchar(40) DEFAULT NULL,
  `PROCESS_ID` varchar(40) DEFAULT NULL,
  `IS_SUBMIT` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`TASK_PROCESS_ID`),
  KEY `FK_Reference_6` (`PROCESS_ID`),
  KEY `FK_Reference_4` (`TASK_ID`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`TASK_ID`) REFERENCES `task` (`TASK_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`PROCESS_ID`) REFERENCES `process` (`PROCESS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_process
-- ----------------------------
INSERT INTO task_process VALUES ('402883e54dc78ff0014dc7911df20001', '402883e54dc78ff0014dc7911ded0000', '402883e54dc30d07014dc32b05c80022', '0');
INSERT INTO task_process VALUES ('402883e54dc78ff0014dc7911df20002', '402883e54dc78ff0014dc7911ded0000', '402883e54dc30d07014dc32b05c80023', '0');
INSERT INTO task_process VALUES ('402883e54dc78ff0014dc7911df20003', '402883e54dc78ff0014dc7911ded0000', '402883e54dc30d07014dc32b05c80024', '0');
INSERT INTO task_process VALUES ('402883e54dc78ff0014dc7911df20004', '402883e54dc78ff0014dc7911ded0000', '402883e54dc30d07014dc32b05c80025', '0');
INSERT INTO task_process VALUES ('402883e54dc7ad52014dc7ae74c70005', '402883e54dc7ad52014dc7ae74bb0004', '402883e54dc30d07014dc32b05c80022', '0');
INSERT INTO task_process VALUES ('402883e54dc7ad52014dc7ae74c70006', '402883e54dc7ad52014dc7ae74bb0004', '402883e54dc30d07014dc32b05c80023', '0');
INSERT INTO task_process VALUES ('402883e54dc7ad52014dc7ae74c70007', '402883e54dc7ad52014dc7ae74bb0004', '402883e54dc30d07014dc32b05c80024', '0');
INSERT INTO task_process VALUES ('402883e54dc7ad52014dc7ae74c70008', '402883e54dc7ad52014dc7ae74bb0004', '402883e54dc30d07014dc32b05c80025', '0');

-- ----------------------------
-- Table structure for `task_user`
-- ----------------------------
DROP TABLE IF EXISTS `task_user`;
CREATE TABLE `task_user` (
  `TASK_USER_ID` varchar(40) NOT NULL,
  `TASK_ID` varchar(40) DEFAULT NULL,
  `USER_ACCOUNT` varchar(40) DEFAULT NULL COMMENT '这里面放的是用户的账户',
  `IS_CREATER` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`TASK_USER_ID`),
  KEY `FK_Reference_2` (`TASK_ID`),
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`TASK_ID`) REFERENCES `task` (`TASK_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_user
-- ----------------------------
INSERT INTO task_user VALUES ('402883e54dc78ff0014dc7911e8b0007', '402883e54dc78ff0014dc7911ded0000', 'zhang', '1');
INSERT INTO task_user VALUES ('402883e54dc7ad52014dc7ae74df0009', '402883e54dc7ad52014dc7ae74bb0004', 'du', '1');
INSERT INTO task_user VALUES ('402883e54dc7ad52014dc7b056da000a', '402883e54dc78ff0014dc7911ded0000', 'du', '0');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `USER_ID` varchar(40) NOT NULL,
  `USER_ACCOUNT` varchar(40) DEFAULT NULL,
  `USER_DEPARTMENT` varchar(100) DEFAULT NULL,
  `USER_DOMAIN` varchar(40) DEFAULT NULL,
  `USER_MAILBOX` varchar(100) DEFAULT NULL,
  `USER_NAME` varchar(1000) DEFAULT NULL,
  `USER_PERMISSION` varchar(40) DEFAULT NULL,
  `USER_TELEPHONE` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO user VALUES ('111bbbe18cd23317f008f111', 'nie', null, 'cloud.vsso.ac.cn', null, '聂殿辉', null, null);
INSERT INTO user VALUES ('333bbbe18cd23317f008f333', 'du', null, 'cloud.vsso.ac.cn', null, '杜鹏宇', null, null);
INSERT INTO user VALUES ('534bbbe18cd23317f008f0b8', 'zhang', null, 'cloud.vsso.ac.cn', null, '张明浩', null, null);
