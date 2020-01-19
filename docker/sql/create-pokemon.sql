 
-- Adminer 4.7.3 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
DROP DATABASE IF EXISTS `project2-amt-pokemon`;
CREATE DATABASE `project2-amt-pokemon` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `project2-amt-pokemon`;

DROP TABLE IF EXISTS `user_entity`;
CREATE TABLE `user_entity` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2jsk4eakd0rmvybo409wgwxuw` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `pokemon_entity`;
CREATE TABLE `pokemon_entity` (
  `poke_dex_id` int(11) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `hp` int(11) DEFAULT NULL,
  `id_user` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`poke_dex_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `trainer_entity`;
CREATE TABLE `trainer_entity` (
  `trainer_id` int(11) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `id_user` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `number_of_badges` int(11) DEFAULT NULL,
  `surname` varchar(255) NOT NULL,
  PRIMARY KEY (`trainer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `capture_entity`;
CREATE TABLE `capture_entity` (
  `id` int(11) NOT NULL,
  `date_capture` varchar(255) DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL,
  `id_pokemon` int(11) DEFAULT NULL,
  `id_trainer` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKam7tb1vw3d976v2jwk31nu23n` (`id_pokemon`),
  KEY `FKpu2jsg55ol4v4ibdq7lhjnl2s` (`id_trainer`),
  CONSTRAINT `FKam7tb1vw3d976v2jwk31nu23n` FOREIGN KEY (`id_pokemon`) REFERENCES `pokemon_entity` (`poke_dex_id`) ON DELETE CASCADE,
  CONSTRAINT `FKpu2jsg55ol4v4ibdq7lhjnl2s` FOREIGN KEY (`id_trainer`) REFERENCES `trainer_entity` (`trainer_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 2020-01-11 09:35:32
