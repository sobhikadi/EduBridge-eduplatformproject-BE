CREATE TABLE user
(
    id         int NOT NULL AUTO_INCREMENT,
    username   varchar(100)  NOT NULL,
    password   varchar(100) NOT NULL,
    student_id int NULL,
    teacher_id int NULL,
    PRIMARY KEY (id),
    UNIQUE (username),
    FOREIGN KEY (student_id) REFERENCES student (id),
    FOREIGN KEY (teacher_id) REFERENCES teacher (id)
);