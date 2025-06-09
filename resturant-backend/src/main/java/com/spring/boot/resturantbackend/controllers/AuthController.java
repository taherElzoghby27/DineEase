package com.spring.boot.resturantbackend.controllers;
import com.spring.boot.resturantbackend.services.AuthService;
import com.spring.boot.resturantbackend.vm.Security.UserAuthRequestVm;
import com.spring.boot.resturantbackend.vm.Security.UserAuthResponseVm;
import jakarta.transaction.SystemException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserAuthResponseVm> signUp(@RequestBody @Valid UserAuthRequestVm userAuthRequestVm) throws SystemException {
        return ResponseEntity.created(URI.create("/sign-up")).body(authService.signUp(userAuthRequestVm));
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuthResponseVm> login(@RequestBody @Valid UserAuthRequestVm userAuthRequestVm) throws SystemException {
        return ResponseEntity.ok(authService.login(userAuthRequestVm));
    }
}
