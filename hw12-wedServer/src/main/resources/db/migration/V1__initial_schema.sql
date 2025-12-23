-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
create sequence client_seq start with 1 increment by 1;

create table client
(
    id   bigint not null primary key,
    name varchar(50)
);

create table address
(
    id        bigserial not null primary key,
    street    varchar(255),
    client_id bigint unique,
    constraint fk_address_client foreign key (client_id) references client (id) on delete cascade
);

create table phone
(
    id        bigserial not null primary key,
    number    varchar(50),
    client_id bigint,
    constraint fk_phone_client foreign key (client_id) references client (id) on delete cascade
);
