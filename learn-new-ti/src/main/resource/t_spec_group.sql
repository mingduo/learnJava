/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : newti

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2020-11-23 17:34:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_spec_group
-- ----------------------------
DROP TABLE IF EXISTS `t_spec_group`;
CREATE TABLE `t_spec_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `spg_id` int(11) NOT NULL COMMENT '品类编号',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '品类名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_spg_id` (`spg_id`) USING BTREE,
  UNIQUE KEY `unq_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_spec_group
-- ----------------------------
INSERT INTO `t_spec_group` VALUES ('1', '10001', '手机');
INSERT INTO `t_spec_group` VALUES ('2', '10002', '手机线');
INSERT INTO `t_spec_group` VALUES ('3', '10003', '手机电池');
INSERT INTO `t_spec_group` VALUES ('4', '11001', '液晶电视');
INSERT INTO `t_spec_group` VALUES ('5', '11002', '投影电视');
