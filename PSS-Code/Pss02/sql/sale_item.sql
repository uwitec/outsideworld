CREATE TABLE `sale_item` (
  `id` varchar(64) NOT NULL,
  `sale_id` varchar(64) DEFAULT NULL,
  `tenant` varchar(64) DEFAULT NULL,
  `item` varchar(64) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `unit_price` double(15,3) DEFAULT NULL,
  `total` double(15,3) DEFAULT NULL,
  `ref_price` double(15,3) DEFAULT NULL,
  `discount` int(11) DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;