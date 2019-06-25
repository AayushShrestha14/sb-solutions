CREATE TABLE if not exists `product_mode` (
 `id` bigint(20) NOT NULL primary KEY AUTO_INCREMENT,
  `product` int(11) DEFAULT NULL,
  `status`  int(10) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

