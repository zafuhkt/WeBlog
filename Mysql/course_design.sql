/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50710 (5.7.10)
 Source Host           : localhost:3306
 Source Schema         : course_design

 Target Server Type    : MySQL
 Target Server Version : 50710 (5.7.10)
 File Encoding         : 65001

 Date: 27/11/2022 18:14:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for agree
-- ----------------------------
DROP TABLE IF EXISTS `agree`;
CREATE TABLE `agree`  (
  `id` int(11) NOT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agree
-- ----------------------------
INSERT INTO `agree` VALUES (1, 123, '浩瀚宇宙，我想分享特别。');
INSERT INTO `agree` VALUES (1, 123, '说的很有道理');

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `place` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `text` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `relay` bigint(20) UNSIGNED NOT NULL,
  `comment` bigint(20) UNSIGNED NOT NULL,
  `thumbup` bigint(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES (1, 123, '11-23 11:12', '浙江', '今天天气真好', 12, 25, 29);
INSERT INTO `blog` VALUES (2, 123, '11-23 11:15', '上海', '企鹅今天烤面包了吗？', 42, 55, 65);
INSERT INTO `blog` VALUES (3, 123, '11-25 18:55', '上海', '今天我不想读书，想吃火锅！', 0, 0, 0);
INSERT INTO `blog` VALUES (4, 1, '11-23 20:20', '浙江', '第四条', 1, 1, 1);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(11) NOT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `place` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 123, '有人陪我散步吗？', '11-25 15:30', '浙江');
INSERT INTO `comment` VALUES (1, 1234, '我陪你出去走走吧。', '11-25 15:30', '江苏');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `posts` int(11) NULL DEFAULT NULL,
  `followers` int(11) NULL DEFAULT NULL,
  `followings` int(11) NULL DEFAULT NULL,
  `pic` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `name`) USING BTREE,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '用户1', '123456', 0, 0, 0, 'head');
INSERT INTO `user` VALUES (2, '用户2', '123456', 0, 0, 0, 'head3');
INSERT INTO `user` VALUES (11, '用户11', '11', 0, 0, 0, '0');
INSERT INTO `user` VALUES (123, '小黄小黄', '123456', 4, 33, 10, 'head2');
INSERT INTO `user` VALUES (1234, '小乳猪1', '123456', 4, 33, 10, 'head');
INSERT INTO `user` VALUES (12345, 'aa', '123456', 0, 0, 0, 'head3');

SET FOREIGN_KEY_CHECKS = 1;
