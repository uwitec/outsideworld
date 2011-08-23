CREATE TABLE `goods` (
  `id` varchar(32) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `category` varchar(1) DEFAULT NULL,
  `specification` varchar(200) DEFAULT NULL,
  `unit` varchar(10) DEFAULT NULL,
  `purchase_price` double(15,3) DEFAULT NULL,
  `sale_price` double(15,3) DEFAULT NULL,
  `inventory` int(11) DEFAULT NULL,
  `barcode` varchar(64) DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  `lastUpdateUser` varchar(100) DEFAULT NULL,
  `lastUpdateDate` date DEFAULT NULL,
  `tenant` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;