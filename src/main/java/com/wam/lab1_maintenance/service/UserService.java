package com.wam.lab1_maintenance.service;

import com.wam.lab1_maintenance.model.User;
import com.wam.lab1_maintenance.repository.UserRepository;
import com.wam.lab1_maintenance.request.PersonRequestBody;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Indique à Spring que cette classe est un "service" (composant métier)
@AllArgsConstructor // Génère automatiquement un constructeur avec tous les attributs (injection de dépendances)
public class UserService {

    // Repository permettant d’interagir avec la base de données pour l’entité User
    private final UserRepository userRepository;

    /**
     * Récupère et retourne la liste complète des utilisateurs dans la base de données.
     * @return liste de tous les utilisateurs
     */
    public List<User> getPersons() {
        return userRepository.findAll();
    }

    /**
     * Recherche des utilisateurs dont le prénom commence par une chaîne donnée (insensible à la casse).
     * @param name chaîne à rechercher
     * @return liste d'utilisateurs correspondant au critère
     */
    public List<User> searchPerson(String name){
        return userRepository.findByFnameStartingWithIgnoreCase(name);
    }

    /**
     * Crée un nouvel utilisateur à partir d’un corps de requête (DTO).
     * @param body données de l'utilisateur à créer
     * @return l'utilisateur créé et sauvegardé en base de données
     */
    public User createPerson(PersonRequestBody body) {
        // Création d’un objet User à partir du contenu du DTO (utilise le pattern Builder)
        User user = User.builder()
                .fname(body.fname())
                .lname(body.lname())
                .age(body.age())
                .gender(body.gender())
                .build();

        // Sauvegarde de l’utilisateur dans la base
        return userRepository.save(user);
    }

    /**
     * Met à jour un utilisateur existant selon les valeurs fournies.
     * Si un champ du body est nul, la valeur actuelle est conservée.
     * @param body nouvelles valeurs à appliquer
     * @param id identifiant de l'utilisateur à modifier
     * @return l'utilisateur mis à jour
     */
    public User updatePerson(PersonRequestBody body, Long id) {
        // Recherche de l'utilisateur par ID, lève une exception si non trouvé
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        // Mise à jour conditionnelle des champs (on garde les valeurs existantes si null)
        user.setFname(body.fname() != null ? body.fname() : user.getFname());
        user.setLname(body.lname() != null ? body.lname() : user.getLname());
        user.setAge(body.age() != null ? body.age() : user.getAge());
        user.setGender(body.gender() != null ? body.gender() : user.getGender());

        // Sauvegarde des modifications en base
        return userRepository.save(user);
    }

    /**
     * Supprime un utilisateur à partir de son identifiant.
     * @param id identifiant de l'utilisateur à supprimer
     * @return l'utilisateur supprimé (utile pour confirmation ou logs)
     */
    public User deletePerson(Long id) {
        // Recherche de l'utilisateur à supprimer
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        // Suppression dans la base
        userRepository.delete(user);

        // Retourne l’utilisateur supprimé
        return user;
    }
}
