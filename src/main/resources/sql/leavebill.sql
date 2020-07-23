/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : activiti

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 23/07/2020 22:27:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for leavebill
-- ----------------------------
DROP TABLE IF EXISTS `leavebill`;
CREATE TABLE `leavebill`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `days` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '天数',
  `leavetime` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0 未提交 1审批中 2审批完成 3 已放弃',
  `userid` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of leavebill
-- ----------------------------
INSERT INTO `leavebill` VALUES (1, '1', '1', '1', '2020-07-23 21:31:55', '1', 13);
INSERT INTO `leavebill` VALUES (2, '请假', '回家', '3', '2020-07-23 00:00:00', '0', 13);

SET FOREIGN_KEY_CHECKS = 1;
