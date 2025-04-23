create table if not exists outbox
(
    id   SERIAL PRIMARY KEY,
    data TEXT NOT NULL
)