/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : newti

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2020-11-23 17:34:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_sku
-- ----------------------------
DROP TABLE IF EXISTS `t_sku`;
CREATE TABLE `t_sku` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `spu_id` int(11) DEFAULT NULL COMMENT '产品id',
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品标题',
  `images` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` decimal(10,0) DEFAULT NULL COMMENT '价格',
  `param` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '参数',
  `saleable` tinyint(1) DEFAULT '1',
  `valid` tinyint(4) DEFAULT '1' COMMENT '是否有效',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_sku
-- ----------------------------
