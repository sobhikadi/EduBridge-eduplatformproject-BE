CREATE TABLE student
(
    id         int NOT NULL AUTO_INCREMENT,
    first_name       varchar(50),
    last_name       varchar(50),
    country_id int,
    last_modified date NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (country_id) REFERENCES country (id)
);