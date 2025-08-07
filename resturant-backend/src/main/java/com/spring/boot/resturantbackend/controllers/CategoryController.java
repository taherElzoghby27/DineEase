package com.spring.boot.resturantbackend.controllers;

import com.spring.boot.resturantbackend.dto.CategoryDto;
import com.spring.boot.resturantbackend.dto.ExceptionDto;
import com.spring.boot.resturantbackend.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.SystemException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(
        name = "Category Controller",
        description = "get all categories,create,get by id"
)
@RestController
@RequestMapping("/categories")
@CrossOrigin("http://localhost:4200")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Operation(
            summary = "get all categories",
            description = "all categories in resturant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status get all categories"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            ),
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/all-categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @Operation(
            summary = "create category",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status create category"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            ),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-category")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return ResponseEntity.created(URI.create("create-category")).body(categoryService.createCategory(categoryDto));
    }

    @Operation(
            summary = "get category by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status get category by id"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Http Status Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            ),
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get-category/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(
            @Parameter(
                    description = "Unique identifier of the product category to retrieve",
                    example = "5",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @Operation(
            summary = "update category",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status update category"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            ),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-category")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto));
    }

    @Operation(
            summary = "delete category by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status delete category by id"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Http Status Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            ),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-category/{id}")
    public ResponseEntity<Void> deleteCategoryById(
            @Parameter(
                    description = "Unique identifier of the product category to delete",
                    example = "5",
                    required = true
            )
            @PathVariable Long id
    ) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }


}
