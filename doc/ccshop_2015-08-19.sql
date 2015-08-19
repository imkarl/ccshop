# ************************************************************
# Sequel Pro SQL dump
# Version 3408
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Database: ccshop
# Generation Time: 2015-08-19 07:02:48 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table t_admin
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_admin`;

CREATE TABLE `t_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `pass` varchar(100) NOT NULL,
  `nickname` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(13) NOT NULL,
  `updatetime` datetime DEFAULT NULL,
  `portrait` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_UNIQUE` (`phone`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `nickname_UNIQUE` (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_product
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_product`;

CREATE TABLE `t_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usersId` int(11) NOT NULL,
  `catalogId` int(11) DEFAULT NULL,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(145) COLLATE utf8_unicode_ci DEFAULT NULL,
  `info` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `unitPrice` int(11) DEFAULT NULL,
  `nowPrice` int(11) DEFAULT NULL,
  `picture` varchar(245) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `isTop` bit(1) DEFAULT b'0',
  `unit` varchar(25) COLLATE utf8_unicode_ci DEFAULT '件',
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `state` int(11) DEFAULT '0',
  `viewCount` int(11) DEFAULT '0',
  `sellCount` int(11) DEFAULT '0',
  `stockCount` int(11) DEFAULT '0',
  `keywords` varchar(145) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name_KEY` (`name`),
  KEY `catalogId_KEY` (`catalogId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



# Dump of table t_product_cart
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_product_cart`;

CREATE TABLE `t_product_cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usersId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



# Dump of table t_product_favorite
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_product_favorite`;

CREATE TABLE `t_product_favorite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usersId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



