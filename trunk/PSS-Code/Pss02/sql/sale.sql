CREATE TABLE `sale` (
  `id` varchar(64) NOT NULL,
  `tenant` varchar(64) DEFAULT NULL,
  `money` double(15,3) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_update_user` varchar(64) DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;