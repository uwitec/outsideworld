SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `pss` DEFAULT CHARACTER SET utf8 ;
USE `pss` ;

-- -----------------------------------------------------
-- Table `pss`.`role`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `pss`.`role` (
  `role_id` VARCHAR(64) NOT NULL ,
  `role_name` VARCHAR(20) NULL DEFAULT NULL ,
  `role_desc` VARCHAR(500) NULL DEFAULT NULL ,
  PRIMARY KEY (`role_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pss`.`role_privalige`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `pss`.`role_privalige` (
  `role_id` VARCHAR(64) NULL DEFAULT NULL ,
  `url` VARCHAR(500) NULL DEFAULT NULL )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pss`.`role_user`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `pss`.`role_user` (
  `user_id` VARCHAR(64) NULL DEFAULT NULL ,
  `role_id` VARCHAR(64) NULL DEFAULT NULL ,
  `tenant_id` VARCHAR(64) NULL DEFAULT NULL )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pss`.`system_sequence`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `pss`.`system_sequence` (
  `seq_name` VARCHAR(200) NOT NULL ,
  `seq_seed` INT(11) NOT NULL DEFAULT '0' ,
  PRIMARY KEY (`seq_name`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pss`.`tenant`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `pss`.`tenant` (
  `tenant_id` VARCHAR(64) NOT NULL ,
  `tenant_name` VARCHAR(50) NOT NULL ,
  `tenant_password` VARCHAR(64) NOT NULL ,
  `tenant_country` VARCHAR(20) NULL DEFAULT '中国' ,
  `tenant_city` VARCHAR(20) NULL DEFAULT '阳泉' ,
  `tenant_province` VARCHAR(20) NULL DEFAULT '山西' ,
  `tenant_email` VARCHAR(50) NULL DEFAULT NULL ,
  `user_num` INT(11) NULL DEFAULT NULL ,
  `creat_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
  `status` VARCHAR(1) NULL DEFAULT NULL ,
  `note` VARCHAR(500) NULL DEFAULT NULL ,
  PRIMARY KEY (`tenant_id`) ,
  UNIQUE INDEX `tenant_name` (`tenant_name` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pss`.`user`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `pss`.`user` (
  `user_id` VARCHAR(64) NOT NULL ,
  `tenant` VARCHAR(64) NULL DEFAULT NULL ,
  `user_name` VARCHAR(50) NULL DEFAULT NULL ,
  `user_password` VARCHAR(64) NULL DEFAULT NULL ,
  `last_login_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
  `status` VARCHAR(1) NULL DEFAULT NULL ,
  `role_id` VARCHAR(64) NOT NULL ,
  PRIMARY KEY (`user_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
