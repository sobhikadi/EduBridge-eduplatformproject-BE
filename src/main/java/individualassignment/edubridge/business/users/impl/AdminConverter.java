package individualassignment.edubridge.business.users.impl;

import individualassignment.edubridge.domain.users.Admin;
import individualassignment.edubridge.persistence.users.entities.AdminEntity;

public class AdminConverter {
    private AdminConverter() {
    }

    public static Admin convert(AdminEntity admin) {

            return Admin.builder()
                    .id(admin.getId())
                    .firstName(admin.getFirstName())
                    .lastName(admin.getLastName())
                    .publishName(admin.getPublishName())
                    .lastModified(admin.getLastModified())
                    .build();
    }
}
