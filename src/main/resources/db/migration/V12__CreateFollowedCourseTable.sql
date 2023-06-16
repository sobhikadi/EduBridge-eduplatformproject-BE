CREATE TABLE followed_course
(
    id int NOT NULL AUTO_INCREMENT,
    course_id  int NOT NULL,
    student_id int NOT NULL,
    following_date date NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (course_id, student_id),
    FOREIGN KEY (course_id) REFERENCES course (id),
    FOREIGN KEY (student_id) REFERENCES student (id)
);