create table public.t_ipo_hk
(
    id                  bigserial    not null,
    name                VARCHAR(150) null,
    market              integer      null,
    code                varchar(20)  null,
    list_time           date         null,
    ipo_price_min       float8       null,
    ipo_price_max       float8       null,
    list_price          float8       null,
    lot_size            int          null,
    entrance_price      float8       null,
    is_subscribe_status smallint     null,
    apply_endTime       date         null
);

create unique index table_ipo_hk_id_unique on public.t_ipo_hk (id);
create unique index table_ipo_hk_code_unique on public.t_ipo_hk (code);
alter table public.t_ipo_hk
    add constraint table_ipo_hk_pk primary key (id);

comment on column public.t_ipo_hk.name is '股票名称';
comment on column public.t_ipo_hk.market is '市场';
comment on column public.t_ipo_hk.code is '股票代码';
comment on column public.t_ipo_hk.list_time is '上市日期';
comment on column public.t_ipo_hk.ipo_price_min is '最低发售价';
comment on column public.t_ipo_hk.ipo_price_max is '最高发售价';
comment on column public.t_ipo_hk.list_price is '上市价';
comment on column public.t_ipo_hk.lot_size is '每手股数';
comment on column public.t_ipo_hk.entrance_price is '入场费';
comment on column public.t_ipo_hk.is_subscribe_status is '是否为认购状态，True-认购中，False-待上市';
comment on column public.t_ipo_hk.apply_endTime is '截止认购日期字符串';

create table public.t_ipo_cn
(
    id                        bigserial    not null,
    name                      VARCHAR(150) null,
    market                    integer      null,
    code                      varchar(20)  null,
    list_time                 date         null,
    apply_code                varchar(20)  null,
    issue_size                bigint       null,
    online_issue_size         bigint       null,
    apply_upper_limit         bigint       null,
    apply_limit_market_value  bigint       null,
    is_estimate_ipo_price     smallint     null,
    ipo_price                 float8       null,
    industry_pe_rate          float8       null,
    is_estimate_winning_ratio smallint     null,
    winning_ratio             float8       null,
    issue_pe_rate             float8       null,
    apply_time                date         null,
    winning_time              date         null,
    is_has_won                smallint     null
);

create unique index table_ipo_cn_id_unique on public.t_ipo_cn (id);
create unique index table_ipo_cn_code_unique on public.t_ipo_cn (code, market);
alter table public.t_ipo_cn
    add constraint table_ipo_cn_pk primary key (id);

comment on column public.t_ipo_cn.name is '股票名称';
comment on column public.t_ipo_cn.market is '市场';
comment on column public.t_ipo_cn.code is '股票代码';
comment on column public.t_ipo_cn.list_time is '上市日期';
comment on column public.t_ipo_cn.apply_code is '申购代码';
comment on column public.t_ipo_cn.issue_size is '发行总数';
comment on column public.t_ipo_cn.online_issue_size is '网上发行量';
comment on column public.t_ipo_cn.apply_upper_limit is '申购上限';
comment on column public.t_ipo_cn.apply_limit_market_value is '顶格申购需配市值';
comment on column public.t_ipo_cn.is_estimate_ipo_price is '是否预估发行价';
comment on column public.t_ipo_cn.ipo_price is '发行价 预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。';
comment on column public.t_ipo_cn.industry_pe_rate is '行业市盈率';
comment on column public.t_ipo_cn.is_estimate_winning_ratio is '是否预估中签率';
comment on column public.t_ipo_cn.winning_ratio is '中签率 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。';
comment on column public.t_ipo_cn.issue_pe_rate is '发行市盈率';
comment on column public.t_ipo_cn.apply_time is '申购日期字符串';
comment on column public.t_ipo_cn.winning_time is '公布中签日期字符串';
comment on column public.t_ipo_cn.is_has_won is '是否已经公布中签号';

create
    table public.t_ipo_cn_ex_winning
(
    id           bigserial not null,
    winning_name varchar   null,
    winning_info varchar   null,
    ipo_cn_id    bigint    null
);
create unique index table_ipo_cn_winning_id_unique on public.t_ipo_cn_ex_winning (id);
alter table public.t_ipo_cn_ex_winning
    add constraint table_ipo_cn_winning_pk primary key (id);

comment on column public.t_ipo_cn_ex_winning.winning_name is '分组名';
comment on column public.t_ipo_cn_ex_winning.winning_info is '中签号信息';

create table public.t_ipo_us
(
    id            bigserial    not null,
    name          VARCHAR(150) null,
    market        integer      null,
    code          varchar(20)  null,
    list_time     date         null,
    ipo_price_min float8       null,
    ipo_price_max float8       null,
    issue_size    bigint       null
);

create unique index table_ipo_us_id_unique on public.t_ipo_us (id);
create unique index table_ipo_us_code_unique on public.t_ipo_us (code);
alter table public.t_ipo_us
    add constraint table_ipo_us_pk primary key (id);

comment on column public.t_ipo_us.name is '股票名称';
comment on column public.t_ipo_us.market is '市场';
comment on column public.t_ipo_us.code is '股票代码';
comment on column public.t_ipo_us.list_time is '上市日期';
comment on column public.t_ipo_us.ipo_price_min is '最低发行价';
comment on column public.t_ipo_us.ipo_price_max is '最高发行价';
comment on column public.t_ipo_us.issue_size is '发行量';
