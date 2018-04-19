insert into `user`
(`username`, `password`)
values
	('Szymoo0', '1234567'),
	('user1', 'password'),
	('user2', 'password'),
	('user3', 'password'),
	('user4', 'password');

insert into `post` 
(`title`, `content`, `creation_time`, `last_modification_time`, `author_username`)
values
	('Some sample article', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur iaculis posuere ante, sit amet consequat turpis convallis vel. Sed tempor dignissim mollis. Nulla in felis sodales odio luctus mollis a sed orci. Nunc vulputate imperdiet bibendum. Cras orci est, lobortis ac tortor a, pellentesque sollicitudin sapien. Vestibulum tempus metus non ligula vulputate, ullamcorper pharetra nisl elementum. Aliquam lacus diam, fringilla a nisi vitae, suscipit consectetur ex. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent bibendum lorem imperdiet varius euismod. Praesent sodales rhoncus ante, ut commodo urna tincidunt sit amet. Donec posuere ultricies nisl, ac rutrum augue commodo et.', '2018-04-05 12:11:33', '2018-04-05 12:11:33', 'Szymoo0'),
	('Another one article', 'Sed sagittis ullamcorper massa, ac commodo leo aliquet in. Aliquam vitae dapibus ante, eu semper mauris. Aenean lorem libero, venenatis at aliquet id, gravida et nulla. Ut quis odio dui. Sed vulputate nibh magna, vitae dapibus massa molestie ac. Curabitur id augue eget ligula viverra porta non rhoncus eros. Aliquam in vulputate enim. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Vestibulum fringilla enim tellus, in vulputate lacus lacinia nec. Praesent suscipit fringilla arcu, et malesuada urna iaculis pharetra. Aliquam porttitor vulputate sollicitudin. Nullam sodales tortor odio, consectetur bibendum ligula porta eu. Suspendisse efficitur nisi fringilla, vulputate tortor in, venenatis elit.', '2018-04-07 11:50:21', '2018-04-07 11:50:21', 'Szymoo0'),
	('Paleo tousled single-origin coffee.', 'Portland disrupt tilde, occupy gastropub sartorial 90''s kickstarter. Salvia listicle XOXO selvage, vegan umami raw denim. Hexagon wolf art party, readymade ennui yr lyft ugh poke tousled glossier enamel pin godard etsy. Semiotics hoodie yuccie, lumbersexual art party typewriter +1 cold-pressed gentrify flannel austin beard trust fund. Tbh schlitz aesthetic pickled. Schlitz quinoa knausgaard, occupy synth affogato kickstarter kogi brunch wayfarers pop-up. Lyft photo booth fashion axe kickstarter pour-over, franzen put a bird on it kale chips roof party brooklyn cred cronut whatever.', '2018-04-08 16:50:21', '2018-04-08 16:50:21', 'user1'),
	('Iceland everyday carry waistcoat.', 'Health goth succulents bespoke meh. Air plant selfies live-edge pitchfork, tumeric helvetica ugh letterpress try-hard austin mixtape. Kale chips cold-pressed tumblr man bun health goth letterpress offal palo santo blog truffaut williamsburg selfies. Whatever blue bottle you probably haven''t heard of them, brooklyn sartorial jean shorts knausgaard shoreditch asymmetrical farm-to-table austin single-origin coffee humblebrag poke. Cred VHS waistcoat vinyl vaporware semiotics keytar, YOLO truffaut keffiyeh woke mixtape. Four dollar toast beard scenester aesthetic, meditation ennui gluten-free.', '2018-04-09 15:12:01', '2018-04-09 16:00:51', 'user2'),
	('Authentic tbh squid blue bottle.', 'Chambray swag small batch coloring book snackwave actually migas bitters la croix kickstarter af sriracha. Taxidermy schlitz art party waistcoat live-edge brunch. Vegan 8-bit sartorial palo santo pinterest church-key put a bird on it austin gluten-free hell of tattooed. Pork belly activated charcoal snackwave williamsburg tbh migas.', '2018-04-11 09:12:11', '2018-04-11 09:12:11', 'user3'),
	('Adaptogen XOXO cred pabst.', 'Sriracha drinking vinegar polaroid copper mug semiotics. Farm-to-table shabby chic lo-fi freegan banjo pug VHS kogi poutine. Ethical everyday carry lomo, dreamcatcher tofu bespoke meggings copper mug. Letterpress art party banh mi mixtape, poutine blue bottle skateboard kombucha bitters flexitarian viral master cleanse air plant shaman.', '2018-04-13 22:15:03', '2018-04-14 00:05:18', 'user4'),
	('Locavore skateboard gastropub.', 'Keffiyeh street art pabst, you probably haven''t heard of them salvia yr gluten-free. Meh put a bird on it kombucha raclette XOXO hell of austin. Everyday carry unicorn man bun, poke actually fingerstache heirloom williamsburg bicycle rights. Iceland pinterest typewriter, seitan literally small batch vinyl locavore salvia hoodie tbh hot chicken vaporware unicorn pitchfork. Gochujang green juice flannel chicharrones everyday carry.', '2018-04-14 03:12:41', '2018-04-14 03:12:41', 'user2'),
	('Paleo leggings glossier.', 'Mixtape air plant brooklyn, chicharrones austin fanny pack master cleanse cray paleo. Food truck copper mug gochujang chambray put a bird on it swag drinking vinegar godard edison bulb photo booth bushwick slow-carb. Tattooed iceland cloud bread yuccie bushwick selfies bespoke helvetica. Cred williamsburg swag, pitchfork unicorn jean shorts letterpress tofu. Man bun meggings drinking vinegar mumblecore. Pinterest raclette shoreditch, messenger bag raw denim marfa cronut sriracha fixie knausgaard slow-carb. Tbh wolf vegan try-hard intelligentsia art party flannel migas actually.', '2018-04-14 04:15:34', '2018-04-14 04:15:34', 'Szymoo0'),
	('Scenester ugh tumblr.', 'Scenester salvia keffiyeh chia vape, viral four loko slow-carb humblebrag crucifix. Ramps snackwave glossier mumblecore. Selvage truffaut scenester swag subway tile VHS. Fixie everyday carry flexitarian try-hard.','2018-04-15 12:55:28', '2018-04-19 18:43:51', 'user4');

insert into `user_reaction`
(`username`, `post_id`, `reaction_type`)
values
	('user2', 9, 1),
	('user3', 9, 0),
	('user4', 9, 0),
	('Szymoo0', 8, 1),
	('user1', 8, 1),
	('user2', 8, 1),
	('user3', 8, 1),
	('user4', 8, 1),
	('user3', 7, 0),
	('user4', 7, 0),
	('user1', 6, 0),
	('user2', 6, 1),
	('user4', 6, 1),
	('Szymoo0', 5, 0),
	('user1', 5, 1),
	('Szymoo0', 4, 0),
	('user3', 4, 0),
	('user4', 4, 1),
	('user1', 3, 0),
	('user2', 3, 1),
	('user4', 3, 1),
	('Szymoo0', 2, 0),
	('user1', 2, 0),
	('user2', 2, 0),
	('user3', 2, 0),
	('user4', 2, 0),
	('Szymoo0', 1, 0),
	('user1', 1, 0),
	('user2', 1, 1),
	('user3', 1, 0),
	('user4', 1, 0);
	