CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


CREATE TABLE category
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(50),
    description VARCHAR(100),
    created_at  TIMESTAMP DEFAULT now(),
    updated_at  TIMESTAMP,
    deleted_at  TIMESTAMP
);

CREATE TABLE product
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(50),
    description VARCHAR(100),
    price       integer,
    picture     VARCHAR(50),
    category_id UUID NOT NULL,
    created_at  TIMESTAMP DEFAULT now(),
    updated_at  TIMESTAMP,
    deleted_at  TIMESTAMP,
    status      smallint
);
