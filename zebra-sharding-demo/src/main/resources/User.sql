/*
Navicat MySQL Data Transfer
Source Database       : test
Target Server Type    : MYSQL
*/

SET FOREIGN_KEY_CHECKS=0;


-- ----------------------------
-- Table structure for `t_user_0`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_0`;
CREATE TABLE `t_user_0` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `uid` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '名字',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试用户表';

-- ----------------------------
-- Records of t_user_0
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user_1`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_1`;
CREATE TABLE `t_user_1` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `uid` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '名字',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='测试用户表';

-- ----------------------------
-- Records of t_user_1
-- ----------------------------
INSERT INTO `t_user_1` VALUES ('1', 'uid0000002', 'test002');

-- ----------------------------
-- Table structure for `t_user_2`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_2`;
CREATE TABLE `t_user_2` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `uid` varchar(32) DEFAULT NULL,
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '名字',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='测试用户表';

-- ----------------------------
-- Records of t_user_2
-- ----------------------------
INSERT INTO `t_user_2` VALUES ('1', 'uid0000001', 'test001');
