create table public.t_order
(
    id               bigserial        not null,
    acc_id           varchar(100)     null,
    trade_env        int              null,
    acc_trade_market int              null,
    trade_side       int              null,
    order_type       int              null,
    order_status     int              null,
    order_id         varchar(100)     null,
    order_id_ex      varchar(200)     null,
    code             varchar(20)      null,
    name             varchar(100)     null,
    qty              double precision null,
    price            double precision null,
    create_time      timestamp        null,
    update_time      timestamp        null,
    fill_qty         double precision null,
    fill_avg_price   double precision null,
    last_err_msg     varchar          null,
    security_market  int              null,
    remark           varchar          null,
    aux_price        double precision null,
    trail_type       int              null,
    trail_value      double precision null,
    trail_spread     double precision null,
    currency         int              null,
    trade_market     int              null
);

comment on column public.t_order.trade_side is '交易方向';
comment on column public.t_order.order_type is '订单类型';
comment on column public.t_order.order_status is '订单状态';
comment on column public.t_order.order_id is '订单号';
comment on column public.t_order.order_id_ex is '扩展订单号(仅查问题时备用)';
comment on column public.t_order.code is '代码';
comment on column public.t_order.name is '名称';
comment on column public.t_order.qty is '订单数量，2位精度，期权单位是"张"';
comment on column public.t_order.price is '订单价格，3位精度';
comment on column public.t_order.create_time is '创建时间，严格按 YYYY-MM-DD HH:MM:SS 或 YYYY-MM-DD HH:MM:SS.MS 格式传,下同';
comment on column public.t_order.update_time is '更新时间';
comment on column public.t_order.fill_qty is '成交数量，2位精度，期权单位是"张"';
comment on column public.t_order.fill_avg_price is '成交均价，无精度限制';
comment on column public.t_order.last_err_msg is '最后的错误描述，如果有错误，会有此描述最后一次错误的原因，无错误为空';
comment on column public.t_order.security_market is '证券所属市场';
comment on column public.t_order.remark is '用户备注字符串';
comment on column public.t_order.aux_price is '触发价格';
comment on column public.t_order.trail_type is '跟踪类型';
comment on column public.t_order.trail_value is '跟踪金额/百分比';
comment on column public.t_order.trail_spread is '指定价差';
comment on column public.t_order.currency is '货币类型';
comment on column public.t_order.trade_market is '交易市场';

alter table public.t_order
    add constraint table_order_pk primary key (id);