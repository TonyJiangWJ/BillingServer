-- MySQL dump 10.13  Distrib 5.7.11, for Win64 (x86_64)
--
-- ------------------------------------------------------
-- Server version	5.7.19-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_admin`
--

DROP TABLE IF EXISTS `t_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tokenId` varchar(42) NOT NULL DEFAULT '',
  `tokenVerify` bigint(20) NOT NULL DEFAULT '0',
  `code` varchar(20) DEFAULT NULL,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL,
  `lastLogin` datetime DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `isDeleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_asset`
--

DROP TABLE IF EXISTS `t_asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_asset` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `type` varchar(16) DEFAULT NULL,
  `amount` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_budget`
--

DROP TABLE IF EXISTS `t_budget`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_budget` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tagId` bigint(20) NOT NULL COMMENT '标签id',
  `budgetMoney` bigint(20) NOT NULL DEFAULT '0' COMMENT '预算金额',
  `createTime` datetime NOT NULL,
  `modifyTime` datetime NOT NULL,
  `belongYear` char(4) NOT NULL DEFAULT '2017' COMMENT '年份',
  `belongMonth` int(11) NOT NULL COMMENT '月份',
  `isDelete` tinyint(4) NOT NULL DEFAULT '0',
  `version` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_cost_info`
--

DROP TABLE IF EXISTS `t_cost_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_cost_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL DEFAULT '-1',
  `tradeNo` varchar(100) DEFAULT NULL,
  `orderNo` varchar(100) DEFAULT NULL,
  `createTime` varchar(100) DEFAULT NULL,
  `paidTime` varchar(100) DEFAULT NULL,
  `modifyTime` varchar(100) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `orderType` varchar(100) DEFAULT NULL,
  `target` varchar(100) DEFAULT NULL,
  `goodsName` varchar(100) DEFAULT NULL,
  `money` bigint(20) DEFAULT NULL,
  `inOutType` varchar(100) DEFAULT NULL,
  `orderStatus` varchar(100) DEFAULT NULL,
  `serviceCost` bigint(20) DEFAULT '0',
  `refundMoney` bigint(20) DEFAULT '0',
  `memo` varchar(100) DEFAULT NULL,
  `tradeStatus` varchar(100) DEFAULT NULL,
  `isDelete` tinyint(4) NOT NULL DEFAULT '0',
  `isHidden` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tradeNoAndUserId` (`tradeNo`,`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_cost_info_deleted`
--

DROP TABLE IF EXISTS `t_cost_info_deleted`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_cost_info_deleted` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL DEFAULT '-1',
  `tradeNo` varchar(100) DEFAULT NULL,
  `orderNo` varchar(100) DEFAULT NULL,
  `createTime` varchar(100) DEFAULT NULL,
  `paidTime` varchar(100) DEFAULT NULL,
  `modifyTime` varchar(100) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `orderType` varchar(100) DEFAULT NULL,
  `target` varchar(100) DEFAULT NULL,
  `goodsName` varchar(100) DEFAULT NULL,
  `money` bigint(20) DEFAULT NULL,
  `inOutType` varchar(100) DEFAULT NULL,
  `orderStatus` varchar(100) DEFAULT NULL,
  `serviceCost` bigint(20) DEFAULT '0',
  `refundMoney` bigint(20) DEFAULT '0',
  `memo` varchar(100) DEFAULT NULL,
  `tradeStatus` varchar(100) DEFAULT NULL,
  `isHidden` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tradeNo` (`tradeNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_cost_tag`
--

DROP TABLE IF EXISTS `t_cost_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_cost_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tagId` bigint(20) NOT NULL,
  `costId` bigint(20) NOT NULL,
  `createTime` datetime NOT NULL,
  `modifyTime` datetime NOT NULL,
  `isDelete` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_liability`
--

DROP TABLE IF EXISTS `t_liability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_liability` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `repayment_day` datetime NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `type` varchar(16) DEFAULT NULL,
  `amount` bigint(20) DEFAULT NULL,
  `installment` int(11) DEFAULT NULL,
  `index` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_tag_info`
--

DROP TABLE IF EXISTS `t_tag_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_tag_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tagName` varchar(32) NOT NULL,
  `createTime` datetime NOT NULL,
  `modifyTime` datetime NOT NULL,
  `isDelete` tinyint(4) NOT NULL DEFAULT '0',
  `userId` bigint(20) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'my_2017_cost'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-11 17:20:49
