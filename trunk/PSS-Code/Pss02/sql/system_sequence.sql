CREATE TABLE `system_sequence` (
  `seq_name` varchar(200) NOT NULL,
  `seq_seed` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`seq_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;