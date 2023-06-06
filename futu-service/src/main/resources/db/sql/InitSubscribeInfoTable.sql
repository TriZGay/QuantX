CREATE TABLE public.t_sub
(
    id              bigserial   not null,
    security_market int         null,
    security_code   varchar(20) null,
    security_name   varchar(50) null,
    security_type   int         null,
    sub_type        int         null
);

create unique index table_sub_id_unique on public.t_sub (id);
alter table public.t_sub
    add constraint table_sub_pk primary key (id);
