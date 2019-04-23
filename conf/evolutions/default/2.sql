# --- Poblate

# --- !Ups

INSERT INTO user(id, numero_documento, tipo_documento, nombre, apellidos, fecha_nacimiento) VALUES(25, '1072663788', 'cc', 'David', 'Ortiz', Now());
INSERT INTO user(id, numero_documento, tipo_documento, nombre, apellidos, fecha_nacimiento) VALUES(30, '1072663733', 'cc', 'Alejandro', 'Martinez', Now());

# --- !Downs

DELETE FROM user;

