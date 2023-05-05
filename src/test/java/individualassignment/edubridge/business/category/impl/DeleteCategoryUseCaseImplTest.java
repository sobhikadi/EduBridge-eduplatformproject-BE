package individualassignment.edubridge.business.category.impl;

import individualassignment.edubridge.persistence.categories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DeleteCategoryUseCaseImplTest {

    @Mock
    private CategoryRepository categoryRepositoryMock;

    @InjectMocks
    private DeleteCategoryUseCaseImpl deleteCategoryUseCase;

    @Test
    void testDeleteCategory_whenCategoryExists_shouldCallDeleteById() {
        Long categoryId = 1L;

        deleteCategoryUseCase.deleteCategory(1L);

        verify(categoryRepositoryMock, times(1)).deleteById(categoryId);
    }

    @Test
    void testDeleteCategory_whenCategoryDoesNotExist_shouldThrowAnEmptyResultException() {
        long categoryId = 1L;

        doThrow(EmptyResultDataAccessException.class)
                .when(categoryRepositoryMock).deleteById(categoryId);

        assertThrows(EmptyResultDataAccessException.class,
                () -> deleteCategoryUseCase.deleteCategory(categoryId));

        verify(categoryRepositoryMock, times(1)).deleteById(categoryId);
    }
}