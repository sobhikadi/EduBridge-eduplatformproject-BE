CREATE TABLE teacher
(
    id         int NOT NULL AUTO_INCREMENT,
    first_name      varchar(50),
    last_name       varchar(50),
    publish_name       varchar(50),
    address_id int,
    last_modified date,
    PRIMARY KEY (id),
    FOREIGN KEY (address_id) REFERENCES address (id)
);