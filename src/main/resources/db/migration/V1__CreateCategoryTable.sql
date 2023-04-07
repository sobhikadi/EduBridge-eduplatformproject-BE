CREATE TABLE category
(
    id          int    NOT NULL AUTO_INCREMENT,
    name        varchar(50),
    PRIMARY KEY (id),
    UNIQUE (name)
);