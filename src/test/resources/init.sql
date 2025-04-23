CREATE TABLE IF NOT EXISTS table1
(
    id      SERIAL PRIMARY KEY,
    column1 VARCHAR(255),
    column2 INT,
    column3 BOOLEAN,
    column4 TIMESTAMP,
    column5 DECIMAL(10, 2)
);

CREATE TABLE IF NOT EXISTS table2
(
    id      SERIAL PRIMARY KEY,
    column1 VARCHAR(255),
    column2 INT,
    column3 BOOLEAN,
    column4 TIMESTAMP,
    column5 DECIMAL(10, 2)
);