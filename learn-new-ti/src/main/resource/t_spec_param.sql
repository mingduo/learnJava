/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : newti

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2020-11-23 17:34:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_spec_param
-- ----------------------------
DROP TABLE IF EXISTS `t_spec_param`;
CREATE TABLE `t_spec_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `spg_id` int(11) NOT NULL COMMENT '品类编号',
  `spp_id` int(11) NOT NULL COMMENT '参数编号',
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '参数名称',
  `numeric` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否为数字',
  `unit` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '单位',
  `generic` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否为通用',
  `searching` tinyint(1) DEFAULT '1' COMMENT '是否用于搜索',
  `segements` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '参数值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_spec_param
-- ----------------------------
INSERT INTO `t_spec_param` VALUES ('1', '10001', '1', 'CPU', '0', null, '1', '1', null);
INSERT INTO `t_spec_param` VALUES ('2', '10001', '2', '内存', '1', 'GB', '0', '1', null);
