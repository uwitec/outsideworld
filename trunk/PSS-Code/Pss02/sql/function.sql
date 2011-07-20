CREATE TABLE `function` (
  `function_id` varchar(64) NOT NULL,
  `url` varchar(500) DEFAULT NULL,
  `module` varchar(100) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;