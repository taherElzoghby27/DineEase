package com.spring.boot.resturantbackend.controllers.product;

import com.spring.boot.resturantbackend.dto.ExceptionDto;
import com.spring.boot.resturantbackend.dto.product.ProductDetailsDto;
import com.spring.boot.resturantbackend.services.product.ProductDetailsService;
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

@Tag(name = "Product Details Controller", description = "Product Details Operations")
@RestController
@RequestMapping("/product-details")
public class ProductDetailsController {
    @Autowired
    private ProductDetailsService productDetailsService;

    @Operation(
            summary = "Add detailed information to a product",
            description = "Associate additional details with an existing product such as nutritional information, " +
                          "allergen data, preparation time, dietary restrictions, or special cooking instructions. " +
                          "This operation is restricted to administrators only."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Product details successfully added",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDetailsDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request - Invalid product details data",
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
                    responseCode = "404",
                    description = "Product not found - Cannot add details to non-existent product",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict - Product already has details associated",
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
    public ResponseEntity<ProductDetailsDto> addProductDetails(@Valid @RequestBody ProductDetailsDto productDetailsDto) {
        return ResponseEntity.created(
                URI.create("/add-product-details-to-product")
        ).body(
                productDetailsService.addProductDetailsToProduct(productDetailsDto)
        );
    }

    @Operation(
            summary = "Retrieve product details by product ID",
            description = "Get comprehensive details for a specific product including nutritional information, " +
                          "allergens, preparation time, dietary information, and any special attributes. " +
                          "Available to all authenticated users for viewing menu item details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved product details",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDetailsDto.class)
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
                    description = "Product not found or no details available for this product",
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
    public ResponseEntity<ProductDetailsDto> getProductDetailsByProductId(
            @Parameter(
                    description = "Unique identifier of the product whose details are being requested",
                    example = "123",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(productDetailsService.getProductDetailsByProductId(id));
    }


    @Operation(
            summary = "Update existing product details",
            description = "Modify the detailed information of an existing product. This allows updating " +
                          "nutritional data, allergen information, preparation instructions, or any other " +
                          "supplementary product attributes. This operation is restricted to administrators only."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product details successfully updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDetailsDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request - Invalid product details data",
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
                    responseCode = "404",
                    description = "Product or product details not found",
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
    @PutMapping
    public ResponseEntity<ProductDetailsDto> updateProductDetailsByProductId(@RequestBody @Valid ProductDetailsDto productDetailsDto) {
        return ResponseEntity.ok(productDetailsService.updateProductDetails(productDetailsDto));
    }
}
