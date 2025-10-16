package com.wam.lab1_maintenance.config;

import com.wam.lab1_maintenance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration principale de l'application pour la gestion de la sécurité.
 * <p>
 * Cette classe définit les beans nécessaires à l'authentification :
 * <ul>
 *     <li>Service de chargement des utilisateurs ({@link UserDetailsService})</li>
 *     <li>Encodeur de mots de passe ({@link PasswordEncoder})</li>
 *     <li>Fournisseur d’authentification ({@link AuthenticationProvider})</li>
 *     <li>Gestionnaire d’authentification ({@link AuthenticationManager})</li>
 * </ul>
 * Elle s’appuie sur {@link UserRepository} pour la récupération des utilisateurs.
 */
@Configuration
@RequiredArgsConstructor
public class AppConfig {

    /** Référentiel des utilisateurs utilisé pour charger les informations d'identification. */
    private final UserRepository userRepository;

    /**
     * Crée un bean {@link UserDetailsService} permettant de charger les utilisateurs
     * à partir du {@link UserRepository} en fonction de leur nom d'utilisateur.
     *
     * @return une implémentation de {@link UserDetailsService}
     * @throws UsernameNotFoundException si aucun utilisateur n'est trouvé avec le nom donné
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByCredentialUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    /**
     * Crée un bean {@link PasswordEncoder} utilisant l'algorithme BCrypt
     * pour encoder et vérifier les mots de passe.
     *
     * @return un encodeur BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Crée et configure un {@link AuthenticationProvider} basé sur la DAO,
     * utilisant le {@link UserDetailsService} et le {@link PasswordEncoder}.
     *
     * @return un fournisseur d’authentification configuré
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Fournit un {@link AuthenticationManager} à partir de la configuration d’authentification.
     *
     * @param config la configuration d’authentification Spring
     * @return un gestionnaire d’authentification prêt à l’emploi
     * @throws Exception en cas d’erreur de configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
