-- Elimina la base de datos existente para evitar conflictos
DROP DATABASE IF EXISTS dein;
-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS dein;

-- Usar la base de datos
USE dein;

-- Crear la tabla personas
CREATE TABLE personas
(
    person_id  INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    birth_date DATE         NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- Insertar 5 personas de ejemplo
INSERT INTO personas (first_name, last_name, birth_date)
VALUES ('Juan', 'García', '1990-05-15'),
       ('María', 'López', '1985-08-22'),
       ('Carlos', 'Martínez', '1992-03-10'),
       ('Ana', 'Rodríguez', '1988-11-30'),
       ('Pedro', 'Sánchez', '1995-07-18');