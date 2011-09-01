CREATE TABLE `pre_purchase_detail` (
  `id` varchar(64) NOT NULL,
  `pur_id` varchar(64) DEFAULT NULL,
  `supplier` varchar(100) DEFAULT NULL,
  `goods` varchar(100) DEFAULT NULL,
  `specification` varchar(200) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `unit` varchar(10) DEFAULT NULL,
  `purchase_price` double(15,3) DEFAULT NULL,
  `ref_purchase_price` double(15,3) DEFAULT NULL,
  `inventory` int(11) DEFAULT NULL,
  `depreciation` int(11) DEFAULT NULL,
  `note` varchar(200) DEFAULT NULL,
  `last_update_user` varchar(64) DEFAULT NULL,
  `last_update_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;