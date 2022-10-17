CREATE TABLE public.t_plate
(
    id     bigserial   not null,
    name   VARCHAR(20) null,
    code   VARCHAR(20) null,
    market int         null
);

create unique index table_plate_id_unique on public.t_plate (id);
alter table public.t_plate
    add constraint table_plate_pk primary key (id);
