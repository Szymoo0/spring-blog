insert into `user`
(`username`, `password`)
values
	('Szymoo0', '1234567'),
	('user123', 'password');

insert into `post` 
(`title`, `content`, `creation_time`, `last_modification_time`, `author_username`)
values
	('Some sample article', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur iaculis posuere ante, sit amet consequat turpis convallis vel. Sed tempor dignissim mollis. Nulla in felis sodales odio luctus mollis a sed orci. Nunc vulputate imperdiet bibendum. Cras orci est, lobortis ac tortor a, pellentesque sollicitudin sapien. Vestibulum tempus metus non ligula vulputate, ullamcorper pharetra nisl elementum. Aliquam lacus diam, fringilla a nisi vitae, suscipit consectetur ex. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent bibendum lorem imperdiet varius euismod. Praesent sodales rhoncus ante, ut commodo urna tincidunt sit amet. Donec posuere ultricies nisl, ac rutrum augue commodo et.', '2018-04-05 12:11:33', '2018-04-05 12:11:33', 'Szymoo0'),
	('Another one article', 'Sed sagittis ullamcorper massa, ac commodo leo aliquet in. Aliquam vitae dapibus ante, eu semper mauris. Aenean lorem libero, venenatis at aliquet id, gravida et nulla. Ut quis odio dui. Sed vulputate nibh magna, vitae dapibus massa molestie ac. Curabitur id augue eget ligula viverra porta non rhoncus eros. Aliquam in vulputate enim. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Vestibulum fringilla enim tellus, in vulputate lacus lacinia nec. Praesent suscipit fringilla arcu, et malesuada urna iaculis pharetra. Aliquam porttitor vulputate sollicitudin. Nullam sodales tortor odio, consectetur bibendum ligula porta eu. Suspendisse efficitur nisi fringilla, vulputate tortor in, venenatis elit.', '2018-04-05 11:50:21', '2018-04-05 11:50:21', 'Szymoo0');

insert into `user_reaction`
(`username`, `post_id`, `reaction`)
values
	('Szymoo0', 1, 1),
	('user123', 1, 1),
	('Szymoo0', 2, 0),
	('user123', 2, 1);