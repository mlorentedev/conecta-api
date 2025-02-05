CREATE SEQUENCE IF NOT EXISTS persona_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS persona (
    id BIGINT PRIMARY KEY DEFAULT nextval('persona_seq'),
    nombre VARCHAR(255),
    apellidos VARCHAR(255),
    email VARCHAR(255),
    telefono VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS profesor (
    id BIGINT PRIMARY KEY DEFAULT nextval('persona_seq'),
    FOREIGN KEY (id) REFERENCES persona(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255),
    profesor_id BIGINT,
    FOREIGN KEY (profesor_id) REFERENCES profesor(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS familia_profesional (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS titulo (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(255),
    duracion VARCHAR(255),
    grado VARCHAR(255),
    familia_profesional_id BIGINT,
    FOREIGN KEY (familia_profesional_id) REFERENCES familia_profesional(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS empresa (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cif VARCHAR(255),
    direccion VARCHAR(255),
    coordenadas VARCHAR(255),
    nombre VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS trabajador (
    id BIGINT PRIMARY KEY DEFAULT nextval('persona_seq'),
    puesto VARCHAR(255),
    area VARCHAR(255),
    empresa_id BIGINT,
    FOREIGN KEY (id) REFERENCES persona(id) ON DELETE CASCADE,
    FOREIGN KEY (empresa_id) REFERENCES empresa(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS contacto (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    fecha DATE,
    canal VARCHAR(50),
    resumen TEXT,
    empresa_id BIGINT,
    FOREIGN KEY (empresa_id) REFERENCES empresa(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS convocatoria (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    curso_escolar VARCHAR(255),
    nombre VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS curso (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(255),
    horas_empresa INT,
    profesor_id BIGINT,
    titulo_id BIGINT,
    FOREIGN KEY (profesor_id) REFERENCES profesor(id) ON DELETE CASCADE,
    FOREIGN KEY (titulo_id) REFERENCES titulo(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS demanda (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cantidad_alumnos INT,
    requisitos TEXT,
    empresa_id BIGINT,
    convocatoria_id BIGINT,
    curso_id BIGINT,
    FOREIGN KEY (empresa_id) REFERENCES empresa(id) ON DELETE CASCADE,
    FOREIGN KEY (convocatoria_id) REFERENCES convocatoria(id) ON DELETE CASCADE,
    FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS profesor_contacto_trabajador (
    profesor_id BIGINT,
    trabajador_id BIGINT,
    PRIMARY KEY (profesor_id, trabajador_id),
    FOREIGN KEY (profesor_id) REFERENCES profesor(id) ON DELETE CASCADE,
    FOREIGN KEY (trabajador_id) REFERENCES trabajador(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS empresa_familia_profesional (
    empresa_id BIGINT,
    familia_profesional_id BIGINT,
    PRIMARY KEY (empresa_id, familia_profesional_id),
    FOREIGN KEY (empresa_id) REFERENCES empresa(id) ON DELETE CASCADE,
    FOREIGN KEY (familia_profesional_id) REFERENCES familia_profesional(id)
);