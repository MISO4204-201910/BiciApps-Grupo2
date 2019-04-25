# --- Poblate

# --- !Ups

INSERT INTO user(id, numero_documento, tipo_documento, nombre, apellidos, fecha_nacimiento) VALUES(25, '1072663788', 'cc', 'David', 'Ortiz', Now());
INSERT INTO user(id, numero_documento, tipo_documento, nombre, apellidos, fecha_nacimiento) VALUES(30, '1072663733', 'cc', 'Alejandro', 'Martinez', Now());
INSERT INTO punto(id, id_usuario, valor, categoria) VALUES(300, 30, 250, 'recomendaciones');

INSERT INTO bicicleta(id, codigo) VALUES(100,'abc123');
INSERT INTO bicicleta(id, codigo) VALUES(110,'abc456');


# --- !Downs

DELETE FROM user;
DELETE FROM bicicleta;
DELETE FROM punto;

