package com.wam.lab1_maintenance.service;

import com.wam.lab1_maintenance.config.JwtService;
import com.wam.lab1_maintenance.model.Credential;
import com.wam.lab1_maintenance.model.Role;
import com.wam.lab1_maintenance.model.User;
import com.wam.lab1_maintenance.repository.CredentialRepository;
import com.wam.lab1_maintenance.repository.UserRepository;
import com.wam.lab1_maintenance.request.AuthReq;
import com.wam.lab1_maintenance.request.AuthRes;
import com.wam.lab1_maintenance.request.RegisterReq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthRes register(RegisterReq req) {
        var credential = Credential.builder()
                .username(req.username())
                .password(passwordEncoder.encode(req.password()))
                .isActive(true)
                .build();
        credentialRepository.save(credential);

        var user = User.builder()
                .fname(req.fname())
                .lname(req.lname())
                .credential(credential)
                .role(Role.USER)
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthRes.builder()
                .token(jwtToken)
                .build();
    }

    public AuthRes authenticate(AuthReq req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.username(),
                        req.password()
                )
        );

        var user = userRepository.findByCredentialUsername(req.username())
                            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthRes.builder()
                .token(jwtToken)
                .build();
    }
}
