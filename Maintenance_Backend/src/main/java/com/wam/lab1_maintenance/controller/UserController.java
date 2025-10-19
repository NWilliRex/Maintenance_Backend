package com.wam.lab1_maintenance.controller;

import com.wam.lab1_maintenance.model.User;
import com.wam.lab1_maintenance.service.UserService;
import com.wam.lab1_maintenance.request.PersonRequestBody;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST responsable de la gestion des utilisateurs.
 * <p>
 * Permet d’effectuer les opérations CRUD (création, lecture, mise à jour, suppression)
 * ainsi que la recherche d’utilisateurs selon leur nom.
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    /**
     * Récupère la liste de tous les utilisateurs.
     *
     * @return une liste d’objets {@link User} représentant tous les utilisateurs existants
     */
    @GetMapping("/getAll")
    public List<User> getAllPerson() {
        return userService.getPersons();
    }

    /**
     * Recherche des utilisateurs par nom.
     *
     * @param name le nom (ou partie du nom) à rechercher
     * @return une liste d’utilisateurs dont le nom correspond au critère
     */
    @GetMapping("/persons/search")
    public List<User> searchPerson(@RequestParam String name) {
        return userService.searchPerson(name);
    }

    /**
     * Crée un nouvel utilisateur dans le système.
     *
     * @param body les informations de l’utilisateur à créer
     * @return l’utilisateur nouvellement créé
     */
    @PostMapping("/persons")
    public User createPerson(@RequestBody PersonRequestBody body) {
        return userService.createPerson(body);
    }

    /**
     * Met à jour les informations d’un utilisateur existant.
     *
     * @param id   l’identifiant unique de l’utilisateur à modifier
     * @param body les nouvelles informations à appliquer
     * @return l’utilisateur mis à jour
     */
    @PutMapping("/persons/{id}")
    public User updatePerson(@PathVariable Long id, @RequestBody PersonRequestBody body) {
        return userService.updatePerson(body, id);
    }

    /**
     * Supprime un utilisateur du système.
     *
     * @param id l’identifiant unique de l’utilisateur à supprimer
     * @return l’utilisateur supprimé
     */
    @DeleteMapping("/persons/{id}")
    public User deletePerson(@PathVariable Long id) {
        return userService.deletePerson(id);
    }
}
