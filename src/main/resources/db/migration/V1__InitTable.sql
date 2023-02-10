DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`              bigint NOT NULL AUTO_INCREMENT,
    `email`           varchar(255)  DEFAULT NULL,
    `full_name`       varchar(255)  DEFAULT NULL,
    `password`        varchar(1000) DEFAULT NULL,
    `phone_number`    varchar(45)   DEFAULT NULL,
    `avatar_url`      varchar(1000) DEFAULT NULL,
    `birthday`        date          DEFAULT NULL,
    `height`          int           DEFAULT NULL,
    `weight`          int           DEFAULT NULL,
    `forte_position`  varchar(255)  DEFAULT NULL,
    `preferred_foot`  int           DEFAULT NULL,
    `join_date`       datetime      DEFAULT NULL,
    `last_login_date` datetime      DEFAULT NULL,
    `auth_provider`   varchar(255)  DEFAULT NULL,
    `status`          bit(1)        DEFAULT NULL,
    `delete_flag`     bit(1)        DEFAULT NULL,
    `role_id`         bigint NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `email_UNIQUE` (`email`)
);

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`   bigint NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`
(
    `id`          bigint NOT NULL,
    `description` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`
(
    `role_id`       bigint NOT NULL,
    `permission_id` bigint NOT NULL,
    PRIMARY KEY (`role_id`, `permission_id`)
);

DROP TABLE IF EXISTS `city`;
CREATE TABLE `city`
(
    `id`   bigint NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `district`;
CREATE TABLE `district`
(
    `id`      bigint NOT NULL,
    `name`    varchar(255) DEFAULT NULL,
    `city_id` bigint NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `detail`       varchar(255) DEFAULT NULL,
    `phone_number` varchar(255) DEFAULT NULL,
    `facebook`     varchar(255) DEFAULT NULL,
    `mail`         varchar(255) DEFAULT NULL,
    `latitude`     varchar(255) DEFAULT NULL,
    `longitude`    varchar(255) DEFAULT NULL,
    `district_id`  bigint NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `pitch`;
CREATE TABLE `pitch`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `name`         varchar(255)  DEFAULT NULL,
    `description`  text          DEFAULT NULL,
    `logo_url`     varchar(1000) DEFAULT NULL,
    `hour_start`   time          DEFAULT NULL,
    `hour_end`     time          DEFAULT NULL,
    `status`       int           DEFAULT NULL,
    `created_date` datetime      DEFAULT NULL,
    `updated_date` datetime      DEFAULT NULL,
    `address_id`   bigint NOT NULL,
    `owner_id`     bigint NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `mini_pitch`;
CREATE TABLE `mini_pitch`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `name`          varchar(255) DEFAULT NULL,
    `quantity`      int          DEFAULT NULL,
    `status`        bit(1)       DEFAULT NULL,
    `pitch_id`      bigint NOT NULL,
    `pitch_type_id` bigint NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `pitch_type`;
CREATE TABLE `pitch_type`
(
    `id`   bigint NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `image`;
CREATE TABLE `image`
(
    `id`       bigint NOT NULL AUTO_INCREMENT,
    `url`      varchar(1000) DEFAULT NULL,
    `pitch_id` bigint        DEFAULT NULL,
    `team_id`  bigint        DEFAULT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `cost`;
CREATE TABLE `cost`
(
    `id`               bigint NOT NULL AUTO_INCREMENT,
    `hour_start`       time DEFAULT NULL,
    `hour_end`         time DEFAULT NULL,
    `day_working_type` int  DEFAULT NULL,
    `price`            int  DEFAULT NULL,
    `mini_pitch_id`    bigint NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `review_pitch`;
CREATE TABLE `review_pitch`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `star`        int      DEFAULT NULL,
    `content`     text     DEFAULT NULL,
    `create_date` datetime DEFAULT NULL,
    `pitch_id`    bigint NOT NULL,
    `user_id`     bigint NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `team`;
CREATE TABLE `team`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `name`          varchar(255)  DEFAULT NULL,
    `description`   text          DEFAULT NULL,
    `logo_url`      varchar(1000) DEFAULT NULL,
    `status`        bit(1)        DEFAULT NULL,
    `recruit_flag`  bit(1)        DEFAULT NULL,
    `created_date`  datetime      DEFAULT NULL,
    `updated_date`  datetime      DEFAULT NULL,
    `level_id`      bigint NOT NULL,
    `address_id`    bigint NOT NULL,
    `home_pitch_id` bigint        DEFAULT NULL,
    `team_boss_id`  bigint NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `team_player`;
CREATE TABLE `team_player`
(
    `team_id`   bigint NOT NULL,
    `player_id` bigint NOT NULL,
    PRIMARY KEY (`team_id`, `player_id`)
);

DROP TABLE IF EXISTS `level`;
CREATE TABLE `level`
(
    `id`   bigint NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `team_register`;
CREATE TABLE `team_register`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `team_id`       bigint NOT NULL,
    `player_id`     bigint NOT NULL,
    `created_date`  datetime DEFAULT NULL,
    `approval_flag` bit(1)   DEFAULT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `invitation`;
CREATE TABLE `invitation`
(
    `id`              bigint NOT NULL AUTO_INCREMENT,
    `message`         text     DEFAULT NULL,
    `time`            datetime DEFAULT NULL,
    `invitation_type` int      DEFAULT NULL,
    `status`          int      DEFAULT NULL,
    `created_date`    datetime DEFAULT NULL,
    `updated_date`    datetime DEFAULT NULL,
    `team_id`         bigint NOT NULL,
    `city_id`         bigint NOT NULL,
    `district_id`     bigint   DEFAULT NULL,
    `competitor_id`   bigint   DEFAULT NULL,
    `pitch_id`        bigint   DEFAULT NULL,
    `pitch_type_id`   bigint   DEFAULT NULL,
    `match_id`        bigint   DEFAULT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `matches`;
CREATE TABLE `matches`
(
    `id`              bigint NOT NULL AUTO_INCREMENT,
    `home_team_id`    bigint NOT NULL,
    `away_team_id`    bigint NOT NULL,
    `home_team_score` int      DEFAULT NULL,
    `away_team_score` int      DEFAULT NULL,
    `pitch_id`        bigint NOT NULL,
    `time`            datetime DEFAULT NULL,
    `status`          int      DEFAULT NULL,
    `created_date`    datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `booking`;
CREATE TABLE `booking`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `time_start`    datetime DEFAULT NULL,
    `time_end`      datetime DEFAULT NULL,
    `price`         int      DEFAULT NULL,
    `payment`       int      DEFAULT NULL,
    `note`          text     DEFAULT NULL,
    `status`        int      DEFAULT NULL,
    `user_id`       bigint NOT NULL,
    `mini_pitch_id` bigint NOT NULL,
    PRIMARY KEY (`id`)
);