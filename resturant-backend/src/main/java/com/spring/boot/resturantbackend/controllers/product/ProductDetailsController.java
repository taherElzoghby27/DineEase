package com.spring.boot.resturantbackend.controllers.product;

import com.spring.boot.resturantbackend.dto.ExceptionDto;
import com.spring.boot.resturantbackend.dto.product.ProductDetailsDto;
import com.spring.boot.resturantbackend.services.product.ProductDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "Product Details Controller", description = "Product Details Operations")
@RestController
@RequestMapping("/product-details")
public class ProductDetailsController {
    @Autowired
    private ProductDetailsService productDetailsService;

    @Operation(summary = "add product details to product")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status add product details"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Http Status Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionDto.class))
            )
            ,})
    @PostMapping("/add-product-details")
    public ResponseEntity<ProductDetailsDto> addProductDetails(@Valid @RequestBody ProductDetailsDto productDetailsDto) {
        return ResponseEntity.created(
                URI.create("/add-product-details-to-product")
        ).body(
                productDetailsService.addProductDetailsToProduct(productDetailsDto)
        );
    }

    @Operation(summary = "get product details by product id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status get product details"),
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
    @GetMapping("/get-product-details")
    public ResponseEntity<ProductDetailsDto> getProductDetailsByProductId(@RequestParam Long id) {
        return ResponseEntity.ok(productDetailsService.getProductDetailsByProductId(id));
    }


    @Operation(summary = "update product details")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status get product details"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status internal server error",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ExceptionDto.class
                            )
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
    @PutMapping("/update-product-details")
    public ResponseEntity<ProductDetailsDto> updateProductDetailsByProductId(@RequestBody @Valid ProductDetailsDto productDetailsDto) {
        return ResponseEntity.ok(productDetailsService.updateProductDetails(productDetailsDto));
    }
}
