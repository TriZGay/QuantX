create table public.t_user
(
    id          bigserial    not null,
    username    varchar(64)  not null,
    password    varchar(100) not null,
    status      smallint     null,
    create_time timestamp    null,
    modify_time timestamp    null
);

alter table public.t_user
    add constraint table_user_pk primary key (id);