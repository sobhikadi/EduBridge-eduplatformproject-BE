package individualassignment.edubridge.controller;


import individualassignment.edubridge.business.categoryUseCases.CreateCategoryUseCase;
import individualassignment.edubridge.business.categoryUseCases.DeleteCategoryUseCase;
import individualassignment.edubridge.business.categoryUseCases.GetAllCategoriesUseCase;
import individualassignment.edubridge.business.categoryUseCases.UpdateCategoryUseCase;
import individualassignment.edubridge.domain.categories.requests.CreateCategoryRequest;
import individualassignment.edubridge.domain.categories.requests.UpdateCategoryRequest;
import individualassignment.edubridge.domain.categories.responses.CreateCategoryResponse;
import individualassignment.edubridge.domain.categories.responses.GetAllCategoriesResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class CategoryController {
    private final CreateCategoryUseCase createCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final GetAllCategoriesUseCase getAllCategoriesUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;

    @PostMapping()
    public ResponseEntity<CreateCategoryResponse> createCategory(@RequestBody @Valid CreateCategoryRequest request){
        CreateCategoryResponse response = createCategoryUseCase.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<GetAllCategoriesResponse> getAllCategories(){
        GetAllCategoriesResponse response = getAllCategoriesUseCase.getAllCategories();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int categoryId) {
        this.deleteCategoryUseCase.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{categoryId}")
    public ResponseEntity<Void> updateCategory(@PathVariable("categoryId") long categoryId, @RequestBody @Valid UpdateCategoryRequest request){
        request.setId(categoryId);
        updateCategoryUseCase.updateCategory(request);
        return ResponseEntity.noContent().build();
    }


}
