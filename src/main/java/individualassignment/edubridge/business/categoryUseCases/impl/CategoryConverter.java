package individualassignment.edubridge.business.categoryUseCases.impl;

import individualassignment.edubridge.domain.categories.Category;

import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;


public class CategoryConverter {
    private CategoryConverter(){}

    public static Category convert(CategoryEntity category) {
        return Category.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}