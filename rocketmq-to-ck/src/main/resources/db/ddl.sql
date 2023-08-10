create database if not exists quantx;

create table if not exists quantx.t_ma30_day
(
    market      Int8,
    code        String,
    rehab_type  Int8,
    ma5         Float64,
    update_time DateTime64,
    add_time    DateTime64
) ENGINE = ReplacingMergeTree(add_time)
      PRIMARY KEY (market, code, rehab_type, update_time)
      ORDER BY (market, code, rehab_type, update_time);

create table if not exists quantx.t_ma20_day
(
    market      Int8,
    code        String,
    rehab_type  Int8,
    ma5         Float64,
    update_time DateTime64,
    add_time    DateTime64
) ENGINE = ReplacingMergeTree(add_time)
      PRIMARY KEY (market, code, rehab_type, update_time)
      ORDER BY (market, code, rehab_type, update_time);

create table if not exists quantx.t_ma10_day
(
    market      Int8,
    code        String,
    rehab_type  Int8,
    ma5         Float64,
    update_time DateTime64,
    add_time    DateTime64
) ENGINE = ReplacingMergeTree(add_time)
      PRIMARY KEY (market, code, rehab_type, update_time)
      ORDER BY (market, code, rehab_type, update_time);

create table if not exists quantx.t_ma5_day
(
    market      Int8,
    code        String,
    rehab_type  Int8,
    ma5         Float64,
    update_time DateTime64,
    add_time    DateTime64
) ENGINE = ReplacingMergeTree(add_time)
      PRIMARY KEY (market, code, rehab_type, update_time)
      ORDER BY (market, code, rehab_type, update_time);


create table if not exists quantx.t_plate_basic_quote_raw
(
    market           Int8,
    code             String,
    price_spread     Float64,
    high_price       Float64,
    open_price       Float64,
    low_price        Float64,
    cur_price        Float64,
    last_close_price Float64,
    volume           Int64,
    turnover         Float64,
    turnover_rate    Float64,
    amplitude        Float64,
    dark_status      UInt8,
    sec_status       UInt8,
    update_time      DateTime64
) ENGINE = MergeTree()
      PRIMARY KEY (market, code, update_time);

create table if not exists quantx.t_brokers_raw
(
    market      Int8,
    code        String,
    broker_id   Int64,
    broker_name String,
    broker_pos  Int8,
    ask_or_bid  Int8,
    order_id    Int64,
    volume      Int64
) ENGINE = MergeTree
      PRIMARY KEY (market, code, broker_id);

