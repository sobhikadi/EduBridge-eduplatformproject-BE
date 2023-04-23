CREATE TABLE address
(
    id   int     NOT NULL AUTO_INCREMENT,
    street char(50) NOT NULL,
    zipcode varchar(15),
    city varchar(35),
    country_id int,
    PRIMARY KEY (id),
    UNIQUE (street, zipcode, city),
    FOREIGN KEY (country_id) REFERENCES country (id)
);