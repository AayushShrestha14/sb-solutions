CREATE TABLE if not exists `product_mode` (
 `id` bigint(20) NOT NULL primary KEY AUTO_INCREMENT,
  `product` int(11) DEFAULT NULL,
  `status`  int(10) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;


INSERT INTO `product_mode` (`id`, `product`,`status`)
 VALUES
  ('1', '0',0),
  ('2', '1',1),
  ('3', '2',1),
  ('4', '3',1);