package com.wam.lab1_maintenance.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuration principale de la sécurité Spring Security pour l’application.
 * <p>
 * Gère l’authentification JWT, la configuration CORS et les autorisations HTTP.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * Configure la chaîne de filtres de sécurité (Security Filter Chain).
     * <ul>
     *     <li>Désactive la protection CSRF.</li>
     *     <li>Active la configuration CORS.</li>
     *     <li>Autorise temporairement toutes les requêtes sur <code>/api/**</code> (en mode développement).</li>
     *     <li>Met en place une gestion de session sans état (JWT).</li>
     *     <li>Ajoute le filtre d’authentification JWT avant celui de Spring Security.</li>
     * </ul>
     *
     * @param http l’objet {@link HttpSecurity} fourni par Spring Security
     * @return la chaîne de filtres configurée
     * @throws Exception en cas d’erreur de configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Définit la configuration CORS pour autoriser les requêtes provenant du front-end.
     * <p>
     * Par défaut, autorise les origines depuis <code>http://localhost:5173</code>,
     * les méthodes HTTP standards et tous les en-têtes.
     * </p>
     *
     * @return une instance de {@link CorsConfigurationSource} appliquant les règles CORS
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
