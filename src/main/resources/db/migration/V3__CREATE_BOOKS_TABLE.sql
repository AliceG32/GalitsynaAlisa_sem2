create table if not exists books
(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    course_id INTEGER REFERENCES courses (id) ON DELETE CASCADE
);

INSERT INTO books (name, course_id) VALUES ('Книга 1', 1);