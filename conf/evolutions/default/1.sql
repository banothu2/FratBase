# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table address (
  id                        integer auto_increment not null,
  user_id                   integer,
  first_name                varchar(255),
  last_name                 varchar(255),
  facebook_id               varchar(255),
  address_line_one          varchar(255),
  address_line_two          varchar(255),
  city                      varchar(255),
  state                     varchar(255),
  zip                       varchar(255),
  country                   varchar(255),
  address_type              varchar(255),
  constraint pk_address primary key (id))
;

create table greek (
  id                        integer auto_increment not null,
  greek_name                varchar(255),
  university                varchar(255),
  constraint pk_greek primary key (id))
;

create table service_log (
  id                        integer auto_increment not null,
  user_id                   integer,
  university                varchar(255),
  greek_organization        varchar(255),
  service_type              varchar(255),
  date                      varchar(255),
  hours                     varchar(255),
  minutes                   varchar(255),
  comments                  varchar(255),
  constraint pk_service_log primary key (id))
;

create table user (
  id                        integer auto_increment not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  username                  varchar(255),
  password_hash             varchar(255),
  email                     varchar(255),
  university                varchar(255),
  greek_organization        varchar(255),
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

alter table service_log add constraint fk_service_log_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_service_log_user_1 on service_log (user_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table address;

drop table greek;

drop table service_log;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

