 
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

INSERT INTO `user_entity` (`id`, `username`, `firstname`, `isadmin`, `lastname`, `mail`, `password`) VALUES
(1,	'admin',	NULL,	CONV('1', 2, 10) + 0,	NULL,	NULL,	'10000:3f38d6884bf04fbfdc8e57d2c1b69910356d9bc193cb08ff:bb50e016748c2b9a3233b1d4f79025a3176d9122200d5655'),
(2,	'user',	NULL,	CONV('0', 2, 10) + 0,	NULL,	'user@test.ch',	'10000:074b253d41a123bec07481d1d4412c28594ff0b2dcb9e714:00f1709a806c217844e77a7a86854f48a64b0307d63d847d');
-- 2020-01-11 09:32:58
