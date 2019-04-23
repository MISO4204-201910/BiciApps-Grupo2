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

  create table bicicletas (
  id                        bigint not null,
  codigo                    varchar(255),
  constraint pk_bicicletas primary key (id));

  create table puntos (
  id                        bigint not null,
  puntosKilometraje               bigint ,
  puntosRecorridos                bigint ,
  puntosRecomendaciones           bigint ,
  constraint pk_puntos primary key (id));

  create table prestamos (
  id                        bigint not null,
  id_usuario                bigint not null,
  id_bicicpleta             bigint not null,
  fecha_inicio              timestamp not null DEFAULT NOW(),
  fecha_fin                 timestamp,
  tipo_pago                 varchar(10),
  valor                     double,
  constraint pk_prestamos primary key (id));



# --- !Downs


drop table if exists users;