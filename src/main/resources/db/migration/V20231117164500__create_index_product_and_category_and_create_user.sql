
create unique  index if not exists category_idx on category (name) where (deleted_at is null);

create unique index if not exists product_idx on product (name) where (deleted_at is null);

CREATE TABLE users
(
    id          UUID PRIMARY KEY,
    login        VARCHAR(50),
    password VARCHAR(100),
    role       smallint,
    created_at  TIMESTAMP DEFAULT now(),
    updated_at  TIMESTAMP,
    deleted_at  TIMESTAMP
);