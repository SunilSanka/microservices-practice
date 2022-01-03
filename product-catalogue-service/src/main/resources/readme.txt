# Table Structure
create table user(
	id integer not null,
	birth_date timestamp,
	name varchar(255),
	primary_key(id)
)