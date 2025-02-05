INSERT INTO persona (id, nombre, apellidos, email, telefono) VALUES
(1, 'Juan', 'García Pérez', 'juan.garcia@email.com', '666111222'),
(2, 'María', 'López Sánchez', 'maria.lopez@email.com', '666333444'),
(3, 'Carlos', 'Martínez Ruiz', 'carlos.martinez@email.com', '666555666'),
(4, 'Ana', 'Fernández Gil', 'ana.fernandez@email.com', '666777888'),
(5, 'Pedro', 'Sánchez Vidal', 'pedro.sanchez@techcorp.com', '666999000'),
(6, 'Laura', 'Gómez Díaz', 'laura.gomez@adminpro.com', '666000111');

INSERT INTO profesor (id) VALUES (1), (2);

INSERT INTO usuario (id, username, password, role, profesor_id) VALUES
(1, 'jgarcia', '$2a$10$encrypted', 'PROFESOR', 1),
(2, 'mlopez', '$2a$10$encrypted', 'PROFESOR', 2);

INSERT INTO familia_profesional (id, nombre) VALUES
(1, 'Informática y Comunicaciones'),
(2, 'Administración y Gestión');

INSERT INTO titulo (id, nombre, duracion, grado, familia_profesional_id) VALUES
(1, 'Desarrollo de Aplicaciones Web', '2000 horas', 'Superior', 1),
(2, 'Administración y Finanzas', '2000 horas', 'Superior', 2);

INSERT INTO empresa (id, cif, direccion, coordenadas, nombre) VALUES
(1, 'B12345678', 'Calle Principal 1', '40.4168,-3.7038', 'TechCorp S.L.'),
(2, 'B87654321', 'Avenida Central 2', '40.4169,-3.7039', 'AdminPro S.A.');

INSERT INTO empresa_familia_profesional (empresa_id, familia_profesional_id) VALUES
(1, 1),
(2, 2);

INSERT INTO trabajador (id, puesto, area, empresa_id) VALUES
(5, 'Jefe de Desarrollo', 'IT', 1),
(6, 'Directora RRHH', 'Recursos Humanos', 2);

INSERT INTO profesor_trabajador (profesor_id, trabajador_id) VALUES
(1, 5),
(2, 6);

INSERT INTO convocatoria (id, curso_escolar, nombre) VALUES
(1, '2024-2025', 'Convocatoria Dual 2024'),
(2, '2025-2026', 'Convocatoria Dual 2025');

INSERT INTO curso (id, nombre, horas_empresa, profesor_id, titulo_id) VALUES
(1, 'DAW2 Dual', 800, 1, 1),
(2, 'AF2 Dual', 700, 2, 2);

INSERT INTO demanda (id, cantidad_alumnos, requisitos, empresa_id, convocatoria_id, curso_id) VALUES
(1, 2, 'Conocimientos de programación Java y SQL', 1, 1, 1),
(2, 3, 'Conocimientos de contabilidad y Excel avanzado', 2, 1, 2);

INSERT INTO contacto (id, fecha, canal, resumen, empresa_id) VALUES
(1, '2024-01-15', 'Email', 'Primer contacto para programa dual', 1),
(2, '2024-01-20', 'Teléfono', 'Seguimiento de solicitud', 2);

ALTER TABLE persona ALTER COLUMN id RESTART WITH 7;
ALTER TABLE profesor ALTER COLUMN id RESTART WITH 3;
ALTER TABLE usuario ALTER COLUMN id RESTART WITH 3;
ALTER TABLE familia_profesional ALTER COLUMN id RESTART WITH 3;
ALTER TABLE titulo ALTER COLUMN id RESTART WITH 3;
ALTER TABLE empresa ALTER COLUMN id RESTART WITH 3;
ALTER TABLE trabajador ALTER COLUMN id RESTART WITH 7;
ALTER TABLE convocatoria ALTER COLUMN id RESTART WITH 3;
ALTER TABLE curso ALTER COLUMN id RESTART WITH 3;
ALTER TABLE demanda ALTER COLUMN id RESTART WITH 3;
ALTER TABLE contacto ALTER COLUMN id RESTART WITH 3;