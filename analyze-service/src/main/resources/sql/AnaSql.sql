-- ema计算sql
with recursive raw_data as (
    select ema.market as market, ema.code as code, ema.rehab_type as rehab_type, k.close_price, ema.ema_5 as ema_5, ema.ema_10 as ema_10, ema.ema_12 as ema_12, ema.ema_20 as ema_20, ema.ema_26 as ema_26, ema.ema_60 as ema_60, ema.ema_120 as ema_120, ema.update_time as update_time
    from t_kl_min_1_arc k inner join t_ema_min_1_arc ema on k.code=ema.code and k.rehab_type = ema.rehab_type and k.update_time = ema.update_time
    where update_time >='2025-07-17 09:30:00' and update_time <='2025-07-17 09:32:00' and code='00700' and rehab_type =1
    ), pre_data as (
    select *, row_number() over ( order by update_time) as rn
    from raw_data
    ), ema_cte as (
    select market, code, rehab_type, close_price, ema_5, update_time, rn
    from pre_data
    where rn = 1
    union all
    select p.market, p.code, p.rehab_type, p.close_price, (p.close_price*2+e.ema_5 * 4)/6 as ema_5, p.update_time, p.rn
    from pre_data p, ema_cte e
    where p.rn - 1 = e.rn
    )
select *
from ema_cte;