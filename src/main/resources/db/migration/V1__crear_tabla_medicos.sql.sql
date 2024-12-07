-- V1__crear_tabla_medicos.sql
-- V2__agregar_columna_telefono.sql
-- V3__eliminar_columna_direccion.sql


CREATE TABLE medicos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    documento VARCHAR(11) NOT NULL UNIQUE,
    especialidad VARCHAR(100) NOT NULL,
    calle VARCHAR(100) NOT NULL,
    distrito VARCHAR(100) NOT NULL,
    complemento VARCHAR(100),
    numero VARCHAR(20),
    ciudad VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);
