CREATE TABLE `suppliers` (
  `id` varchar(64) NOT NULL,
  `tenant` varchar(64) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `zip_code` varchar(20) DEFAULT NULL,
  `contact` varchar(100) DEFAULT NULL,
  `tel` varchar(100) DEFAULT NULL,
  `note` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;