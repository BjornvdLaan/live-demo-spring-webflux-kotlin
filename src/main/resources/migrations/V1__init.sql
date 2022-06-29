CREATE TABLE cat
(
    id IDENTITY PRIMARY KEY,
    owner_id INT,
    name     varchar NOT NULL,
    type     varchar NOT NULL,
    age      int     NOT NULL
);

CREATE TABLE person
(
    id IDENTITY PRIMARY KEY,
    name varchar NOT NULL,
    age  int     NOT NULL
);