create table public.t_order
(
    id           bigserial    not null,
    acc_id       varchar(100) null,
    trade_env    int          null,
    trade_market int          null,
    order_id     varchar(100) null,
    create_time  date         null default now()
);

alter table public.t_order
    add constraint table_order_pk primary key (id);