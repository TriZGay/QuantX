CREATE TABLE public.t_stock
(
    id                bigserial        not null,
    name              varchar(150)     null,
    market            integer          null,
    code              varchar(20)      null,
    lot_size          int              null,
    stock_type        integer          null,
    stock_child_type  integer          null,
    stock_owner       varchar(20)      null,
    option_type       integer          null,
    strike_time       varchar(20)      null,
    strike_price      double precision null,
    option_market     varchar          null,
    suspension        boolean          null,
    listing_date      date             null,
    stock_id          varchar(50)      null,
    delisting         smallint         null,
    index_option_type integer          null,
    main_contract     smallint         null,
    last_trade_time   date             null,
    exchange_type     integer          null
);

comment on column public.t_stock.name is '股票名称';
comment on column public.t_stock.code is '股票代码';
comment on column public.t_stock.market is '市场';
comment on column public.t_stock.lot_size is '每手数量,期权类型表示一份合约的股数';
comment on column public.t_stock.stock_type is '股票类型';
comment on column public.t_stock.stock_child_type is '股票子类型,如窝轮类型';
comment on column public.t_stock.stock_owner is '拥有者,如窝轮拥有者是某只股票';
comment on column public.t_stock.option_type is '期权类型';
comment on column public.t_stock.strike_time is '行权日';
comment on column public.t_stock.strike_price is '行权价';
comment on column public.t_stock.suspension is '是否停牌';
comment on column public.t_stock.listing_date is '上市时间(ft已停止维护)';
comment on column public.t_stock.stock_id is '股票id from ft';
comment on column public.t_stock.delisting is '是否退市';
comment on column public.t_stock.index_option_type is '指数期权的类型,仅在指数期权有效';
comment on column public.t_stock.main_contract is '是否主连合约';
comment on column public.t_stock.last_trade_time is '最后交易日,只有非主连期货合约才有该字段';
comment on column public.t_stock.exchange_type is '所属交易所';

create unique index table_stock_id_unique on public.t_stock (id);
create unique index t_stock_code_market_uindex on public.t_stock (code, market);
alter table public.t_stock
    add constraint table_stock_pk primary key (id);