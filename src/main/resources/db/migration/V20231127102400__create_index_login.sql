create unique  index if not exists login_idx on users (login) where (deleted_at is null);