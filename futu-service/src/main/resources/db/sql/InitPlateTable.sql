CREATE TABLE public.t_plate
(
    id         bigserial   not null,
    name       VARCHAR(20) null,
    code       VARCHAR(20) null,
    plate_type int         null,
    market     int         null
);

create unique index table_plate_id_unique on public.t_plate (id);
create unique index t_plate_code_market_uindex on public.t_plate (code,market);
alter table public.t_plate
    add constraint table_plate_pk primary key (id);

CREATE TABLE public.t_plate_stock
(
    id       bigserial not null,
    plate_id bigint    not null,
    stock_id bigint    not null
);

create unique index table_plate_stock_unique on public.t_plate_stock (id);
create unique index table_plate_stock_relation_unique on public.t_plate_stock (plate_id, stock_id);
alter table public.t_plate_stock
    add constraint table_plate_stock_pk primary key (id);