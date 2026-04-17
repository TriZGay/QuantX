alter table public.t_stock
alter column code type varchar(50) using code::varchar(50);