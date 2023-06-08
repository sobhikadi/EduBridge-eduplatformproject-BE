package individualassignment.edubridge.controller;


import individualassignment.edubridge.business.category.CreateCategoryUseCase;
import individualassignment.edubridge.business.category.DeleteCategoryUseCase;
import individualassignment.edubridge.business.category.GetAllCategoriesUseCase;
import individualassignment.edubridge.business.category.UpdateCategoryUseCase;
import individualassignment.edubridge.configuration.security.isauthenticated.IsAuthenticated;
import individualassignment.edubridge.domain.categories.requests.CreateCategoryRequest;
import individualassignment.edubridge.domain.categories.requests.UpdateCategoryRequest;
import individualassignment.edubridge.domain.categories.responses.CreateCategoryResponse;
import individualassignment.edubridge.domain.categories.responses.GetAllCategoriesResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
    private final CreateCategoryUseCase createCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final GetAllCategoriesUseCase getAllCategoriesUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
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

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @DeleteMapping("{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int categoryId) {
        try{
            this.deleteCategoryUseCase.deleteCategory(categoryId);
        }catch (Exception e){
            String messageType = e.getClass().getSimpleName();
            if(messageType.equals("DataIntegrityViolationException")){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @PutMapping("{categoryId}")
    public ResponseEntity<Void> updateCategory(@PathVariable("categoryId") long categoryId, @RequestBody @Valid UpdateCategoryRequest request){
        request.setId(categoryId);
        updateCategoryUseCase.updateCategory(request);
        return ResponseEntity.noContent().build();
    }


}
