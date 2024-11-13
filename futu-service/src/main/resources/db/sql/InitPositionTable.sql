create table public.t_position
(
    id               bigserial        not null,
    trade_env        int              null,
    acc_id           varchar(100)     null,
    acc_trade_market int              null,
    position_id      bigint           null,
    position_side    int              null,
    code             varchar(20)      null,
    name             varchar(100)     null,
    qty              double precision null,
    can_sell_qty     double precision null,
    price            double precision null,
    cost_price       double precision null,
    val              double precision null,
    pl_val           double precision null,
    pl_ratio         double precision null,
    security_market  int              null,
    td_pl_val        double precision null,
    td_trd_val       double precision null,
    td_buy_val       double precision null,
    td_buy_qty       double precision null,
    td_sell_val      double precision null,
    td_sell_qty      double precision null,
    unrealized_pl    double precision null,
    realized_pl      double precision null,
    currency         int              null,
    trade_market     int              null
);

comment on column public.t_position.position_id is '持仓 ID，一条持仓的唯一标识';
comment on column public.t_position.position_side is '持仓方向';
comment on column public.t_position.code is '股票代码';
comment on column public.t_position.name is '股票名称F';
comment on column public.t_position.qty is '持有数量，2位精度，期权单位是"张"，下同';
comment on column public.t_position.can_sell_qty is '可用数量，是指持有的可平仓的数量。可用数量=持有数量-冻结数量。期权和期货的单位是“张”';
comment on column public.t_position.price is '市价，3位精度，期货为2位精度';
comment on column public.t_position.cost_price is '摊薄成本价（证券账户），平均开仓价（期货账户）。证券无精度限制，期货为2位精度，如果没传，代表此时此值无效';
comment on column public.t_position.val is '市值，3位精度, 期货此字段值为0';
comment on column public.t_position.pl_val is '盈亏金额，3位精度，期货为2位精度';
comment on column public.t_position.pl_ratio is '盈亏百分比(如 plRatio 等于0.088代表涨8.8%)，无精度限制，如果没传，代表此时此值无效';
comment on column public.t_position.security_market is '证券所属市场';
comment on column public.t_position.td_pl_val is '今日盈亏金额，3位精度，下同, 期货为2位精度';
comment on column public.t_position.td_trd_val is '今日交易额，期货不适用';
comment on column public.t_position.td_buy_val is '今日买入总额，期货不适用';
comment on column public.t_position.td_buy_qty is '今日买入总量，期货不适用';
comment on column public.t_position.td_sell_val is '今日卖出总额，期货不适用';
comment on column public.t_position.td_sell_qty is '今日卖出总量，期货不适用';
comment on column public.t_position.unrealized_pl is '未实现盈亏（仅期货账户适用）';
comment on column public.t_position.realized_pl is '已实现盈亏（仅期货账户适用）	';
comment on column public.t_position.currency is '货币类型';
comment on column public.t_position.trade_market is '交易市场';

alter table public.t_position
    add constraint table_position_pk primary key (id);