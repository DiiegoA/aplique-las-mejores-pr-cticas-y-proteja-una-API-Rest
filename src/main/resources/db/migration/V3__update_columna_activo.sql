-- V1__crear_tabla_medicos.sql
-- V2__agregar_columna_telefono.sql
-- V3__eliminar_columna_direccion.sql


ALTER TABLE medicos ADD activo BIT;
UPDATE medicos SET activo = 1;