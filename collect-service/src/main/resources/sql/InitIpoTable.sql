CREATE TABLE public.t_ipo
(
    id             bigserial   not null,
    name           VARCHAR(20) null,
    security       VARCHAR(20) null,
    list_time      VARCHAR(20) null,
    list_timestamp timestamp   null,
    ipo_ex_hk_id   bigint      null
);

create unique index table_ipo_id_unique on public.t_ipo (id);
alter table public.t_ipo
    add constraint table_ipo_pk primary key (id);

create table public.t_ipo_ex_hk
(
    id                  bigserial   not null,
    ipo_price_min       float8      not null,
    ipo_price_max       float8      not null,
    list_price          float8      not null,
    lot_size            int         not null,
    entrance_price      float8      not null,
    is_subscribe_status smallint    not null,
    apply_endTime       varchar(20) null,
    apply_endTimestamp  timestamp   null
);

create unique index table_ipo_hk_id_unique on public.t_ipo_ex_hk (id);
alter table public.t_ipo_ex_hk
    add constraint table_ipo_hk_pk primary key (id);

create table public.t_ipo_ex_cn
(
    id                        bigserial   not null,
    apply_code                varchar(20) not null,
    issue_size                bigint      not null,
    online_issue_size         bigint      not null,
    apply_upper_limit         bigint      not null,
    apply_limit_market_value  bigint      not null,
    is_estimate_ipo_price     smallint    not null,
    ipo_price                 float8      not null,
    industry_pe_rate          float8      not null,
    is_estimate_winning_ratio smallint    not null,
    winning_ratio             float8      not null,
    issue_pe_rate             float8      not null,
    apply_time                varchar(20) null,
    apply_timestamp           timestamp   null,
    winning_time              varchar(20) null,
    winning_timestamp         timestamp   null,
    is_has_won                smallint    not null
);
create unique index table_ipo_cn_id_unique on public.t_ipo_ex_cn (id);
alter table public.t_ipo_ex_cn
    add constraint table_ipo_cn_pk primary key (id);

create table public.t_ipo_ex_us
(
    id            bigserial not null,
    ipo_price_min float8    not null,
    ipo_price_max float8    not null,
    issue_size    bigint    not null
);

create unique index table_ipo_us_id_unique on public.t_ipo_ex_us (id);
alter table public.t_ipo_ex_us
    add constraint table_ipo_us_pk primary key (id);

create table public.t_ipo_ex_cn_winning
(
    id           bigserial   not null,
    winning_name varchar(20) not null,
    winning_info varchar(20) not null,
    ipo_cn_id    bigint      not null
);
create unique index table_ipo_cn_winning_id_unique on public.t_ipo_ex_cn_winning (id);
alter table public.t_ipo_ex_cn_winning
    add constraint table_ipo_cn_winning_pk primary key (id);