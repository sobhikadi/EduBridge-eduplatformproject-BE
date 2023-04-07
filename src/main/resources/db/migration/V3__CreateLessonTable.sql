CREATE TABLE lesson (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(100),
    description varchar(300),
    course_id int,
    PRIMARY KEY (id),
    UNIQUE (name),
    FOREIGN KEY (course_id) REFERENCES course (id)
);

