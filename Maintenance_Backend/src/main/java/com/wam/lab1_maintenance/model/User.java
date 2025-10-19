package com.wam.lab1_maintenance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Représente un utilisateur du système.
 * <p>
 * Cette entité stocke les informations personnelles de l’utilisateur,
 * ainsi que ses informations d’authentification via la classe {@link Credential}.
 * Elle implémente {@link UserDetails} pour l’intégration avec Spring Security.
 */
@Entity
@Table(name = "person")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    /**
     * Identifiant unique de l’utilisateur.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    /**
     * Prénom de l’utilisateur.
     */
    @Column(name = "fname", length = 25)
    private String fname;

    /**
     * Nom de famille de l’utilisateur.
     */
    @Column(name = "lname", length = 25)
    private String lname;

    /**
     * Âge de l’utilisateur.
     */
    @Column(name = "age")
    private Integer age;

    /**
     * Genre de l’utilisateur (voir {@link Gender}).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    /**
     * Rôle de l’utilisateur (ADMIN, USER, etc.).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    /**
     * Informations d’identification (nom d’utilisateur, mot de passe, etc.).
     * Lié en relation One-to-One avec la table {@link Credential}.
     */
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "credential_id", referencedColumnName = "credential_id")
    private Credential credential;

    /**
     * Définit le nom d’utilisateur (username).
     * Si aucun {@link Credential} n’est encore associé, un nouvel objet est créé.
     *
     * @param username le nom d’utilisateur à définir
     */
    public void setUsername(String username) {
        if (this.credential == null) {
            this.credential = Credential.builder()
                    .username(username)
                    .password(null)
                    .isActive(true)
                    .build();
        } else {
            this.credential.setUsername(username);
        }
    }

    /**
     * Définit le mot de passe de l’utilisateur.
     * Si aucun {@link Credential} n’est encore associé, un nouvel objet est créé.
     *
     * @param password le mot de passe à définir
     */
    public void setPassword(String password) {
        if (this.credential == null) {
            this.credential = Credential.builder()
                    .username(null)
                    .password(password)
                    .isActive(true)
                    .build();
        } else {
            this.credential.setPassword(password);
        }
    }

    /**
     * Définit le rôle de l’utilisateur à partir d’une chaîne (ex. : "admin" ou "user").
     *
     * @param roleName nom du rôle à appliquer
     */
    public void setRole(String roleName) {
        if (roleName == null) {
            this.role = null;
            return;
        }
        this.role = Role.valueOf(roleName.toUpperCase());
    }

    /**
     * Retourne les autorités (permissions) de l’utilisateur sous forme d’objets {@link GrantedAuthority}.
     *
     * @return collection d’autorisations correspondant au rôle
     */
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role != null ? List.of(new SimpleGrantedAuthority(this.role.name())) : List.of();
    }

    /**
     * Retourne le mot de passe de l’utilisateur.
     *
     * @return mot de passe haché ou {@code null} si absent
     */
    @Override
    @JsonIgnore
    public String getPassword() {
        return this.credential != null ? this.credential.getPassword() : null;
    }

    /**
     * Retourne le nom d’utilisateur (username).
     *
     * @return nom d’utilisateur ou {@code null} si absent
     */
    @Override
    @JsonIgnore
    public String getUsername() {
        return this.credential != null ? this.credential.getUsername() : null;
    }

    /**
     * Indique si le compte est expiré.
     * Toujours {@code true} dans cette implémentation.
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indique si le compte est verrouillé.
     * Toujours {@code true} dans cette implémentation.
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indique si les identifiants sont expirés.
     * Toujours {@code true} dans cette implémentation.
     */
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indique si le compte est actif (activé).
     *
     * @return {@code true} si le compte est actif, sinon {@code false}
     */
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return this.credential != null && this.credential.getIsActive();
    }
}
