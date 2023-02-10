CREATE TABLE IF NOT EXISTS todo_users
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    login    TEXT UNIQUE,
    password TEXT
);
