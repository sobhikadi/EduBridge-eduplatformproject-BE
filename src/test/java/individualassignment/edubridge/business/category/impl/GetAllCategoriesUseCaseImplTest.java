package individualassignment.edubridge.business.category.impl;

import individualassignment.edubridge.domain.categories.Category;
import individualassignment.edubridge.domain.categories.responses.GetAllCategoriesResponse;
import individualassignment.edubridge.persistence.categories.CategoryRepository;
import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllCategoriesUseCaseImplTest {

    @Mock
    private CategoryRepository categoryRepositoryMock;

    @InjectMocks
    private GetAllCategoriesUseCaseImpl getAllCategoriesUseCase;

    @Test
    void testGetAllCategories_shouldReturnAllCategories() {
        CategoryEntity category1 = new CategoryEntity(1L, "Category 1");
        CategoryEntity category2 = new CategoryEntity(2L, "Category 2");

        when(categoryRepositoryMock.findAll(any(Sort.class))).thenReturn(List.of(category1, category2));

        GetAllCategoriesResponse response = getAllCategoriesUseCase.getAllCategories();

        List<Category> expectedCategories = Stream.of(category1, category2)
                        .map(CategoryConverter::convert)
                        .toList();

        assertEquals(expectedCategories, response.getCategories());
        verify(categoryRepositoryMock, times(1)).findAll(any(Sort.class));

    }

    @Test
    void testGetAllCategories_shouldReturnEmptyListWhenNoCategoriesFound() {
        when(categoryRepositoryMock.findAll(any(Sort.class))).thenReturn(List.of());

        GetAllCategoriesResponse response = getAllCategoriesUseCase.getAllCategories();

        assertTrue(response.getCategories().isEmpty());
        verify(categoryRepositoryMock, times(1)).findAll(any(Sort.class));
    }
}