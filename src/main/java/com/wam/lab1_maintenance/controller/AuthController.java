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

/**
 * Contrôleur REST gérant l’authentification et l’enregistrement des utilisateurs.
 * <p>
 * Ce contrôleur fournit les points d’entrée pour :
 * <ul>
 *     <li>L’enregistrement d’un nouvel utilisateur (<code>/register</code>)</li>
 *     <li>L’authentification et la génération d’un token JWT (<code>/login</code>)</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Enregistre un nouvel utilisateur à partir des informations fournies.
     *
     * @param authReq les informations d’enregistrement (nom d’utilisateur, mot de passe, etc.)
     * @return une réponse contenant le token JWT et les informations d’utilisateur
     */
    @PostMapping("/register")
    public ResponseEntity<AuthRes> register(
            @RequestBody final RegisterReq authReq
    ) {
        return ResponseEntity.ok(authService.register(authReq));
    }

    /**
     * Authentifie un utilisateur existant et retourne un token JWT s’il est valide.
     *
     * @param authReq les informations de connexion (nom d’utilisateur et mot de passe)
     * @return une réponse contenant le token JWT et les informations d’utilisateur
     */
    @PostMapping("/login")
    public ResponseEntity<AuthRes> authenticate(
            @RequestBody final AuthReq authReq
    ) {
        return ResponseEntity.ok(authService.authenticate(authReq));
    }
}
