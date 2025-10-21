package com.spring.boot.resturantbackend.controllers.product;

import com.spring.boot.resturantbackend.vm.ProductResponseVm;
import com.spring.boot.resturantbackend.dto.ExceptionDto;
import com.spring.boot.resturantbackend.services.product.ProductService;
import com.spring.boot.resturantbackend.vm.product.ProductRequestVm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(
        name = "Product Controller",
        description = "get all products"
)
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Operation(
            summary = "Retrieve all products with pagination",
            description = "Get all restaurant products with pagination support. Returns a paginated list of all available menu items."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved products",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseVm.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Authentication required",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - Access denied",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            )
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<ProductResponseVm> getAllProducts(
            @Parameter(
                    description = "Page number (1-based)",
                    example = "1",
                    required = true
            )
            @RequestParam int page,
            @Parameter(
                    description = "Number of items per page",
                    example = "10",
                    required = true
            )
            @RequestParam int size
    ) {
        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }

    @Operation(
            summary = "Retrieve products by category with pagination",
            description = "Get all products belonging to a specific category with pagination support. " +
                          "Useful for filtering menu items by food categories like appetizers, main courses, desserts, etc."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved products for the category",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseVm.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Authentication required",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - Access denied",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            )
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseVm> getAllProductsByCategoryId(
            @Parameter(
                    description = "Unique identifier of the product category",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @Parameter(
                    description = "Page number (1-based)",
                    example = "1",
                    required = true
            )
            @RequestParam int page,
            @Parameter(
                    description = "Number of items per page",
                    example = "10",
                    required = true
            )
            @RequestParam int size
    ) {
        return ResponseEntity.ok(productService.getAllProductsByCategoryId(id, page, size));
    }


    @Operation(
            summary = "Search products by keyword with pagination",
            description = "Search for products using a keyword that matches product names or descriptions. " +
                          "Supports partial matching and is case-insensitive."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved products matching the search criteria",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseVm.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Authentication required",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - Access denied",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No products found matching the search criteria",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            )
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/by-key")
    public ResponseEntity<ProductResponseVm> getAllProductsByKey(
            @Parameter(
                    description = "Search keyword to filter products by name or description",
                    example = "pizza",
                    required = true
            )
            @RequestParam String key,

            @Parameter(
                    description = "Page number (1-based)",
                    example = "1",
                    required = true
            )
            @RequestParam int page,

            @Parameter(
                    description = "Number of items per page",
                    example = "10",
                    required = true
            )
            @RequestParam int size
    ) {
        return ResponseEntity.ok(productService.getAllProductsByKey(key, page, size));
    }

    @Operation(
            summary = "Search products by keyword and category with pagination",
            description = "Advanced search combining both category filtering and keyword matching. " +
                          "Returns products that belong to the specified category AND match the search keyword."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved products matching both category and search criteria",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseVm.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Authentication required",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - Access denied",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No products found matching the combined search criteria",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            )
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/by-key-and-category-id")
    public ResponseEntity<ProductResponseVm> getAllProductsByKeyAndCategoryId(
            @Parameter(
                    description = "Unique identifier of the product category to filter by",
                    example = "2",
                    required = true
            )
            @RequestParam Long categoryId,

            @Parameter(
                    description = "Search keyword to filter products by name or description within the category",
                    example = "margherita",
                    required = true
            )
            @RequestParam String key,

            @Parameter(
                    description = "Page number (1-based)",
                    example = "1",
                    required = true
            )
            @RequestParam int page,

            @Parameter(
                    description = "Number of items per page",
                    example = "10",
                    required = true
            )
            @RequestParam int size
    ) {
        return ResponseEntity.ok(productService.getAllProductsByCategoryIdAndKey(categoryId, key, page, size));
    }

    @Operation(
            summary = "Delete a product",
            description = "Remove a product from the menu. This operation is restricted to administrators only. " +
                          "Once deleted, the product will no longer be available for ordering."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Product successfully deleted"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Authentication required",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - Admin role required",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict - Product cannot be deleted due to existing references",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> deleteProductById(
            @Parameter(
                    description = "Unique identifier of the product to be deleted",
                    example = "123",
                    required = true
            )
            @RequestParam Long id
    ) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Create a new product",
            description = "Add a new product to the restaurant menu. This operation is restricted to administrators only. " +
                          "The product will be immediately available for customers to order once created."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Product successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductRequestVm.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request - Invalid product data",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Authentication required",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - Admin role required",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict - Product with similar details already exists",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Unprocessable entity - Validation failed",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductRequestVm> createProduct(@Valid @RequestBody ProductRequestVm productRequestVm) {
        return ResponseEntity.created(URI.create("/create-product")).body(
                productService.createProduct(productRequestVm)
        );
    }
}
