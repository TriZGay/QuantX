create table public.t_role
(
    id          bigserial   not null,
    name        varchar(64) null,
    status      int         null,
    create_time timestamp   null,
    modify_time timestamp   null
);

alter table public.t_role
    add constraint table_role_pk primary key (id);