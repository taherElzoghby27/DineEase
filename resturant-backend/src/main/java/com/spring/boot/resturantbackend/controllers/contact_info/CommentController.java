package com.spring.boot.resturantbackend.controllers.contact_info;

import com.spring.boot.resturantbackend.dto.ExceptionDto;
import com.spring.boot.resturantbackend.dto.contact_info.CommentDto;
import com.spring.boot.resturantbackend.services.contact_info.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Comment Controller", description = "operations on Comments Contact Info")
@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Operation(
            summary = "send comment",
            description = "send comment for contact info",
            requestBody = @RequestBody(
                    required = true
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status send comment"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status internal server error",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ExceptionDto.class
                            )
                    )
            ),
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping
    public ResponseEntity<Void> sendComment(@Valid @RequestBody CommentDto commentDto) {
        commentService.sendComment(commentDto);
        return ResponseEntity.ok().build();
    }
}
