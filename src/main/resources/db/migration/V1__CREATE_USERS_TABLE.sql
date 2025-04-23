create table if not exists users
(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

INSERT INTO users (name) VALUES ('Пользователь 1');