package individualassignment.edubridge.business.category.impl;

import individualassignment.edubridge.business.category.exceptions.CategoryNameAlreadyExistsException;
import individualassignment.edubridge.domain.categories.requests.CreateCategoryRequest;
import individualassignment.edubridge.domain.categories.responses.CreateCategoryResponse;
import individualassignment.edubridge.persistence.categories.CategoryRepository;
import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCategoryUseCaseImplTest {

    @Mock
    private CategoryRepository categoryRepositoryMock;

    @InjectMocks
    private CreateCategoryUseCaseImpl createCategoryUseCase;

    @Test
    void testCreateCategory_whenCategoryDoesNotExist_shouldSaveNewCategoryAndReturnResponse() {
        CreateCategoryRequest request = CreateCategoryRequest.builder()
                .name("Test Category")
                .build();

        when(categoryRepositoryMock.existsByName(request.getName())).thenReturn(false);
        when(categoryRepositoryMock.save(any())).thenReturn(CategoryEntity.builder()
                .id(1L)
                .name(request.getName())
                .build());

        CreateCategoryResponse response = createCategoryUseCase.createCategory(request);

        verify(categoryRepositoryMock, times(1)).save(any(CategoryEntity.class));
        assertEquals(1L, response.getCategoryId() );
    }

    @Test
    void testCreateCategory_whenCategoryAlreadyExists_shouldThrowException() {
        CreateCategoryRequest request = CreateCategoryRequest.builder()
                .name("Test Category")
                .build();

        when(categoryRepositoryMock.existsByName(request.getName())).thenReturn(true);

        assertThrows(
                CategoryNameAlreadyExistsException.class,
                () -> createCategoryUseCase.createCategory(request));
        verify(categoryRepositoryMock, never()).save(any(CategoryEntity.class));
    }
}