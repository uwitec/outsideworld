SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `yuqing` ;
CREATE SCHEMA IF NOT EXISTS `yuqing` DEFAULT CHARACTER SET utf8 ;
USE `yuqing` ;

-- -----------------------------------------------------
-- Table `yuqing`.`source`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yuqing`.`source` ;

CREATE  TABLE IF NOT EXISTS `yuqing`.`source` (
  `id` INT(11) NOT NULL ,
  `category_id` INT(11) NOT NULL COMMENT '所属分类ID' ,
  `name` VARCHAR(200) NOT NULL ,
  `type` VARCHAR(255) NULL DEFAULT NULL COMMENT '类型：网页，微博，QQ等' ,
  `url` VARCHAR(200) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `url` (`url` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '数据源';


-- -----------------------------------------------------
-- Table `yuqing`.`template`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yuqing`.`template` ;

CREATE  TABLE IF NOT EXISTS `yuqing`.`template` (
  `id` INT(11) NOT NULL ,
  `domain` VARCHAR(200) NOT NULL COMMENT '域名' ,
  `fetch_interval` INT(11) NULL DEFAULT NULL COMMENT '抓取间隔' ,
  `url_regex` VARCHAR(255) NOT NULL COMMENT 'URL正则表达式' ,
  `source_id` INT(11) NULL DEFAULT NULL COMMENT '数据源ID' ,
  PRIMARY KEY (`id`) ,
  INDEX `FKB13ACC7A9F6FFE44` (`source_id` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '提取模板';


-- -----------------------------------------------------
-- Table `yuqing`.`element`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yuqing`.`element` ;

CREATE  TABLE IF NOT EXISTS `yuqing`.`element` (
  `id` INT(11) NOT NULL ,
  `define` VARCHAR(200) NOT NULL COMMENT 'CSS/XPATH定义' ,
  `format` VARCHAR(200) NULL DEFAULT NULL COMMENT '日期的格式如:yyyyMMdd等' ,
  `name` VARCHAR(100) NOT NULL COMMENT '元素名称，title,content,publish time,等' ,
  `regex` VARCHAR(200) NULL DEFAULT NULL COMMENT '提取后进一步提取的正则表达式' ,
  `type` INT(11) NOT NULL COMMENT 'CSS/XPATH' ,
  `template_id` INT(11) NULL DEFAULT NULL COMMENT '模板ID' ,
  PRIMARY KEY (`id`) ,
  INDEX `FK9CE31EFC3B18BB64` (`template_id` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '页面元素title,content,publish time,等';


-- -----------------------------------------------------
-- Table `yuqing`.`favorite`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yuqing`.`favorite` ;

CREATE  TABLE IF NOT EXISTS `yuqing`.`favorite` (
  `id` INT(11) NOT NULL ,
  `info_id` VARCHAR(255) NOT NULL COMMENT '舆情ID' ,
  `user_id` VARCHAR(255) NOT NULL COMMENT '用户ID' ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '用户收藏的舆情';


-- -----------------------------------------------------
-- Table `yuqing`.`group_topic`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yuqing`.`group_topic` ;

CREATE  TABLE IF NOT EXISTS `yuqing`.`group_topic` (
  `id` INT(11) NOT NULL ,
  `group_id` INT(11) NOT NULL ,
  `topic_id` INT(11) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '记录用户组可以查看哪些舆情';


-- -----------------------------------------------------
-- Table `yuqing`.`groups`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yuqing`.`groups` ;

CREATE  TABLE IF NOT EXISTS `yuqing`.`groups` (
  `id` INT(11) NOT NULL ,
  `name` VARCHAR(200) NOT NULL ,
  `role` VARCHAR(255) NULL DEFAULT NULL COMMENT '角色：管理员，普通用户等' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name` (`name` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '用户组';


-- -----------------------------------------------------
-- Table `yuqing`.`log`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yuqing`.`log` ;

CREATE  TABLE IF NOT EXISTS `yuqing`.`log` (
  `id` BIGINT(20) NOT NULL ,
  `dttm` DATETIME NOT NULL COMMENT '记录时间' ,
  `message` LONGTEXT NOT NULL COMMENT '日志内容' ,
  `type` VARCHAR(255) NOT NULL COMMENT '动作类型' ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '日志表';


-- -----------------------------------------------------
-- Table `yuqing`.`param`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yuqing`.`param` ;

CREATE  TABLE IF NOT EXISTS `yuqing`.`param` (
  `id` INT(11) NOT NULL ,
  `name1` VARCHAR(200) NOT NULL ,
  `name2` VARCHAR(200) NULL DEFAULT NULL ,
  `name3` VARCHAR(200) NULL DEFAULT NULL ,
  `name4` VARCHAR(200) NULL DEFAULT NULL ,
  `name5` VARCHAR(200) NULL DEFAULT NULL ,
  `type` VARCHAR(255) NULL DEFAULT NULL COMMENT '参数类型，用户过滤' ,
  `value1` VARCHAR(200) NULL DEFAULT NULL ,
  `value2` VARCHAR(200) NULL DEFAULT NULL ,
  `value3` VARCHAR(200) NULL DEFAULT NULL ,
  `value4` VARCHAR(200) NULL DEFAULT NULL ,
  `value5` VARCHAR(200) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '参数表';


-- -----------------------------------------------------
-- Table `yuqing`.`source_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yuqing`.`source_category` ;

CREATE  TABLE IF NOT EXISTS `yuqing`.`source_category` (
  `id` INT(11) NOT NULL ,
  `name` VARCHAR(200) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name` (`name` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '数据源分类';


-- -----------------------------------------------------
-- Table `yuqing`.`topic`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yuqing`.`topic` ;

CREATE  TABLE IF NOT EXISTS `yuqing`.`topic` (
  `id` INT(11) NOT NULL ,
  `disable` BIT(1) NOT NULL COMMENT '是否禁用' ,
  `warn` BIT(1) NOT NULL COMMENT '是否告警' ,
  `exclude` VARCHAR(200) NULL DEFAULT NULL COMMENT '不可以包含的关键词' ,
  `expire_dttm` DATETIME NULL DEFAULT NULL COMMENT '过期时间' ,
  `include` VARCHAR(200) NOT NULL COMMENT '必须包含的关键词' ,
  `negative` BIT(1) NOT NULL COMMENT '是否负面消息' ,
  `name` VARCHAR(200) NOT NULL ,
  `optional` VARCHAR(200) NULL DEFAULT NULL COMMENT '可以包含的关键词' ,
  `warn_type` VARCHAR(255) NULL DEFAULT NULL COMMENT '告警类型：邮件，短信等' ,
  `warn_limit` INT(11) NOT NULL COMMENT '告警需要的舆情记录数' ,
  `warn_title` LONGTEXT NULL DEFAULT NULL COMMENT '告警标题' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name` (`name` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '舆情主题';


-- -----------------------------------------------------
-- Table `yuqing`.`topic_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yuqing`.`topic_item` ;

CREATE  TABLE IF NOT EXISTS `yuqing`.`topic_item` (
  `uuid` VARCHAR(255) NOT NULL ,
  `item_id` BIGINT(20) NOT NULL COMMENT 'mongoDB中的舆情ID' ,
  `topic_id` INT(11) NOT NULL COMMENT '舆情主题ID' ,
  PRIMARY KEY (`uuid`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '舆情主题和舆情关联表';


-- -----------------------------------------------------
-- Table `yuqing`.`topic_source`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yuqing`.`topic_source` ;

CREATE  TABLE IF NOT EXISTS `yuqing`.`topic_source` (
  `id` INT(11) NOT NULL ,
  `category_id` INT(11) NOT NULL COMMENT '舆情数据源分类ID' ,
  `source_id` INT(11) NOT NULL COMMENT '舆情数据源ID' ,
  `topic_id` INT(11) NOT NULL COMMENT '舆情主题ID' ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '舆情主题和数据源关联表，具体指某一舆情主题从哪些或哪类数据源中提取信息';


-- -----------------------------------------------------
-- Table `yuqing`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yuqing`.`user` ;

CREATE  TABLE IF NOT EXISTS `yuqing`.`user` (
  `id` INT(11) NOT NULL ,
  `department` VARCHAR(200) NOT NULL ,
  `email` VARCHAR(200) NOT NULL ,
  `group_id` INT(11) NOT NULL ,
  `lastLogin` DATETIME NULL DEFAULT NULL ,
  `loginname` VARCHAR(50) NOT NULL ,
  `mobile` VARCHAR(30) NOT NULL ,
  `name` VARCHAR(200) NOT NULL ,
  `password` VARCHAR(64) NOT NULL ,
  `phone` VARCHAR(30) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `email` (`email` ASC) ,
  UNIQUE INDEX `mobile` (`mobile` ASC) ,
  UNIQUE INDEX `name` (`name` ASC) ,
  UNIQUE INDEX `phone` (`phone` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '用户表';


-- -----------------------------------------------------
-- Table `yuqing`.`user_topic`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yuqing`.`user_topic` ;

CREATE  TABLE IF NOT EXISTS `yuqing`.`user_topic` (
  `id` INT(11) NOT NULL ,
  `topic_id` INT(11) NOT NULL ,
  `user_id` INT(11) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '用户和舆情主题关联表';


-- -----------------------------------------------------
-- Table `yuqing`.`warn`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yuqing`.`warn` ;

CREATE  TABLE IF NOT EXISTS `yuqing`.`warn` (
  `id` INT(11) NOT NULL ,
  `topic_id` INT(11) NOT NULL ,
  `user_id` INT(11) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '告警主题和提示用户关联表';



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
