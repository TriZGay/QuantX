create table public.t_acc
(
    id                     bigserial    not null,
    trade_env              int          null,
    acc_id                 VARCHAR(100) null,
    trade_market_auth_list varchar(20)  null,
    acc_type               int          null,
    card_num               varchar(100) null,
    firm                   int          null,
    sim_acc_type           int          null
);

comment on column public.t_acc.trade_env is '交易环境';
comment on column public.t_acc.acc_id is '业务账号';
comment on column public.t_acc.trade_market_auth_list is '业务账户支持的交易市场权限，即此账户能交易那些市场, 可拥有多个交易市场权限，目前仅单个';
comment on column public.t_acc.acc_type is '账户类型';
comment on column public.t_acc.card_num is '卡号';
comment on column public.t_acc.firm is '所属券商';
comment on column public.t_acc.sim_acc_type is '模拟交易账号类型';

alter table public.t_acc
    add constraint table_acc_pk primary key (id);