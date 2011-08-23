CREATE TABLE `purchase` (
  `id` varchar(64) NOT NULL,
  `tenant` varchar(64) DEFAULT NULL,
  `surveyor` varchar(64) DEFAULT NULL,
  `paid` double(15,3) DEFAULT NULL,
  `payable` double(15,3) DEFAULT NULL,
  `last_update_user` varchar(64) DEFAULT NULL,
  `last_update_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;