CREATE TABLE refresh_token
(
    id int NOT NULL AUTO_INCREMENT,
    user_id int NOT NULL,
    token varchar(255) NOT NULL,
    expiration_date date NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (user_id, token),
    FOREIGN KEY (user_id) REFERENCES `user` (id)
);