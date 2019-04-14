# --- First database schema

# --- !Ups

create table users (
  id                        bigint not null,
  numeroDocumento           varchar(255),
  tipoDocumento             varchar(255),
  nombre                    varchar(255),
  apellidos                 varchar(255),
  fechaNacimiento           timestamp,
  constraint pk_users primary key (id));



# --- !Downs


drop table if exists users;