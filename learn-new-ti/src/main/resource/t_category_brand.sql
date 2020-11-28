/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : newti

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2020-11-23 17:34:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_category_brand
-- ----------------------------
DROP TABLE IF EXISTS `t_category_brand`;
CREATE TABLE `t_category_brand` (
  `categroy_id` int(11) NOT NULL COMMENT '分类id',
  `brand_id` int(11) NOT NULL COMMENT '品牌id',
  PRIMARY KEY (`categroy_id`,`brand_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_category_brand
-- ----------------------------
