# --- Poblate

# --- !Ups

INSERT INTO user(id, numero_documento, tipo_documento, nombre, apellidos, fecha_nacimiento, puntos) VALUES(25, '1072663788', 'cc', 'David', 'Ortiz', Now(),0);
INSERT INTO user(id, numero_documento, tipo_documento, nombre, apellidos, fecha_nacimiento, puntos) VALUES(30, '1072663733', 'cc', 'Alejandro', 'Martinez', Now(),250);


INSERT INTO bicicleta(id, codigo) VALUES(100,'abc123');
INSERT INTO bicicleta(id, codigo) VALUES(110,'abc456');


# --- !Downs

DELETE FROM user;
DELETE FROM bicicleta;
DELETE FROM punto;

