create table if not exists quantx.t_ema_min_1_raw
(
    market      Int8,
    code        String,
    rehab_type  Int8,
    ema_5       Float64,
    ema_10      Float64,
    ema_12      Float64,
    ema_20      Float64,
    ema_26      Float64,
    ema_60      Float64,
    ema_120     Float64,
    update_time DateTime64,
    add_time    DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (market, code, rehab_type, update_time);

create table if not exists quantx.t_ema_min_1_arc
(
    market      Int8,
    code        String,
    rehab_type  Int8,
    ema_5       Float64,
    ema_10      Float64,
    ema_12      Float64,
    ema_20      Float64,
    ema_26      Float64,
    ema_60      Float64,
    ema_120     Float64,
    update_time DateTime64
) ENGINE = MergeTree
      PRIMARY KEY (market, code, rehab_type, update_time);