create table if not exists quantx.t_timeshare_raw
(
    market           Int8,
    code             String,
    minute           Int32,
    price            Float64,
    last_close_price Float64,
    avg_price        Float64,
    volume           Int64,
    turnover         Float64,
    update_time      DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (market, code, update_time);

create table if not exists quantx.t_ticker_raw
(
    market           Int8,
    code             String,
    sequence         Int64,
    ticker_direction Int8,
    price            Float64,
    volume           Int64,
    turnover         Float64,
    ticker_type      Int8,
    type_sign        Int8,
    update_time      DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (sequence, update_time);

create table if not exists quantx.t_kl_min_60_raw
(
    market           Int8,
    code             String,
    rehab_type       Int8,
    high_price       Float64,
    open_price       Float64,
    low_price        Float64,
    close_price      Float64,
    last_close_price Float64,
    volume           Int64,
    turnover         Float64,
    turnover_rate    Float64,
    pe               Float64,
    change_rate      Float64,
    update_time      DateTime64,
    add_time         DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (market, code, rehab_type, update_time);

create table if not exists quantx.t_kl_min_30_raw
(
    market           Int8,
    code             String,
    rehab_type       Int8,
    high_price       Float64,
    open_price       Float64,
    low_price        Float64,
    close_price      Float64,
    last_close_price Float64,
    volume           Int64,
    turnover         Float64,
    turnover_rate    Float64,
    pe               Float64,
    change_rate      Float64,
    update_time      DateTime64,
    add_time         DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (market, code, rehab_type, update_time);

create table if not exists quantx.t_kl_min_15_raw
(
    market           Int8,
    code             String,
    rehab_type       Int8,
    high_price       Float64,
    open_price       Float64,
    low_price        Float64,
    close_price      Float64,
    last_close_price Float64,
    volume           Int64,
    turnover         Float64,
    turnover_rate    Float64,
    pe               Float64,
    change_rate      Float64,
    update_time      DateTime64,
    add_time         DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (market, code, rehab_type, update_time);

create table if not exists quantx.t_kl_min_5_raw
(
    market           Int8,
    code             String,
    rehab_type       Int8,
    high_price       Float64,
    open_price       Float64,
    low_price        Float64,
    close_price      Float64,
    last_close_price Float64,
    volume           Int64,
    turnover         Float64,
    turnover_rate    Float64,
    pe               Float64,
    change_rate      Float64,
    update_time      DateTime64,
    add_time         DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (market, code, rehab_type, update_time);

create table if not exists quantx.t_kl_min_3_raw
(
    market           Int8,
    code             String,
    rehab_type       Int8,
    high_price       Float64,
    open_price       Float64,
    low_price        Float64,
    close_price      Float64,
    last_close_price Float64,
    volume           Int64,
    turnover         Float64,
    turnover_rate    Float64,
    pe               Float64,
    change_rate      Float64,
    update_time      DateTime64,
    add_time         DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (market, code, rehab_type, update_time);

create table if not exists quantx.t_kl_min_1_raw
(
    market           Int8,
    code             String,
    rehab_type       Int8,
    high_price       Float64,
    open_price       Float64,
    low_price        Float64,
    close_price      Float64,
    last_close_price Float64,
    volume           Int64,
    turnover         Float64,
    turnover_rate    Float64,
    pe               Float64,
    change_rate      Float64,
    update_time      DateTime64,
    add_time         DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (market, code, rehab_type, update_time);

create table if not exists quantx.t_kl_year_raw
(
    market           Int8,
    code             String,
    rehab_type       Int8,
    high_price       Float64,
    open_price       Float64,
    low_price        Float64,
    close_price      Float64,
    last_close_price Float64,
    volume           Int64,
    turnover         Float64,
    turnover_rate    Float64,
    pe               Float64,
    change_rate      Float64,
    update_time      DateTime64,
    add_time         DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (market, code, rehab_type, update_time);

create table if not exists quantx.t_kl_quarter_raw
(
    market           Int8,
    code             String,
    rehab_type       Int8,
    high_price       Float64,
    open_price       Float64,
    low_price        Float64,
    close_price      Float64,
    last_close_price Float64,
    volume           Int64,
    turnover         Float64,
    turnover_rate    Float64,
    pe               Float64,
    change_rate      Float64,
    update_time      DateTime64,
    add_time         DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (market, code, rehab_type, update_time);

create table if not exists quantx.t_kl_month_raw
(
    market           Int8,
    code             String,
    rehab_type       Int8,
    high_price       Float64,
    open_price       Float64,
    low_price        Float64,
    close_price      Float64,
    last_close_price Float64,
    volume           Int64,
    turnover         Float64,
    turnover_rate    Float64,
    pe               Float64,
    change_rate      Float64,
    update_time      DateTime64,
    add_time         DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (market, code, rehab_type, update_time);

create table if not exists quantx.t_kl_week_raw
(
    market           Int8,
    code             String,
    rehab_type       Int8,
    high_price       Float64,
    open_price       Float64,
    low_price        Float64,
    close_price      Float64,
    last_close_price Float64,
    volume           Int64,
    turnover         Float64,
    turnover_rate    Float64,
    pe               Float64,
    change_rate      Float64,
    update_time      DateTime64,
    add_time         DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (market, code, rehab_type, update_time);

create table if not exists quantx.t_kl_day_raw
(
    market           Int8,
    code             String,
    rehab_type       Int8,
    high_price       Float64,
    open_price       Float64,
    low_price        Float64,
    close_price      Float64,
    last_close_price Float64,
    volume           Int64,
    turnover         Float64,
    turnover_rate    Float64,
    pe               Float64,
    change_rate      Float64,
    update_time      DateTime64,
    add_time         DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (market, code, rehab_type, update_time);

create table if not exists quantx.t_stock_basic_quote_raw
(
    market           Int8,
    code             String,
    price_spread     Float64,
    high_price       Float64,
    open_price       Float64,
    low_price        Float64,
    cur_price        Float64,
    last_close_price Float64,
    volume           Int64,
    turnover         Float64,
    turnover_rate    Float64,
    amplitude        Float64,
    dark_status      UInt8,
    sec_status       UInt8,
    update_time      DateTime64
) ENGINE = MergeTree()
      PRIMARY KEY (market, code, update_time);

create table if not exists quantx.t_indies_basic_quote_raw
(
    market           Int8,
    code             String,
    price_spread     Float64,
    high_price       Float64,
    open_price       Float64,
    low_price        Float64,
    cur_price        Float64,
    last_close_price Float64,
    volume           Int64,
    turnover         Float64,
    turnover_rate    Float64,
    amplitude        Float64,
    dark_status      UInt8,
    sec_status       UInt8,
    update_time      DateTime64
) ENGINE = MergeTree()
      PRIMARY KEY (market, code, update_time);