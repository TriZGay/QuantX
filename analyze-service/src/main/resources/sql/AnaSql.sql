-- ema计算sql
with recursive pre_data as (
    select code, update_time, close_price, row_number() over (partition by (code, rehab_type) order by update_time) as rn
    from t_kl_min_1_arc
    where update_time >= '2025-01-02'
    ), ema_cte as (
    select code, update_time, close_price, close_price as ema12, rn from pre_data
    where rn = 1
    union all
    select code, update_time, close_price, (p.close_price*2+e.ema12*11)/13, p.rn
    from pre_data p, ema_cte e
    where p.rn-1 = e.rn
    )
select *
from ema_cte
limit 10;

