package individualassignment.edubridge.Business.CourseUseCases.Impl;

import individualassignment.edubridge.Domain.Courses.Category;

import individualassignment.edubridge.Persistence.Courses.Entity.CategoryEntity;


public class CategoryConverter {
    private CategoryConverter(){}

    public static Category convert(CategoryEntity category) {
        return Category.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}