package individualassignment.edubridge.business.users.impl;

import individualassignment.edubridge.domain.users.Teacher;
import individualassignment.edubridge.persistence.users.entities.TeacherEntity;

public class TeacherConverter {
    private TeacherConverter() {
    }

    public static Teacher convert(TeacherEntity teacher) {

            return Teacher.builder()
                    .id(teacher.getId())
                    .firstName(teacher.getFirstName())
                    .lastName(teacher.getLastName())
                    .publishName(teacher.getPublishName())
                    .lastModified(teacher.getLastModified())
                    .address(AddressConverter.convert(teacher.getAddress()))
                    .build();
    }
}
