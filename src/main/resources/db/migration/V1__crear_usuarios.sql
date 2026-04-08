CREATE TYPE rol_usuario AS ENUM ('ADMIN', 'USER', 'MODERATOR');

CREATE TABLE users (
    id          BIGSERIAL PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    rol         rol_usuario  NOT NULL DEFAULT 'USER',
    activo      BOOLEAN      NOT NULL DEFAULT TRUE,
    creado_en   TIMESTAMP    NOT NULL DEFAULT NOW()
);