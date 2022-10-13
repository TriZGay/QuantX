CREATE TABLE public.t_stock
(
    id                bigserial   not null,
    name              varchar(20) null,
    code              varchar(20) null,
    lot_size          int         null,
    stock_type        varchar(10) null,
    stock_child_type  varchar(10) null,
    stock_owner       varchar(20) null,
    option_type       varchar(10) null,
    strike_time       varchar(20) null,
    strike_price      float8      null,
    suspension        bool        null,
    listing_date      varchar(12) null,
    stock_id          bigint      null,
    delisting         bool        null,
    index_option_type varchar(20) null,
    main_contract     bool        null,
    last_trade_time   varchar(20) null,
    exchange_type     varchar(20) null,
    create_date       timestamp   null default now(),
    modify_date       timestamp   null
);

create unique index table_stock_id_unique on public.t_stock (id);
alter table public.t_stock
    add constraint table_stock_pk primary key (id);
