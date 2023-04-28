package individualassignment.edubridge.business.users.impl;

import individualassignment.edubridge.business.course.impl.CourseConverter;
import individualassignment.edubridge.domain.users.Student;
import individualassignment.edubridge.persistence.users.entities.StudentEntity;

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
                        student.getFavoriteCourses().isEmpty() ? null : student.getFavoriteCourses()
                                .stream()
                                .map(CourseConverter::convert)
                                .toList()
                )
                .followedCourses(
                        student.getFollowedCourses().isEmpty() ? null : student.getFollowedCourses()
                                .stream()
                                .map(CourseConverter::convert)
                                .toList()
                )
                .build();
    }
}
