create table public.t_acc_info
(
    id                  bigserial        not null,
    acc_id              VARCHAR(100)     null,
    power               double precision null,
    total_assets        double precision null,
    cash                double precision null,
    market_val          double precision null,
    frozen_cash         double precision null,
    debt_cash           double precision null,
    avl_withdrawal_cash double precision null,
    currency            int              null,
    available_funds     double precision null,
    unrealized_pl       double precision null,
    realized_pl         double precision null,
    risk_level          int              null,
    initial_margin      double precision null,
    maintenance_margin  double precision null,
    cash_info_list      varchar(20)      null,
    max_power_short     double precision null,
    net_cash_power      double precision null,
    long_mv             double precision null,
    short_mv            double precision null,
    pending_asset       double precision null,
    max_withdrawal      double precision null,
    risk_status         int              null,
    margin_call_margin  double precision null,
    is_pdt              smallint         null,
    pdt_seq             varchar(20)      null,
    beginning_dtbp      double precision null,
    remaining_dtbp      double precision null,
    dt_call_amount      double precision null,
    dt_status           int              null
);

comment on column public.t_acc_info.acc_id is '业务账号';
comment on column public.t_acc_info.power is '最大购买力（此字段是按照 50% 的融资初始保证金率计算得到的 近似值。但事实上，每个标的的融资初始保证金率并不相同。我们建议您使用 查询最大可买可卖 接口返回的 最大可买 字段，来判断实际可买入的最大数量）';
comment on column public.t_acc_info.total_assets is '资产净值';
comment on column public.t_acc_info.cash is '现金';
comment on column public.t_acc_info.market_val is '证券市值, 仅证券账户适用';
comment on column public.t_acc_info.frozen_cash is '冻结资金';
comment on column public.t_acc_info.debt_cash is '计息金额';
comment on column public.t_acc_info.avl_withdrawal_cash is '现金可提，仅证券账户适用';
comment on column public.t_acc_info.currency is '币种，本结构体资金相关的货币类型';
comment on column public.t_acc_info.available_funds is '可用资金，期货适用';
comment on column public.t_acc_info.unrealized_pl is '未实现盈亏，期货适用';
comment on column public.t_acc_info.realized_pl is '已实现盈亏，期货适用';
comment on column public.t_acc_info.risk_level is '风控状态';
comment on column public.t_acc_info.initial_margin is '初始保证金';
comment on column public.t_acc_info.maintenance_margin is '维持保证金';
comment on column public.t_acc_info.cash_info_list is '分币种的现金信息';
comment on column public.t_acc_info.max_power_short is '卖空购买力（此字段是按照 60% 的融券保证金率计算得到的近似值。但事实上，每个标的的融券保证金率并不相同。我们建议您使用 查询最大可买可卖 接口返回的 可卖空 字段，来判断实际可卖空的最大数量。）';
comment on column public.t_acc_info.net_cash_power is '现金购买力';
comment on column public.t_acc_info.long_mv is '多头市值';
comment on column public.t_acc_info.short_mv is '空头市值';
comment on column public.t_acc_info.pending_asset is '在途资产';
comment on column public.t_acc_info.max_withdrawal is '融资可提，仅证券账户适用';
comment on column public.t_acc_info.risk_status is '风险状态';
comment on column public.t_acc_info.margin_call_margin is 'Margin Call 保证金';

comment on column public.t_acc_info.is_pdt is '是否PDT账户，仅富途证券（美国）账户适用 (使用范围下同)';
comment on column public.t_acc_info.pdt_seq is '剩余日内交易次数';
comment on column public.t_acc_info.beginning_dtbp is '初始日内交易购买力';
comment on column public.t_acc_info.remaining_dtbp is '剩余日内交易购买力';
comment on column public.t_acc_info.dt_call_amount is '日内交易待缴金额';
comment on column public.t_acc_info.dt_status is '日内交易限制情况';

alter table public.t_acc_info
    add constraint table_acc_info_pk primary key (id);