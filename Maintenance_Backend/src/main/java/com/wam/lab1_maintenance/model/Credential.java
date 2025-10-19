package com.wam.lab1_maintenance.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente les informations d’identification d’un utilisateur dans le système.
 * <p>
 * Cette entité contient le nom d’utilisateur, le mot de passe chiffré,
 * ainsi qu’un indicateur d’activité (actif ou désactivé).
 * Elle est liée à la table {@code credential} dans la base de données.
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Credential {

    /**
     * Identifiant unique de l’entrée {@link Credential}.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credential_id")
    private Long id;

    /**
     * Nom d’utilisateur unique associé à ce compte.
     * Ce champ ne peut pas être nul.
     */
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    /**
     * Mot de passe chiffré associé à ce compte.
     * Ce champ ne peut pas être nul.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Indique si le compte est actuellement actif.
     * {@code true} → actif, {@code false} → désactivé.
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
