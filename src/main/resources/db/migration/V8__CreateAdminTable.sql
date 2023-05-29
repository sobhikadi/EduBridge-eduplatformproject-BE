CREATE TABLE admin
(
    id         int NOT NULL AUTO_INCREMENT,
    first_name      varchar(50),
    last_name       varchar(50),
    publish_name    varchar(50),
    last_modified date,
    PRIMARY KEY (id)
);