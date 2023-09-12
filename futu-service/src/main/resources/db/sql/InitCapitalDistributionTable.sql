create table public.t_capital_distribution
(
    id                bigserial        not null,
    market            integer          null,
    code              varchar(20)      null,
    capital_in_super  double precision null,
    capital_in_big    double precision null,
    capital_in_mid    double precision null,
    capital_in_small  double precision null,
    capital_out_super double precision null,
    capital_out_big   double precision null,
    capital_out_mid   double precision null,
    capital_out_small double precision null,
    update_time       timestamp        null
);

comment on column public.t_capital_distribution.capital_in_super is '流入资金额度，特大单';
comment on column public.t_capital_distribution.capital_in_big is '流入资金额度，大单';
comment on column public.t_capital_distribution.capital_in_mid is '流入资金额度，中单';
comment on column public.t_capital_distribution.capital_in_small is '流入资金额度，小单';
comment on column public.t_capital_distribution.capital_out_super is '流出资金额度，特大单';
comment on column public.t_capital_distribution.capital_out_big is '流出资金额度，大单';
comment on column public.t_capital_distribution.capital_out_mid is '流出资金额度，中单';
comment on column public.t_capital_distribution.capital_out_small is '流出资金额度，小单';
comment on column public.t_capital_distribution.update_time is '更新时间';

create unique index table_capital_distribution_id_unique on public.t_capital_distribution (id);
create unique index t_capital_distribution_code_market_uindex on public.t_capital_distribution (code, market);
alter table public.t_capital_distribution
    add constraint table_capital_distribution_pk primary key (id);