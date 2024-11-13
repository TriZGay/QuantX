create table public.t_trade_date
(
    id                 bigserial   not null,
    market_or_security varchar(20) not null,
    time               varchar(20) not null,
    trade_date_type    smallint    not null
);

alter table public.t_trade_date
    add constraint table_trade_date_pk primary key (id);