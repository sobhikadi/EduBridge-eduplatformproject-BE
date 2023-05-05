package individualassignment.edubridge.business.category.impl;

import individualassignment.edubridge.business.category.exceptions.InvalidCategoryIdException;
import individualassignment.edubridge.domain.categories.requests.UpdateCategoryRequest;
import individualassignment.edubridge.persistence.categories.CategoryRepository;
import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCategoryUseCaseImplTest {

    @Mock
    private CategoryRepository categoryRepositoryMock;

    @InjectMocks
    private UpdateCategoryUseCaseImpl updateCategoryUseCase;

    @Test
    void updateCategory_withValidRequest_updatesCategory() {
        UpdateCategoryRequest request = new UpdateCategoryRequest(1L, "Category");
        CategoryEntity category = CategoryEntity.builder().id(1L).name("Old Category Name").build();
        category.setName(request.getName());

        when(categoryRepositoryMock.findById(request.getId())).thenReturn(Optional.of(category));
        when(categoryRepositoryMock.save(category)).thenReturn(category);

        updateCategoryUseCase.updateCategory(request);

        assert(category.getName().equals(request.getName()));
        verify(categoryRepositoryMock, times(1)).findById(1L);
        verify(categoryRepositoryMock, times(1)).save(category);

    }

    @Test
    void updateCategory_withInvalidCategoryId_throwsInvalidCategoryIdException() {
        UpdateCategoryRequest request = UpdateCategoryRequest.builder().id(1L).name("Category").build();

        when(categoryRepositoryMock.findById(request.getId())).thenReturn(Optional.empty());

        assertThrows(InvalidCategoryIdException.class, () -> {
            updateCategoryUseCase.updateCategory(request);
        });

        verify(categoryRepositoryMock, times(1)).findById(1L);
        verify(categoryRepositoryMock, never()).save(any(CategoryEntity.class));
    }
}