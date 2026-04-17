alter table public.t_plate
alter column code type varchar(50) using code::varchar(50);