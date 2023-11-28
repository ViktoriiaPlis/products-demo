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

create unique  index if not exists category_idx on category (name) where (deleted_at is null);

create unique index if not exists product_idx on product (name) where (deleted_at is null);

CREATE TABLE users
(
    id          UUID PRIMARY KEY,
    login        VARCHAR(50),
    role       VARCHAR(50),
    created_at  TIMESTAMP DEFAULT now(),
    updated_at  TIMESTAMP,
    deleted_at  TIMESTAMP,
    salt VARCHAR(100),
    hash VARCHAR(100)
);

create unique  index if not exists login_idx on users (login) where (deleted_at is null);

insert into users(id, login, role, salt, hash)
values (gen_random_uuid(), 'admin', 'ADMIN', 'oU0vRpamjcWTr2kfGJMox/hrUQico6AiDA2xeLqkTgKGFb3pr1s/L7f4d6el1iNW', 'MMzuWXVncl5FnsN5zRPzEOsNeQJQPtJ0r540hb8t0UA=');

insert into users(id, login, role, salt, hash)
values (gen_random_uuid(), 'user', 'USER', 'y3pffPXieIMDcSDWzqPW9WZ1wdjMXAI+j0Kj1GvngO1TAU8ZTlhxiHSkH8Zng9zp', '3WpBoO639MAw2/4rt3bqR7/L4rK3SQRH0P6H8IYKumk=');