# Dump of table t_sys_profit
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_sys_profit`;

CREATE TABLE `t_sys_profit` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一ID',
  `usersId` int(11) NOT NULL COMMENT '用户ID (默认收益 <管理员ID ‘0’>设置,其他用户设置)',
  `oneBalance` int(11) DEFAULT NULL COMMENT '一级余额充值收益',
  `twoBalance` int(11) DEFAULT NULL COMMENT '二级余额充值收益',
  `threeBalance` int(11) DEFAULT NULL COMMENT '三级余额充值收益',
  `oneWithdraw` int(11) DEFAULT NULL COMMENT '一级余额提现收益',
  `twoWithdraw` int(11) DEFAULT NULL COMMENT '二级余额提现收益',
  `threeWithdraw` int(11) DEFAULT NULL COMMENT '三级余额提现收益',
  `oneCall` varchar(512) DEFAULT NULL COMMENT '一级话费充值收益',
  `twoCall` varchar(512) DEFAULT NULL COMMENT '二级话费充值收益',
  `threeCall` varchar(512) DEFAULT NULL COMMENT '三级话费充值收益',
  `oneShop` int(11) DEFAULT NULL COMMENT '一级购物收益',
  `twoShop` int(11) DEFAULT NULL COMMENT '二级购物收益',
  `threeShop` int(11) DEFAULT NULL COMMENT '三级购物收益',
  `oneTransfer` int(11) DEFAULT NULL COMMENT '一级转账收益',
  `twoTransfer` int(11) DEFAULT NULL COMMENT '二级转账收益',
  `threeTransfer` int(11) DEFAULT NULL COMMENT '三级转账收益',
  `call_50` int(11) DEFAULT NULL COMMENT '话费充值费率-50',
  `call_100` int(11) DEFAULT NULL COMMENT '话费充值费率-100',
  `call_150` int(11) DEFAULT NULL COMMENT '话费充值费率-150',
  `call_200` int(11) DEFAULT NULL COMMENT '话费充值费率-200',
  `call_300` int(11) DEFAULT NULL COMMENT '话费充值费率-300',
  `call_500` int(11) DEFAULT NULL COMMENT '话费充值费率-500',
  `call_1000` int(11) DEFAULT NULL COMMENT '话费充值费率-1000',
  `transfer` int(11) DEFAULT NULL COMMENT '转账费率',
  `withdraw` int(11) DEFAULT NULL COMMENT '提现手续费',
  `alipay` int(11) DEFAULT NULL COMMENT '支付宝汇率',
  `kklpay` int(11) DEFAULT NULL COMMENT '卡卡联NFC汇率',
  `auth` int(11) DEFAULT NULL COMMENT '卡卡联认证支付汇率',
  `bank` int(11) DEFAULT NULL COMMENT '网银汇率',
  `allinpay` int(11) DEFAULT NULL COMMENT '通联汇率',
  PRIMARY KEY (`id`),
  UNIQUE KEY `usersId_UNIQUE` (`usersId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_sys_trade
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_sys_trade`;

CREATE TABLE `t_sys_trade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fromid` int(11) NOT NULL,
  `toid` int(11) NOT NULL,
  `money` int(11) NOT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `createtime` datetime NOT NULL,
  `sn` varchar(100) NOT NULL,
  `endtime` datetime DEFAULT NULL,
  `state` int(3) NOT NULL DEFAULT '0',
  `paysn` varchar(100) DEFAULT NULL,
  `paytype` int(3) DEFAULT NULL,
  `type` int(3) NOT NULL,
  `name` varchar(100) NOT NULL,
  `isInstant` bit(1) NOT NULL,
  `feesMoney` int(11) DEFAULT NULL,
  `oneProfitMoney` int(11) DEFAULT NULL,
  `twoProfitMoney` int(11) DEFAULT NULL,
  `threeProfitMoney` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn_UNIQUE` (`sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_trade_call
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_trade_call`;

CREATE TABLE `t_trade_call` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fromid` int(11) NOT NULL,
  `money` int(11) NOT NULL,
  `createtime` datetime NOT NULL,
  `sn` varchar(100) NOT NULL,
  `phone` varchar(11) NOT NULL,
  `callMoney` int(11) NOT NULL,
  `isArrival` bit(1) DEFAULT NULL,
  `orderSn` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn_UNIQUE` (`sn`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table t_trade_transfer
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_trade_transfer`;

CREATE TABLE `t_trade_transfer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fromid` int(11) NOT NULL,
  `money` int(11) NOT NULL,
  `createtime` datetime NOT NULL,
  `sn` varchar(100) NOT NULL,
  `toId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn_UNIQUE` (`sn`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table t_users
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_users`;

CREATE TABLE `t_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `pass` varchar(100) NOT NULL,
  `nickname` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(13) NOT NULL,
  `createtime` datetime NOT NULL,
  `updatetime` datetime DEFAULT NULL,
  `state` int(3) DEFAULT '0',
  `inviteId` int(11) DEFAULT NULL,
  `inviteCode` varchar(20) NOT NULL DEFAULT '',
  `portrait` varchar(100) DEFAULT NULL,
  `authstate` int(3) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_UNIQUE` (`phone`),
  UNIQUE KEY `inviteCode_UNIQUE` (`inviteCode`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `nickname_UNIQUE` (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_users_auth
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_users_auth`;

CREATE TABLE `t_users_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usersId` int(11) NOT NULL,
  `photo` varchar(200) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `idcard` varchar(20) DEFAULT NULL,
  `bankcard` varchar(20) DEFAULT NULL,
  `bankname` varchar(100) DEFAULT NULL,
  `bankbranch` varchar(100) DEFAULT NULL,
  `bankaddr` varchar(200) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `usersId_UNIQUE` (`usersId`),
  UNIQUE KEY `idcard_UNIQUE` (`idcard`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_users_money
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_users_money`;

CREATE TABLE `t_users_money` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usersId` int(11) NOT NULL,
  `money` int(11) NOT NULL DEFAULT '0',
  `lockmoney` int(11) NOT NULL DEFAULT '0',
  `isLock` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `usersId_UNIQUE` (`usersId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_users_money_change
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_users_money_change`;

CREATE TABLE `t_users_money_change` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fromId` int(11) NOT NULL,
  `sn` varchar(100) NOT NULL,
  `money` int(11) NOT NULL,
  `createtime` datetime NOT NULL,
  `isAdd` bit(1) DEFAULT NULL,
  `linkSn` varchar(100) DEFAULT NULL,
  `isArrival` bit(1) DEFAULT NULL,
  `users_money` int(11) DEFAULT NULL,
  `users_lockmoney` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn_UNIQUE` (`sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_users_roles
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_users_roles`;

CREATE TABLE `t_users_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一ID',
  `usersId` int(11) NOT NULL COMMENT '用户ID',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `hasUpdateProfit` bit(1) DEFAULT b'0' COMMENT '是否可修改利润比例',
  `hasInvite` bit(1) DEFAULT b'0' COMMENT '是否可邀请他人',
  `hasShop` bit(1) DEFAULT b'0' COMMENT '是否可开通微店',
  `hasP2p` bit(1) DEFAULT b'0' COMMENT '是否可理财',
  `hasCall` bit(1) DEFAULT b'0' COMMENT '是否可充值话费',
  PRIMARY KEY (`id`),
  UNIQUE KEY `usersId_UNIQUE` (`usersId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
