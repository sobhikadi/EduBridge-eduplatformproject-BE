package individualassignment.edubridge.business.users.impl;

import individualassignment.edubridge.business.course.impl.CourseConverter;
import individualassignment.edubridge.domain.users.Student;
import individualassignment.edubridge.persistence.users.entities.StudentEntity;

import java.util.ArrayList;
import java.util.Objects;

public class StudentConverter {
    private StudentConverter() {
    }

    public static Student convert(StudentEntity student) {
        return Student.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .country(CountryConverter.convert(student.getCountry()))
                .lastModified(student.getLastModified())
                .favoriteCourses(
                        student.getFavoriteCourses().isEmpty() ? new ArrayList<>() : student.getFavoriteCourses()
                                .stream()
                                .map(CourseConverter::convert)
                                .toList()
                )
                .followedCourses(
                         Objects.isNull(student.getFollowedCourses()) || student.getFollowedCourses().isEmpty() ? new ArrayList<>() : student.getFollowedCourses()
                                .stream()
                                .map(StudentFollowedCourseConverter::convert)
                                .toList()
                )

                .build();
    }
}
