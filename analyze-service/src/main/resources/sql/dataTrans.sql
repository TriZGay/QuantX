create table quantx.t_kl_min_1_arc as quantx.t_kl_min_1_raw;
alter table quantx.t_kl_min_1_arc
    drop column add_time;
insert into quantx.t_kl_min_1_arc
select market,
       code,
       rehab_type,
       high_price,
       open_price,
       low_price,
       close_price,
       last_close_price,
       volume,
       turnover,
       turnover_rate,
       pe,
       change_rate,
       update_time
from t_kl_min_1_raw as t1 all
         inner join (select update_time, max(add_time) as latest from t_kl_min_1_raw group by update_time) as t2
                    on (t2.update_time = t1.update_time) and (t2.latest = t1.add_time);
---
create table quantx.t_kl_min_3_arc as quantx.t_kl_min_3_raw;
alter table quantx.t_kl_min_3_arc
    drop column add_time;
insert into quantx.t_kl_min_3_arc
select market,
       code,
       rehab_type,
       high_price,
       open_price,
       low_price,
       close_price,
       last_close_price,
       volume,
       turnover,
       turnover_rate,
       pe,
       change_rate,
       update_time
from t_kl_min_3_raw as t1 all
         inner join (select update_time, max(add_time) as latest from t_kl_min_3_raw group by update_time) as t2
                    on (t2.update_time = t1.update_time) and (t2.latest = t1.add_time);
---
create table quantx.t_kl_min_5_arc as quantx.t_kl_min_5_raw;
alter table quantx.t_kl_min_5_arc
    drop column add_time;
insert into quantx.t_kl_min_5_arc
select market,
       code,
       rehab_type,
       high_price,
       open_price,
       low_price,
       close_price,
       last_close_price,
       volume,
       turnover,
       turnover_rate,
       pe,
       change_rate,
       update_time
from t_kl_min_5_raw as t1 all
         inner join (select update_time, max(add_time) as latest from t_kl_min_5_raw group by update_time) as t2
                    on (t2.update_time = t1.update_time) and (t2.latest = t1.add_time);
---
create table quantx.t_kl_min_30_arc as quantx.t_kl_min_30_raw;
alter table quantx.t_kl_min_30_arc
    drop column add_time;
insert into quantx.t_kl_min_30_arc
select market,
       code,
       rehab_type,
       high_price,
       open_price,
       low_price,
       close_price,
       last_close_price,
       volume,
       turnover,
       turnover_rate,
       pe,
       change_rate,
       update_time
from t_kl_min_30_raw as t1 all
         inner join (select update_time, max(add_time) as latest from t_kl_min_30_raw group by update_time) as t2
                    on (t2.update_time = t1.update_time) and (t2.latest = t1.add_time);
---
create table quantx.t_kl_min_60_arc as quantx.t_kl_min_60_raw;
alter table quantx.t_kl_min_60_arc
    drop column add_time;
insert into quantx.t_kl_min_60_arc
select market,
       code,
       rehab_type,
       high_price,
       open_price,
       low_price,
       close_price,
       last_close_price,
       volume,
       turnover,
       turnover_rate,
       pe,
       change_rate,
       update_time
from t_kl_min_60_raw as t1 all
         inner join (select update_time, max(add_time) as latest from t_kl_min_60_raw group by update_time) as t2
                    on (t2.update_time = t1.update_time) and (t2.latest = t1.add_time);
---
create table quantx.t_kl_day_arc as quantx.t_kl_day_raw;
alter table quantx.t_kl_day_arc
    drop column add_time;
insert into quantx.t_kl_day_arc
select market,
       code,
       rehab_type,
       high_price,
       open_price,
       low_price,
       close_price,
       last_close_price,
       volume,
       turnover,
       turnover_rate,
       pe,
       change_rate,
       update_time
from t_kl_day_raw as t1 all
         inner join (select update_time, max(add_time) as latest from t_kl_day_raw group by update_time) as t2
                    on (t2.update_time = t1.update_time) and (t2.latest = t1.add_time);
---
create table quantx.t_kl_month_arc as quantx.t_kl_month_raw;
alter table quantx.t_kl_month_arc
    drop column add_time;
insert into quantx.t_kl_month_arc
select market,
       code,
       rehab_type,
       high_price,
       open_price,
       low_price,
       close_price,
       last_close_price,
       volume,
       turnover,
       turnover_rate,
       pe,
       change_rate,
       update_time
from t_kl_month_raw as t1 all
         inner join (select update_time, max(add_time) as latest from t_kl_month_raw group by update_time) as t2
                    on (t2.update_time = t1.update_time) and (t2.latest = t1.add_time);
---
create table quantx.t_kl_quarter_arc as quantx.t_kl_quarter_raw;
alter table quantx.t_kl_quarter_arc
    drop column add_time;
insert into quantx.t_kl_quarter_arc
select market,
       code,
       rehab_type,
       high_price,
       open_price,
       low_price,
       close_price,
       last_close_price,
       volume,
       turnover,
       turnover_rate,
       pe,
       change_rate,
       update_time
from t_kl_quarter_raw as t1 all
         inner join (select update_time, max(add_time) as latest from t_kl_quarter_raw group by update_time) as t2
                    on (t2.update_time = t1.update_time) and (t2.latest = t1.add_time);
---
create table quantx.t_kl_week_arc as quantx.t_kl_week_raw;
alter table quantx.t_kl_week_arc
    drop column add_time;
insert into quantx.t_kl_week_arc
select market,
       code,
       rehab_type,
       high_price,
       open_price,
       low_price,
       close_price,
       last_close_price,
       volume,
       turnover,
       turnover_rate,
       pe,
       change_rate,
       update_time
from t_kl_week_raw as t1 all
         inner join (select update_time, max(add_time) as latest from t_kl_week_raw group by update_time) as t2
                    on (t2.update_time = t1.update_time) and (t2.latest = t1.add_time);
---
create table quantx.t_kl_year_arc as quantx.t_kl_year_raw;
alter table quantx.t_kl_year_arc
    drop column add_time;
insert into quantx.t_kl_year_arc
select market,
       code,
       rehab_type,
       high_price,
       open_price,
       low_price,
       close_price,
       last_close_price,
       volume,
       turnover,
       turnover_rate,
       pe,
       change_rate,
       update_time
from t_kl_year_raw as t1 all
         inner join (select update_time, max(add_time) as latest from t_kl_year_raw group by update_time) as t2
                    on (t2.update_time = t1.update_time) and (t2.latest = t1.add_time);