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

/**
 * Service responsable de la logique métier liée à l’authentification et à l’inscription des utilisateurs.
 * <p>
 * Il gère la création de nouveaux comptes, la vérification des identifiants,
 * et la génération de tokens JWT pour sécuriser l’accès aux ressources.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    /** Référentiel pour la gestion des utilisateurs. */
    private final UserRepository userRepository;

    /** Référentiel pour la gestion des identifiants (credentials). */
    private final CredentialRepository credentialRepository;

    /** Encodeur de mots de passe utilisé pour sécuriser les informations d’authentification. */
    private final PasswordEncoder passwordEncoder;

    /** Service de génération et validation de tokens JWT. */
    private final JwtService jwtService;

    /** Gestionnaire d’authentification Spring Security. */
    private final AuthenticationManager authenticationManager;

    /**
     * Enregistre un nouvel utilisateur dans le système.
     *
     * @param req objet {@link RegisterReq} contenant les informations d’inscription (nom, prénom, username, mot de passe, etc.)
     * @return un objet {@link AuthRes} contenant le token JWT attribué à l’utilisateur nouvellement créé
     */
    public AuthRes register(RegisterReq req) {
        // Création et sauvegarde des informations d’identification
        var credential = Credential.builder()
                .username(req.username())
                .password(passwordEncoder.encode(req.password()))
                .isActive(true)
                .build();
        credentialRepository.save(credential);

        // Création et sauvegarde de l’utilisateur
        var user = User.builder()
                .fname(req.fname())
                .lname(req.lname())
                .credential(credential)
                .role(Role.USER)
                .build();
        userRepository.save(user);

        // Génération du token JWT
        var jwtToken = jwtService.generateToken(user);

        // Retour de la réponse d’authentification
        return AuthRes.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Authentifie un utilisateur existant à partir de ses identifiants.
     * <p>
     * Si l’authentification réussit, un token JWT est généré et renvoyé au client.
     *
     * @param req objet {@link AuthReq} contenant le nom d’utilisateur et le mot de passe
     * @return un objet {@link AuthRes} contenant le token JWT de session
     * @throws UsernameNotFoundException si le nom d’utilisateur n’existe pas dans la base de données
     */
    public AuthRes authenticate(AuthReq req) {
        // Vérifie la validité des identifiants via Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.username(),
                        req.password()
                )
        );

        // Récupère l’utilisateur correspondant
        var user = userRepository.findByCredentialUsername(req.username())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        // Génère un token JWT pour l’utilisateur authentifié
        var jwtToken = jwtService.generateToken(user);

        // Retourne la réponse d’authentification
        return AuthRes.builder()
                .token(jwtToken)
                .build();
    }
}
