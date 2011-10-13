CREATE TABLE `un_storage` (
  `id` varchar(64) NOT NULL,
  `tenant` varchar(64) DEFAULT NULL,
  `last_update_user` varchar(64) DEFAULT NULL,
  `last_update_date` date DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  `note` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;