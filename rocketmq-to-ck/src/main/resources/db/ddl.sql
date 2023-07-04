create database if not exists quantx;

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