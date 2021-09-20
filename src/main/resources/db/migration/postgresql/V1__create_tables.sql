create sequence user_id_seq start with 10 increment by 2;
create sequence tweet_id_seq start with 10 increment by 2;

create table users (
    id bigint DEFAULT nextval('user_id_seq') not null,
    username varchar(255) not null CONSTRAINT user_username_unique UNIQUE,
    password varchar(255) not null,
    name varchar(255) not null,
    role varchar (255) not null,
    primary key (id)
);

create table tweet (
       id bigint DEFAULT nextval('tweet_id_seq') not null,
       tweet_data varchar(255) not null,
       create_date date,
       modify_date date,
       userid bigint,
       FOREIGN KEY (userid) REFERENCES USERS(id) ,
       primary key (id)
);
