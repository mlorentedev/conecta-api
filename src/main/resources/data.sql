INSERT INTO persona (id, nombre, apellidos, email, telefono) VALUES
(1, 'Juan', 'García Pérez', 'juan.garcia@email.com', '666111222'),
(2, 'María', 'López Sánchez', 'maria.lopez@email.com', '666333444'),
(3, 'Carlos', 'Martínez Ruiz', 'carlos.martinez@email.com', '666555666'),
(4, 'Ana', 'Fernández Gil', 'ana.fernandez@email.com', '666777888');

INSERT INTO profesor (id) VALUES (1), (2);

INSERT INTO usuario (username, password, role, profesor_id) VALUES
('jgarcia', '$2a$10$encrypted', 'PROFESOR', 1),
('mlopez', '$2a$10$encrypted', 'PROFESOR', 2);

INSERT INTO familia_profesional (id, nombre) VALUES
(1, 'Informática y Comunicaciones'),
(2, 'Administración y Gestión');

INSERT INTO titulo (nombre, duracion, grado, familia_profesional_id) VALUES
('Desarrollo de Aplicaciones Web', '2000 horas', 'Superior', 1),
('Administración y Finanzas', '2000 horas', 'Superior', 2);

INSERT INTO empresa (cif, direccion, coordenadas, nombre) VALUES
('B12345678', 'Calle Principal 1', '40.4168,-3.7038', 'TechCorp S.L.'),
('B87654321', 'Avenida Central 2', '40.4169,-3.7039', 'AdminPro S.A.');

INSERT INTO empresa_familia_profesional (empresa_id, familia_profesional_id) VALUES
(1, 1),
(2, 2);

INSERT INTO persona (id, nombre, apellidos, email, telefono) VALUES
(5, 'Pedro', 'Sánchez Vidal', 'pedro.sanchez@techcorp.com', '666999000'),
(6, 'Laura', 'Gómez Díaz', 'laura.gomez@adminpro.com', '666000111');

INSERT INTO trabajador (id, puesto, area, empresa_id) VALUES
(5, 'Jefe de Desarrollo', 'IT', 1),
(6, 'Directora RRHH', 'Recursos Humanos', 2);

INSERT INTO profesor_trabajador (profesor_id, trabajador_id) VALUES
(1, 5),
(2, 6);

INSERT INTO convocatoria (curso_escolar, nombre) VALUES
('2024-2025', 'Convocatoria Dual 2024'),
('2025-2026', 'Convocatoria Dual 2025');

INSERT INTO curso (nombre, horas_empresa, profesor_id, titulo_id) VALUES
('DAW2 Dual', 800, 1, 1),
('AF2 Dual', 700, 2, 2);

INSERT INTO demanda (cantidad_alumnos, requisitos, empresa_id, convocatoria_id, curso_id) VALUES
(2, 'Conocimientos de programación Java y SQL', 1, 1, 1),
(3, 'Conocimientos de contabilidad y Excel avanzado', 2, 1, 2);

INSERT INTO contacto (fecha, canal, resumen, empresa_id) VALUES
('2024-01-15', 'Email', 'Primer contacto para programa dual', 1),
('2024-01-20', 'Teléfono', 'Seguimiento de solicitud', 2);