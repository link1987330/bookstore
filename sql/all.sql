
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号信息';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图书信息';

CREATE TABLE `bookstore`.`t_inventory` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `book_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '图书ID（实际中应该用条码）',
  `quantity` int(10) NOT NULL DEFAULT '0' COMMENT '当前库存数',
  `min_quantity` int(10) NOT NULL DEFAULT '0' COMMENT '最小库存数（提醒补货）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_user_id` bigint(20) NOT NULL DEFAULT '0',
  `defunct` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存信息';

create table `bookstore`.`t_order` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '下单用户id',
    `order_status` int(2) NOT NULL DEFAULT '0' COMMENT '订单状态',
    `pay_status` int(2) NOT NULL DEFAULT '0' COMMENT '订单支付状态',
    `total_amount` decimal(18,2) not null COMMENT '总金额',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_user_id` bigint(20) NOT NULL DEFAULT '0',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_user_id` bigint(20) NOT NULL DEFAULT '0',
    `defunct` tinyint(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单信息';

create table `bookstore`.`t_order_item` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单id',
    `book_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '购买商品id',
    `quantity` int(10) NOT NULL DEFAULT '0' COMMENT '购买数量',
    `unit_price` decimal(18,2) NOT NULL COMMENT '单价',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_user_id` bigint(20) NOT NULL DEFAULT '0',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_user_id` bigint(20) NOT NULL DEFAULT '0',
    `defunct` tinyint(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='下单商品明细';