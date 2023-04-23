CREATE TABLE favorite_course
(
    course_id  int NOT NULL,
    student_id int NOT NULL,
    PRIMARY KEY (course_id, student_id),
    FOREIGN KEY (course_id) REFERENCES course (id),
    FOREIGN KEY (student_id) REFERENCES student (id)
);