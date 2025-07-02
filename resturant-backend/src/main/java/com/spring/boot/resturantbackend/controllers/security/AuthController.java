package com.spring.boot.resturantbackend.controllers.security;

import com.spring.boot.resturantbackend.dto.ExceptionDto;
import com.spring.boot.resturantbackend.services.security.AuthService;
import com.spring.boot.resturantbackend.vm.Security.AccountAuthRequestVm;
import com.spring.boot.resturantbackend.vm.Security.AccountAuthResponseVm;
import com.spring.boot.resturantbackend.vm.Security.UpdateProfileVm;
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

@Tag(name = "Auth Controller", description = " Sign up, login , get profile ")
@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @Operation(summary = "sign up")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Http Status sign up"),
            @ApiResponse(responseCode = "500", description = "Http Status internal server error",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
    })
    @PostMapping("/sign-up")
    public ResponseEntity<AccountAuthResponseVm> signUp(@RequestBody @Valid AccountAuthRequestVm accountAuthRequestVm) {
        return ResponseEntity.created(URI.create("/sign-up")).body(authService.signUp(accountAuthRequestVm));
    }

    @Operation(summary = "login")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Http Status sign up"),
            @ApiResponse(responseCode = "500", description = "Http Status internal server error",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
    })
    @PostMapping("/login")
    public ResponseEntity<AccountAuthResponseVm> login(@RequestBody @Valid AccountAuthRequestVm accountAuthRequestVm) {
        return ResponseEntity.ok(authService.login(accountAuthRequestVm));
    }

    @Operation(summary = "get profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Http Status get profile"),
            @ApiResponse(responseCode = "500", description = "Http Status internal server error",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
    })
    @GetMapping("/profile")
    public ResponseEntity<UpdateProfileVm> getProfile() {
        return ResponseEntity.ok(authService.getProfile());
    }

    @Operation(summary = "update profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Http Status update profile"),
            @ApiResponse(responseCode = "500", description = "Http Status internal server error",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
    })
    @PutMapping("/update-profile")
    public ResponseEntity<UpdateProfileVm> updateProfile(@Valid @RequestBody UpdateProfileVm updateProfileVm) {
        return ResponseEntity.created(URI.create("/update-profile")).body(authService.updateProfile(updateProfileVm));
    }
}
