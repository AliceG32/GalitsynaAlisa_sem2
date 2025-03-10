create table if not exists courses
(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    user_id INTEGER REFERENCES users (id) ON DELETE CASCADE
);

INSERT INTO courses (name, user_id) VALUES ('Курс 1', 1);