CREATE TABLE pacientes  (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    documento_identidad VARCHAR(255) UNIQUE NOT NULL,
    telefono VARCHAR(50) NOT NULL,
    calle VARCHAR(100) NOT NULL,
    distrito VARCHAR(100) NOT NULL,
    complemento VARCHAR(100),
    numero VARCHAR(20),
    ciudad VARCHAR(100) NOT NULL,
    activo BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);
