create table public.t_menu
(
    id          bigserial    not null,
    name        varchar(64)  null,
    parent_id   bigint       null,
    path        varchar(128) null,
    component   varchar(128) null,
    redirect    varchar(128) null,
    icon        varchar(64)  null,
    visible     smallint     null,
    create_time timestamp    null,
    modify_time timestamp    null
);

alter table public.t_menu
    add constraint table_menu_pk primary key (id);