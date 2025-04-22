drop table users;

CREATE TABLE users
	(username		varchar(25),
	password		varbinary(16));

alter table users	
	add constraint user_username_pk primary key(username);
