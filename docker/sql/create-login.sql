 
-- Adminer 4.7.3 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';

DROP DATABASE IF EXISTS `project2-amt-login`;
CREATE DATABASE `project2-amt-login` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `project2-amt-login`;

DROP TABLE IF EXISTS `user_entity`;
CREATE TABLE `user_entity` (
  `id` int(11) NOT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `isadmin` bit(1) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 2020-01-11 09:32:58
