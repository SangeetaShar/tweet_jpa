create sequence user_id_seq start with 10 increment by 2;
create sequence tweet_id_seq start with 10 increment by 2;

create table users (
    id bigint default user_id_seq.nextval,
    username varchar(255) not null,
    password varchar(255) not null,
    name varchar(255) not null,
    role varchar (255) not null,
    primary key (id),
    UNIQUE KEY user_username_unique (username)
);


create table tweet (
   id bigint DEFAULT tweet_id_seq.nextval,
   tweet_data varchar(255) not null,
   create_date date,
   modify_date date,
   userid bigint,
   FOREIGN KEY (userid) REFERENCES USERS(id) ,
   primary key (id)
);

