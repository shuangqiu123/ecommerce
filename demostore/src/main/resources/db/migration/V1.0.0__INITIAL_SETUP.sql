use ecommerce1;
CREATE TABLE `member` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT,
     `username` varchar(50) NOT NULL,
     `password` varchar(32) NOT NULL,
     `phone` varchar(20) DEFAULT NULL,
     `email` varchar(50) DEFAULT NULL,
     `created` datetime NOT NULL,
     `updated` datetime NOT NULL,
     `sex` varchar(2) DEFAULT '',
     `address` varchar(255) DEFAULT NULL,
     `state` int(1) DEFAULT '0',
     `file` varchar(255) DEFAULT NULL,
     `description` varchar(500),
     `points` int(11) DEFAULT '0',
     `balance` decimal(10,2) DEFAULT '0.00',
     isverified varchar(26) DEFAULT 'N',
     PRIMARY KEY (`id`),
     UNIQUE KEY `username` (`username`) USING BTREE,
     UNIQUE KEY `phone` (`phone`) USING BTREE,
     UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


CREATE TABLE `order` (
    `order_id` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '',
    `payment` decimal(10,2) DEFAULT NULL,
    `payment_type` int(1) DEFAULT NULL,
    `post_fee` decimal(10,2) DEFAULT NULL,
    `status` int(1) DEFAULT NULL,
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    `payment_time` datetime DEFAULT NULL,
    `consign_time` datetime DEFAULT NULL,
    `end_time` datetime DEFAULT NULL,
    `close_time` datetime DEFAULT NULL,
    `shipping_name` varchar(20) COLLATE utf8_bin DEFAULT NULL,
    `shipping_code` varchar(20) COLLATE utf8_bin DEFAULT NULL,
    `user_id` bigint(20) DEFAULT NULL,
    `buyer_message` varchar(100) COLLATE utf8_bin DEFAULT NULL,
    `buyer_nick` varchar(50) COLLATE utf8_bin DEFAULT NULL,
    `buyer_comment` tinyint(1) DEFAULT NULL,
    `unique_key` varchar(50) COLLATE utf8_bin DEFAULT NULL,
    PRIMARY KEY (`order_id`),
    KEY `create_time` (`create_time`),
    KEY `buyer_nick` (`buyer_nick`),
    KEY `status` (`status`),
    KEY `payment_type` (`payment_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `item` (
    `id` bigint(20) NOT NULL,
    `title` varchar(100) DEFAULT NULL,
    `sell_point` varchar(100) DEFAULT NULL,
    `price` decimal(10,2) DEFAULT '0.00',
    `num` int(11) DEFAULT NULL,
    `limit_num` int(11) DEFAULT NULL,
    `image` varchar(2000) DEFAULT NULL,
    `cid` bigint(11) DEFAULT NULL,
    `status` int(1) DEFAULT '1',
    `created` datetime DEFAULT NULL,
    `updated` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `cid` (`cid`),
    KEY `status` (`status`),
    KEY `updated` (`updated`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';


CREATE TABLE `order_item` (
    `id` varchar(50) COLLATE utf8_bin NOT NULL,
    `item_id` bigint(20) NOT NULL,
    `order_id` varchar(50) COLLATE utf8_bin NOT NULL,
    `num` int(10) DEFAULT NULL,
    `title` varchar(200) COLLATE utf8_bin DEFAULT NULL,
    `price` decimal(10,2) DEFAULT NULL,
    `total_fee` decimal(10,2) DEFAULT NULL,
    `pic_path` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
    `status` int(4) DEFAULT NULL,
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `oder_item_id` (`order_id`,`item_id`) USING BTREE,
    KEY `item_id` (`item_id`),
    KEY `order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `order_shipping` (
    `order_id` varchar(50) NOT NULL,
    `receiver_name` varchar(20) DEFAULT NULL,
    `receiver_phone` varchar(20) DEFAULT NULL,
    `receiver_mobile` varchar(30) DEFAULT NULL,
    `receiver_state` varchar(10) DEFAULT NULL,
    `receiver_city` varchar(10) DEFAULT NULL,
    `receiver_district` varchar(20) DEFAULT NULL,
    `receiver_address` varchar(200) DEFAULT NULL,
    `receiver_zip` varchar(6) DEFAULT NULL,
    `created` datetime DEFAULT NULL,
    `updated` datetime DEFAULT NULL,
    PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `payment` (
    `id` varchar(50) NOT NULL,
    `status` varchar(20) NOT NULL,
    `order_id` varchar(50) NOT NULL,
    `product_name` varchar(80) DEFAULT NULL,
    `pay_no` varchar(80) DEFAULT NULL,
    `trade_no` varchar(80) DEFAULT NULL,
    `payer_uid` int(20) NOT NULL,
    `payer_name` varchar(50) DEFAULT NULL,
    `payer_amount` decimal(10,2) NOT NULL,
    `order_amount` decimal(10,2) NOT NULL,
    `pay_way` varchar(10) NOT NULL,
    `pay_success_time` datetime DEFAULT NULL,
    `complete_time` datetime DEFAULT NULL,
    `remark` varchar(500) DEFAULT NULL,
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
