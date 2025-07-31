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


create table quantx.t_kl_min_1_arc as quantx.t_kl_min_1_raw;
alter table quantx.t_kl_min_1_arc
    drop column add_time;

create table quantx.t_kl_min_3_arc as quantx.t_kl_min_3_raw;
alter table quantx.t_kl_min_3_arc
    drop column add_time;

create table quantx.t_kl_min_5_arc as quantx.t_kl_min_5_raw;
alter table quantx.t_kl_min_5_arc
    drop column add_time;

create table quantx.t_kl_min_30_arc as quantx.t_kl_min_30_raw;
alter table quantx.t_kl_min_30_arc
    drop column add_time;

create table quantx.t_kl_min_60_arc as quantx.t_kl_min_60_raw;
alter table quantx.t_kl_min_60_arc
    drop column add_time;

create table quantx.t_kl_day_arc as quantx.t_kl_day_raw;
alter table quantx.t_kl_day_arc
    drop column add_time;

create table quantx.t_kl_month_arc as quantx.t_kl_month_raw;
alter table quantx.t_kl_month_arc
    drop column add_time;

create table quantx.t_kl_quarter_arc as quantx.t_kl_quarter_raw;
alter table quantx.t_kl_quarter_arc
    drop column add_time;

create table quantx.t_kl_week_arc as quantx.t_kl_week_raw;
alter table quantx.t_kl_week_arc
    drop column add_time;

create table quantx.t_kl_year_arc as quantx.t_kl_year_raw;
alter table quantx.t_kl_year_arc
    drop column add_time;