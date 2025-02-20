CREATE TABLE public.t_snapshot_base
(
    id                     bigserial not null,
    market                 integer   null,
    code                   varchar   null,
    name                   varchar   null,
    type                   integer   null,
    is_suspend             boolean   null,
    list_time              date      null,
    lot_size               integer   null,
    price_spread           float8    null,
    update_time            timestamp null,
    high_price             float8    null,
    open_price             float8    null,
    low_price              float8    null,
    last_close_price       float8    null,
    cur_price              float8    null,
    volume                 bigint    null,
    turnover               float8    null,
    turnover_rate          float8    null,
    ask_price              float8    null,
    bid_price              float8    null,
    ask_vol                bigint    null,
    bid_vol                bigint    null,
    amplitude              float8    null,
    avg_price              float8    null,
    bid_ask_ratio          float8    null,
    volume_ratio           float8    null,
    highest_52_weeks_price float8    null,
    lowest_52_weeks_price  float8    null,
    highest_history_price  float8    null,
    lowest_history_price   float8    null,
    pre_price              float8    null,
    pre_high_price         float8    null,
    pre_low_price          float8    null,
    pre_volume             bigint    null,
    pre_turnover           float8    null,
    pre_change_val         float8    null,
    pre_change_rate        float8    null,
    pre_amplitude          float8    null,
    after_price            float8    null,
    after_high_price       float8    null,
    after_low_price        float8    null,
    after_volume           bigint    null,
    after_turnover         float8    null,
    after_change_val       float8    null,
    after_change_rate      float8    null,
    after_amplitude        float8    null,
    sec_status             integer   null,
    close_price_5_minute   float8    null
);
create unique index table_snapshot_base_id_unique on public.t_snapshot_base (id);
create unique index t_snapshot_base_code_market_uindex on public.t_snapshot_base (code, market);
alter table public.t_snapshot_base
    add constraint table_snapshot_base_pk primary key (id);

CREATE TABLE public.t_snapshot_equity_ex
(
    id                     bigserial not null,
    market                 integer   null,
    code                   varchar   null,
    issued_shares          bigint    null,
    issued_market_val      float8    null,
    net_asset              float8    null,
    net_profit             float8    null,
    earnings_per_share     float8    null,
    outstanding_shares     bigint    null,
    outstanding_market_val float8    null,
    net_asset_per_share    float8    null,
    ey_rate                float8    null,
    pe_rate                float8    null,
    pb_rate                float8    null,
    pe_ttm_rate            float8    null,
    dividend_ttm           float8    null,
    dividend_ratio_ttm     float8    null,
    dividend_lfy           float8    null,
    dividend_lfy_ratio     float8    null
);
create unique index table_snapshot_equity_id_unique on public.t_snapshot_equity_ex (id);
create unique index t_snapshot_equity_code_market_uindex on public.t_snapshot_equity_ex (code, market);
alter table public.t_snapshot_equity_ex
    add constraint table_snapshot_equity_pk primary key (id);

create table public.t_snapshot_warrant_ex
(
    id                   bigserial not null,
    owner_market         integer   null,
    owner_code           varchar   null,
    conversion_rate      float8    null,
    warrant_type         integer   null,
    strike_price         float8    null,
    maturity_time        varchar   null,
    end_trade_time       varchar   null,
    recovery_price       float8    null,
    street_volumn        bigint    null,
    issue_volumn         bigint    null,
    street_rate          float8    null,
    delta                float8    null,
    implied_volatility   float8    null,
    premium              float8    null,
    maturity_timestamp   timestamp null,
    end_trade_timestamp  timestamp null,
    leverage             float8    null,
    ipop                 float8    null,
    break_event_point    float8    null,
    conversion_price     float8    null,
    price_recovery_ratio float8    null,
    score                float8    null,
    upper_strike_price   float8    null,
    lower_strike_price   float8    null,
    inline_price_status  integer   null,
    issuer_code          varchar   null
);
create unique index table_snapshot_warrant_id_unique on public.t_snapshot_warrant_ex (id);
create unique index t_snapshot_warrant_code_market_uindex on public.t_snapshot_warrant_ex (owner_code, owner_market);
alter table public.t_snapshot_warrant_ex
    add constraint table_snapshot_warrant_pk primary key (id);

create table public.t_snapshot_option_ex
(
    id                     bigserial not null,
    owner_market           integer   null,
    owner_code             varchar   null,
    option_type            integer   null,
    strike_time            varchar   null,
    strike_price           float8    null,
    contract_size          integer   null,
    contract_size_float    float8    null,
    open_interest          integer   null,
    implied_volatility     float8    null,
    premium                float8    null,
    delta                  float8    null,
    gamma                  float8    null,
    vega                   float8    null,
    theta                  float8    null,
    rho                    float8    null,
    index_option_type      integer   null,
    net_open_interest      integer   null,
    expiry_date_distance   integer   null,
    contract_nominal_value float8    null,
    owner_lot_multiplier   float8    null,
    option_area_type       integer   null,
    contract_multiplier    float8    null
);
create unique index table_snapshot_option_id_unique on public.t_snapshot_option_ex (id);
create unique index t_snapshot_option_code_market_uindex on public.t_snapshot_option_ex (owner_code, owner_market);
alter table public.t_snapshot_option_ex
    add constraint table_snapshot_option_pk primary key (id);

create table public.t_snapshot_index_ex
(
    id          bigserial not null,
    market      integer   null,
    code        varchar   null,
    raise_count integer   null,
    fall_count  integer   null,
    equal_count integer   null
);
create unique index table_snapshot_index_id_unique on public.t_snapshot_index_ex (id);
create unique index t_snapshot_index_code_market_uindex on public.t_snapshot_index_ex (code, market);
alter table public.t_snapshot_index_ex
    add constraint table_snapshot_index_pk primary key (id);

create table public.t_snapshot_plate_ex
(
    id          bigserial not null,
    market      integer   null,
    code        varchar   null,
    raise_count integer   null,
    fall_count  integer   null,
    equal_count integer   null
);
create unique index table_snapshot_plate_id_unique on public.t_snapshot_plate_ex (id);
create unique index t_snapshot_plate_code_market_uindex on public.t_snapshot_plate_ex (code, market);
alter table public.t_snapshot_plate_ex
    add constraint table_snapshot_plate_pk primary key (id);

create table public.t_snapshot_future_ex
(
    id                bigserial not null,
    market            integer   null,
    code              varchar   null,
    last_settle_price float8    null,
    position          integer   null,
    position_change   integer   null,
    last_trade_time   date      null,
    is_main_contract  boolean   null
);
create unique index table_snapshot_future_id_unique on public.t_snapshot_future_ex (id);
create unique index t_snapshot_future_code_market_uindex on public.t_snapshot_future_ex (code, market);
alter table public.t_snapshot_future_ex
    add constraint table_snapshot_future_pk primary key (id);

create table public.t_snapshot_trust_ex
(
    id                bigserial not null,
    market            integer   null,
    code              varchar   null,
    dividend_yield    float8    null,
    aum               float8    null,
    outstanding_units bigint    null,
    net_asset_value   float8    null,
    premium           float8    null,
    asset_class       integer   null
);
create unique index table_snapshot_trust_id_unique on public.t_snapshot_trust_ex (id);
create unique index t_snapshot_trus_code_market_uindex on public.t_snapshot_trust_ex (code, market);
alter table public.t_snapshot_trust_ex
    add constraint table_snapshot_trust_pk primary key (id);