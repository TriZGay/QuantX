create database if not exists quantx;

create table if not exists quantx.t_rehabs
(
    market           Int8,
    code             String,
    company_act_flag Int64,
    fwd_factor_a     Float64,
    fwd_factor_b     Float64,
    bwd_factor_a     Float64,
    bwd_factor_b     Float64,
    split_base       Int32,
    split_ert        Int32,
    join_base        Int32,
    join_ert         Int32,
    bonus_base       Int32,
    bonus_ert        Int32,
    transfer_base    Int32,
    transfer_ert     Int32,
    allot_base       Int32,
    allot_ert        Int32,
    allot_price      Float64,
    add_base         Int32,
    add_ert          Int32,
    add_price        Float64,
    dividend         Float64,
    sp_dividend      Float64,
    time             Date,
    add_time         DateTime64
) ENGINE = MergeTree()
      PRIMARY KEY (market, code, time);

create table if not exists quantx.t_capital_flow
(
    market          Int8,
    code            String,
    in_flow         Float64,
    main_in_flow    Float64,
    super_in_flow   Float64,
    big_in_flow     Float64,
    mid_in_flow     Float64,
    sml_in_flow     Float64,
    time            DateTime64,
    last_valid_time DateTime64
) ENGINE = MergeTree()
      PRIMARY KEY (market, code, time);

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

create table if not exists quantx.t_ma_min_1_arc
(
    market      Int8,
    code        String,
    rehab_type  Int8,
    ma_5        Float64,
    ma_10       Float64,
    ma_20       Float64,
    ma_30       Float64,
    ma_60       Float64,
    ma_120      Float64,
    update_time DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (market, code, rehab_type, update_time);


create table if not exists quantx.t_macd_min_1_arc
(
    market      Int8,
    code        String,
    rehab_type  Int8,
    dif         Float64,
    dea         Float64,
    macd        Float64,
    update_time DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (market, code, rehab_type, update_time);

create table if not exists quantx.t_boll_min_1_arc
(
    market       Int8,
    code         String,
    rehab_type   Int8,
    ma20_mid     Float64,
    double_upper Float64,
    double_lower Float64,
    one_upper    Float64,
    one_lower    Float64,
    triple_upper Float64,
    triple_lower Float64,
    update_time  DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (market, code, rehab_type, update_time);

create table if not exists quantx.t_rsi_min_1_arc
(
    market      Int8,
    code        String,
    rehab_type  Int8,
    rsi_6       Float64,
    rsi_12      Float64,
    rsi_24      Float64,
    update_time DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (market, code, rehab_type, update_time);

create table if not exists quantx.t_kdj_min_1_arc
(
    market      Int8,
    code        String,
    rehab_type  Int8,
    k           Float64,
    d           Float64,
    j           Float64,
    update_time DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (market, code, rehab_type, update_time);

create table if not exists quantx.t_arbr_min_1_arc
(
    market      Int8,
    code        String,
    rehab_type  Int8,
    ar          Float64,
    br          Float64,
    update_time DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (market, code, rehab_type, update_time);

create table if not exists quantx.t_ak_us_rt
(
    id             Int64,
    code           String,
    price          Float64,
    ratio          Float64,
    ratio_val      Float64,
    turnover       Float64,
    volume         Float64,
    amplitude      Float64,
    high           Float64,
    low            Float64,
    open           Float64,
    close          Float64,
    turnover_ratio Float64,
    market_cap     Float64,
    pe_ratio       Float64,
    add_time       DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (code, add_time);

create table if not exists quantx.t_kafka_test
(
    a String,
    b Float64
) ENGINE = Kafka()
      SETTINGS
          kafka_broker_list = '172.31.10.120:9092',
          kafka_topic_list = 'test_ck_kafka',
          kafka_group_name = 'test_ck_kafka_group',
          kafka_format = 'JSONEachRow';