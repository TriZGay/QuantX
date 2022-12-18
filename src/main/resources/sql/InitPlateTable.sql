CREATE TABLE public.t_plate
(
    id     bigserial   not null,
    name   VARCHAR(20) null,
    code   VARCHAR(20) null,
    market int         null
);

create unique index table_plate_id_unique on public.t_plate (id);
create unique index t_plate_code_uindex on public.t_plate (code);
alter table public.t_plate
    add constraint table_plate_pk primary key (id);