CREATE TABLE country
(
    id   int     NOT NULL AUTO_INCREMENT,
    code char(2) NOT NULL,
    name varchar(50),
    PRIMARY KEY (id),
    UNIQUE (code),
    UNIQUE (name)
);