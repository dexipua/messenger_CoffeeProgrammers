create table accounts
(
    id       int generated by default as identity,
    role     smallint     not null,
    email    varchar(255) not null,
    password varchar(255) not null,
    last_name varchar(255) not null,
    first_name varchar(255) not null,
    description varchar(70),
    status smallint not null,
    primary key (id)
)