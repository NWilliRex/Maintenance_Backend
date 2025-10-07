package com.wam.lab1_maintenance.controller;

import com.wam.lab1_maintenance.request.AuthReq;
import com.wam.lab1_maintenance.request.AuthRes;
import com.wam.lab1_maintenance.request.RegisterReq;
import com.wam.lab1_maintenance.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthRes> register(
            @RequestBody final RegisterReq authReq
    ) {
        return ResponseEntity.ok(authService.register(authReq));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthRes> authenticate(
            @RequestBody final AuthReq authReq
    ) {
        return ResponseEntity.ok(authService.authenticate(authReq));
    }
}
