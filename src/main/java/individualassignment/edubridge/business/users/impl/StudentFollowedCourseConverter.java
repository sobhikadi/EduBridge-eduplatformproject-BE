package individualassignment.edubridge.business.users.impl;

import individualassignment.edubridge.business.course.impl.CourseConverter;
import individualassignment.edubridge.domain.users.StudentFollowedCourse;
import individualassignment.edubridge.persistence.users.entities.StudentFollowedCourseEntity;

public class StudentFollowedCourseConverter {
    public StudentFollowedCourseConverter() {
    }

    public static StudentFollowedCourse convert(StudentFollowedCourseEntity followedCourse) {
        return StudentFollowedCourse.builder()
                .id(followedCourse.getId())
                .course(CourseConverter.convert(followedCourse.getCourse()))
                .followingDate(followedCourse.getFollowingDate())
                .build();
    }

}
