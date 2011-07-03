CREATE TABLE `tenant` (
  `tenant_id` varchar(64) NOT NULL,
  `tenant_name` varchar(50) NOT NULL,
  `tenant_password` varchar(64) NOT NULL,
  `tenant_country` varchar(20) DEFAULT NULL,
  `tenant_city` varchar(20) DEFAULT NULL,
  `tenant_province` varchar(20) DEFAULT NULL,
  `tenant_email` varchar(50) DEFAULT NULL,
  `user_num` int(11) DEFAULT NULL,
  `creat_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(1) DEFAULT NULL,
  `note` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`tenant_id`),
  UNIQUE KEY `tenant_name` (`tenant_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
