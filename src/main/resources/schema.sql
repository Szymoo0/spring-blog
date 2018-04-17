drop table if exists `post`;
drop table if exists `user`;
drop table if exists `user_reaction`;

create table `user` 
(
	`username` varchar(128) not null primary key,
	`password` varchar(128) not null,
	`avatar_url` varchar(512),
	`enabled` tinyint(1) default 1
);

create table `post` 
(
	`id` int not null auto_increment primary key,
	`title` clob not null,
	`content` clob not null,
	`creation_time` timestamp not null,
	`last_modification_time` timestamp not null,
	`author_username` varchar(128) not null,
	`image` varchar(512),
	constraint FK_UserPost foreign key (`author_username`)
	references user(username)
);

create table `user_reaction` 
(
	`username` varchar(128) not null,
	`post_id` int not null,
	`reaction` int,
	constraint FK_UserUserReaction foreign key (`username`) references user(`username`),
	constraint FK_PostUserReaction foreign key (`post_id`) references post(`id`)
);
