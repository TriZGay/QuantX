-- 递归sql计算kdj数据
with recursive rsv_pre as (
    select market, code, rehab_type, high_price, low_price, close_price, update_time, max (high_price) over (order by update_time rows between 8 preceding and current row ) as highest_9, min (low_price) over (order by update_time rows between 8 preceding and current row) as lowest_9
    from t_kl_min_1_arc
    where update_time >= '2025-01-02 00:00:00' and update_time <= '2025-01-04 16:00:00' and code = '00700' and rehab_type = 1
    ), rsv_data as (
    select *, case
    when highest_9 = lowest_9 then 50
    else (close_price - lowest_9) / (highest_9 - lowest_9) * 100
    end as rsv, row_number() over (order by update_time ) as rn
    from rsv_pre
    ), connect_data as (
    select r.*, kdj.k, kdj.d, kdj.j
    from rsv_data r left join t_kdj_min_1_arc kdj on r.code=kdj.code and r.rehab_type=kdj.rehab_type and r.update_time=kdj.update_time
    ), kdj_cte as (
    select market, code, rehab_type, k, d, j, rsv, update_time, rn
    from connect_data
    where rn = 1
    union all
    select market, code, rehab_type, ((2/3)*e.k)+((1/3)*p.rsv) as k, ((2/3)*e.d+(1/3)*k) as d, ((3*k)-(2*d)) as j, rsv, update_time, p.rn
    from connect_data p, kdj_cte e
    where p.rn - 1 = e.rn
    )
select market, code, rehab_type, k, d, j, update_time
from kdj_cte
where update_time >= '2025-01-03 09:30:00';

-- 初始化2025年kdj数据
insert into t_kdj_min_1_arc
select market, code, rehab_type, 50, 50, 50, update_time
from t_kl_min_1_arc
where update_time = '2025-01-02 09:30:00'

-- kdj策略-只判断金叉、死叉
with recursive pre_kdj as (
    select *, row_number() over (order by update_time ) as rn
    from t_kdj_min_1_arc
    where code='00700' and rehab_type=1 and update_time >='2025-01-02 09:30:00' and update_time <='2025-01-02 16:00:00'
    ), golden_dead_cross as (
    select market, code, rehab_type, k, d, j, 'HOLD' as signal, update_time, rn
    from pre_kdj where rn = 1
    union all
    select market, code, rehab_type, k, d, j, case when (p.k<p.d and e.k>e.d ) then 'BUY'
    when (p.k>p.d and e.k<e.d) then 'SELL'
    else 'HOLD'
    end as signal, update_time, p.rn
    from pre_kdj p, golden_dead_cross e
    where p.rn - 1 = e.rn
    )
select *
from golden_dead_cross;

-- kdj策略-金叉死叉结合超买区(K>80/D>70/J>100)、超卖区(K<20/D<30/J<0)