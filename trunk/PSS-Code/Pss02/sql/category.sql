CREATE TABLE `category` (
  `tenant` varchar(64) DEFAULT NULL,	
  `id` varchar(64) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;