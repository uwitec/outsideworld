CREATE TABLE `role` (
  `role_id` varchar(64) NOT NULL,
  `role_name` varchar(20) DEFAULT NULL,
  `role_desc` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
