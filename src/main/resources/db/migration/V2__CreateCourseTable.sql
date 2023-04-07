CREATE TABLE course
(
    id         int NOT NULL AUTO_INCREMENT,
    title       varchar(100),
    description varchar(300),
    provider   varchar(100),
    creation_date date,
    publish_state varchar(15),
    publish_date date,
    last_modified date,
    category_id int,
    image_url varchar(200),
    PRIMARY KEY (id),
    UNIQUE (title),
    FOREIGN KEY (category_id) REFERENCES category (id)
);