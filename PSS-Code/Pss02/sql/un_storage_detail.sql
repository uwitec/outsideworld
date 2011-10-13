CREATE TABLE `un_storage_detail` (
  `id` varchar(64) NOT NULL,
  `tenant` varchar(64) DEFAULT NULL,
  `store_id` varchar(64) DEFAULT NULL,
  `good_name` varchar(100) DEFAULT NULL,
  `good_id` varchar(64) DEFAULT NULL,
  `good_specification` varchar(200) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `note` varchar(200) DEFAULT NULL,
  `last_update_user` varchar(64) DEFAULT NULL,
  `last_update_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;