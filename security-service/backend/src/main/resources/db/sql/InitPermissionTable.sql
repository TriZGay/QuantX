create table public.t_permission
(
    id          bigserial    not null,
    name        varchar(64)  null,
    type        smallint     null,
    tag         varchar(128) not null,
    method      varchar(10)  null,
    menu_id     bigint       null,
    create_time timestamp    null,
    modify_time timestamp    null
);

alter table public.t_permission
    add constraint table_permission_pk primary key (id);