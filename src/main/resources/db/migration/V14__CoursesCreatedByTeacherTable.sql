CREATE TABLE courses_created_by_teacher
(
    course_id  int NOT NULL,
    teacher_id int NOT NULL,
    PRIMARY KEY (course_id, teacher_id),
    FOREIGN KEY (course_id) REFERENCES course (id),
    FOREIGN KEY (teacher_id) REFERENCES student (id)
);