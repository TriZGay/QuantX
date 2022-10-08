CREATE TABLE admin.t_plate
(
    id          bigserial   not null,
    name        VARCHAR(20) null,
    code        VARCHAR(20) null,
    plate_id    VARCHAR(20) null,
    create_date timestamp   null default now(),
    modify_date timestamp   null
);

create unique index table_plate_id_unique on admin.t_plate (id);
alter table admin.t_plate
    add constraint table_plate_pk primary key (id);
CREATE TABLE user1.t_plate
(
    id          bigserial   not null,
    name        VARCHAR(20) null,
    code        VARCHAR(20) null,
    plate_id    VARCHAR(20) null,
    create_date timestamp   null default now(),
    modify_date timestamp   null
);

create unique index table_plate_id_unique on user1.t_plate (id);
alter table user1.t_plate
    add constraint table_plate_pk primary key (id);
