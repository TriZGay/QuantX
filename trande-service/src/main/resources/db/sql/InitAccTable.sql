create table public.t_acc
(
    id                     bigserial    not null,
    trade_env              int          null,
    acc_id                 VARCHAR(100) null,
    trade_market_auth_list varchar(20)  null,
    acc_type               int          null,
    card_num               varchar(100) null,
    firm                   int          null
);

alter table public.t_acc
    add constraint table_acc_pk primary key (id);