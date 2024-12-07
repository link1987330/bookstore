
CREATE TABLE `bookstore`.`t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL DEFAULT '',
  `last_name` varchar(50) NOT NULL DEFAULT '',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_user_id` bigint(20) NOT NULL DEFAULT '0',
  `defunct` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `bookstore`.`t_passport` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_type` int(2) NOT NULL DEFAULT '0',
  `email` varchar(100) NOT NULL DEFAULT '',
  `mobile` varchar(50) NOT NULL DEFAULT '',
  `password` varchar(50) NOT NULL DEFAULT '',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_user_id` bigint(20) NOT NULL DEFAULT '0',
  `defunct` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `bookstore`.`t_book` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bookname` varchar(100) NOT NULL DEFAULT '' COMMENT '书名',
  `author` varchar(100) NOT NULL DEFAULT '' COMMENT '作者',
  `price` decimal(9,2) NOT NULL DEFAULT '0.00' COMMENT '原价',
  `selling_price` decimal(9,2) NOT NULL DEFAULT '0.00' COMMENT '售价',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_user_id` bigint(20) NOT NULL DEFAULT '0',
  `defunct` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
