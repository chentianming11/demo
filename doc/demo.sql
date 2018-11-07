CREATE TABLE `T_BLOG_ARTICLE` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`collection_id` int(11) NOT NULL DEFAULT 0,
`content` text NOT NULL,
`title` varchar(255) NOT NULL DEFAULT '',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
`is_valid` tinyint(4) NOT NULL DEFAULT 0,
PRIMARY KEY (`id`)
)
CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `T_BLOG_COLLECTION` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`user_id` int(11) NOT NULL DEFAULT 0,
`name` varchar(100) NOT NULL DEFAULT '',
`description` varchar(255) NOT NULL DEFAULT '',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (`id`)
)
CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



CREATE TABLE `T_BLOG_USER` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`username` varchar(50) NOT NULL DEFAULT '',
`password` varchar(50) NOT NULL DEFAULT '',
`phone` varchar(50) NOT NULL DEFAULT '',
`email` varchar(50) NOT NULL DEFAULT '',
`head_url` varchar(255) NOT NULL DEFAULT '',
`description` varchar(255) NOT NULL DEFAULT '',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
`birthday` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (`id`)
)
CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `t_common_uv` (
`id` bigint(11) NOT NULL,
`page_view_uicode` varchar(255) NOT NULL DEFAULT  '',
`count` int NOT NULL DEFAULT 0,
`ctime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
`date` datetime NOT NULL,
PRIMARY KEY (`id`)
)
CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;






