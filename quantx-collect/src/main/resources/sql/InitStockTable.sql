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
    suspension        smallint    null,
    listing_date      varchar(12) null,
    stock_id          bigint      null,
    delisting         smallint    null,
    index_option_type varchar(20) null,
    main_contract     smallint    null,
    last_trade_time   varchar(20) null,
    exchange_type     varchar(20) null
);

create unique index table_stock_id_unique on public.t_stock (id);
alter table public.t_stock
    add constraint table_stock_pk primary key (id);
