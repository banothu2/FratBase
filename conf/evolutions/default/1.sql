# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table address (
  id                        integer auto_increment not null,
  user_id                   integer,
  address_line_one          varchar(255),
  address_line_two          varchar(255),
  city                      varchar(255),
  state                     varchar(255),
  zip                       varchar(255),
  country                   varchar(255),
  address_type              varchar(255),
  constraint pk_address primary key (id))
;

create table attend (
  id                        integer auto_increment not null,
  user_id                   integer,
  event_id                  integer,
  constraint pk_attend primary key (id))
;

create table event (
  id                        integer auto_increment not null,
  greek_id                  integer,
  name                      varchar(255),
  start_date_and_time       datetime,
  end_date_and_time         datetime,
  open_event                tinyint(1) default 0,
  location                  varchar(255),
  event_type                varchar(255),
  constraint pk_event primary key (id))
;

create table greek (
  id                        integer auto_increment not null,
  name                      varchar(255),
  university                varchar(255),
  constraint pk_greek primary key (id))
;

create table service_log (
  id                        integer auto_increment not null,
  user_id                   integer,
  greek_id                  integer,
  service_type              varchar(255),
  date                      varchar(255),
  hours                     integer,
  minutes                   integer,
  comments                  varchar(255),
  constraint pk_service_log primary key (id))
;

create table user (
  id                        integer auto_increment not null,
  greek_id                  integer,
  first_name                varchar(255),
  last_name                 varchar(255),
  username                  varchar(255),
  password_hash             varchar(255),
  email                     varchar(255),
  age                       integer,
  sex                       varchar(255),
  graduation_date           varchar(255),
  major                     varchar(255),
  phone_number              varchar(255),
  linked_in                 varchar(255),
  relationship_status       tinyint(1) default 0,
  greek_name                varchar(255),
  profile_picture           varchar(255),
  facebook_id               varchar(255),
  resume                    varchar(255),
  status                    varchar(255),
  access_level              integer,
  constraint pk_user primary key (id))
;

alter table address add constraint fk_address_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_address_user_1 on address (user_id);
alter table attend add constraint fk_attend_user_2 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_attend_user_2 on attend (user_id);
alter table attend add constraint fk_attend_event_3 foreign key (event_id) references event (id) on delete restrict on update restrict;
create index ix_attend_event_3 on attend (event_id);
alter table event add constraint fk_event_greek_4 foreign key (greek_id) references greek (id) on delete restrict on update restrict;
create index ix_event_greek_4 on event (greek_id);
alter table service_log add constraint fk_service_log_user_5 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_service_log_user_5 on service_log (user_id);
alter table service_log add constraint fk_service_log_greek_6 foreign key (greek_id) references greek (id) on delete restrict on update restrict;
create index ix_service_log_greek_6 on service_log (greek_id);
alter table user add constraint fk_user_greek_7 foreign key (greek_id) references greek (id) on delete restrict on update restrict;
create index ix_user_greek_7 on user (greek_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table address;

drop table attend;

drop table event;

drop table greek;

drop table service_log;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

