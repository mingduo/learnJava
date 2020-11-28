/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : newti

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2020-11-23 17:33:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_spu
-- ----------------------------
DROP TABLE IF EXISTS `t_spu`;
CREATE TABLE `t_spu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sub_titile` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '副标题',
  `category_id` int(11) DEFAULT NULL COMMENT '分类id',
  `brand_id` int(11) DEFAULT NULL COMMENT '品牌id',
  `spg_id` int(11) DEFAULT NULL COMMENT '品类id',
  `saleable` tinyint(255) DEFAULT '0' COMMENT '是否上架',
  `valid` int(11) DEFAULT '1' COMMENT '是否有效',
  `create_time` datetime DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
