create table if not exists universities
(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    user_id INTEGER REFERENCES users (id) ON DELETE CASCADE
);

INSERT INTO universities (name, user_id) VALUES ('Универ 1', 1);