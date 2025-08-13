-- 递归sql计算kdj数据
with recursive raw_data as (
    select kdj.market as market, kdj.code as code, kdj.rehab_type as rehab_type, close_price, high_price, low_price, k, d, j, update_time
    from t_kl_min_1_arc k
    inner join t_kdj_min_1_arc kdj on k.code = kdj.code and k.rehab_type = kdj.rehab_type and k.update_time = kdj.update_time
    where update_time >= '2025-07-21 09:30:00' and update_time <= '2025-07-23 09:33:00' and code = '00700' and rehab_type = 1
    ), rsv_pre as (
    select *, max (high_price) over (order by update_time rows between 8 preceding and current row ) as highest_9, min (low_price) over (order by update_time rows between 8 preceding and current row) as lowest_9
    from raw_data
    ), rsv_data as (
    select *, case
    when highest_9 = lowest_9 then 50
    else (close_price - lowest_9) / (highest_9 - lowest_9) * 100
    end as rsv, row_number() over (order by update_time ) as rn
    from rsv_pre
    where update_time >='2025-07-22 09:30:00'
    ), kdj_cte as (
    select market, code, rehab_type, k, d, j, rsv, update_time, rn
    from rsv_data
    where rn = 1
    union all
    select market, code, rehab_type, ((2/3)*e.k)+((1/3)*p.rsv) as k, ((2/3)*e.d+(1/3)*k) as d, ((3*k)-(2*d)) as j, rsv, update_time, p.rn
    from rsv_data p, kdj_cte e
    where p.rn - 1 = e.rn
    )
select *
from kdj_cte;