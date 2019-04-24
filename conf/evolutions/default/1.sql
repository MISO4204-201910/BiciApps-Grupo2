# --- First database schema

# --- !Ups

create table user (
  id                        bigint not null,
  numero_documento           varchar(255),
  tipo_documento             varchar(255),
  nombre                     varchar(255),
  apellidos                  varchar(255),
  fecha_nacimiento           timestamp,
  puntos                     bigint,
  constraint pk_users primary key (id));


  create table bicicleta (
  id                        bigint not null,
  codigo                    varchar(255),
  constraint pk_bicicletas primary key (id));

  create table punto (
  id                              bigint not null,
  id_usuario                      bigint not null,
  valor                           bigint ,
  categoria                       varchar (255),
  constraint pk_puntos primary key (id));

  create table prestamo (
  id                        bigint not null,
  id_usuario                bigint not null,
  id_bicicleta              bigint not null,
  fecha_inicio              timestamp not null DEFAULT NOW(),
  fecha_fin                 timestamp,
  tipo_pago                 varchar(10),
  valor                     double,
  constraint pk_prestamos primary key (id));



# --- !Downs


drop table if exists user;
drop table if exists bicicleta;
drop table if exists prestamo;
drop table if exists punto;