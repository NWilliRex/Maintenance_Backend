package com.wam.lab1_maintenance.config;

import com.wam.lab1_maintenance.model.Series;
import com.wam.lab1_maintenance.model.Episode;
import com.wam.lab1_maintenance.model.User;
import com.wam.lab1_maintenance.model.RatingSeries;
import com.wam.lab1_maintenance.model.History;
import com.wam.lab1_maintenance.model.Gender;
import com.wam.lab1_maintenance.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Classe responsable de l’initialisation (seeding) de la base de données.
 * <p>
 * Ce composant s’exécute automatiquement au démarrage de l’application
 * si le profil actif est <b>"seed"</b>. Il crée des séries, des épisodes,
 * des utilisateurs, des notes et un historique de visionnage
 * pour faciliter les tests et le développement.
 * </p>
 *
 * <p>Les données sont générées aléatoirement afin de simuler un environnement réaliste.</p>
 */
@Component
@Profile("seed")
public class DataSeeder implements CommandLineRunner {

    /** Référentiel pour les séries. */
    @Autowired private SeriesRepository seriesRepo;

    /** Référentiel pour les épisodes. */
    @Autowired private EpisodeRepository episodeRepo;

    /** Référentiel pour les utilisateurs. */
    @Autowired private UserRepository userRepo;

    /** Référentiel pour les notes des séries. */
    @Autowired private RatingSeriesRepository ratingRepo;

    /** Référentiel pour l’historique de visionnage. */
    @Autowired private HistoryRepository historyRepo;

    /** Générateur aléatoire utilisé pour créer des données variées. */
    private final Random random = new Random();

    /** Encodeur de mot de passe utilisé pour sécuriser les utilisateurs créés. */
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Méthode principale appelée automatiquement lors du démarrage
     * pour insérer les données initiales dans la base.
     *
     * @param args arguments de la ligne de commande
     */
    @Override
    public void run(String... args) {
        System.out.println("=== Starting Data Seeding ===");

        // Vérifie si des données existent déjà pour éviter les doublons
        if (seriesRepo.count() > 0 || userRepo.count() > 0) {
            System.out.println("Data already exists - no seeding.");
            return;
        }

        seedSeries();
        seedUsers();
        seedRatingsAndHistory();

        System.out.println("Seeding done");
    }

    /**
     * Crée plusieurs séries avec un nombre aléatoire d’épisodes.
     * Chaque série reçoit une note moyenne et chaque épisode a sa propre note.
     */
    private void seedSeries() {
        System.out.println("🎬 Creating series...");

        List<String> seriesNames = Arrays.asList(
                "Batman: The Animated Series",
                "The Batman",
                "Batman Beyond",
                "Batman: Brave and the Bold",
                "Beware the Batman",
                "Gotham",
                "Pennyworth",
                "Titans",
                "Batwoman",
                "Justice League Unlimited"
        );

        for (String name : seriesNames) {
            Series series = new Series();
            series.setTitle(name);
            series.setDescription("About " + name);
            series.setGenre(randomGenre());
            int nbEpisodes = 5 + random.nextInt(16); // 5 à 20 épisodes
            series.setNbEpisodes(nbEpisodes);
            float seriesNote = 6.0f + random.nextFloat() * 3.5f;
            series.setNote(seriesNote);
            seriesRepo.save(series);

            // Création des épisodes associés
            for (int i = 1; i <= nbEpisodes; i++) {
                Episode episode = new Episode();
                episode.setTitle("Episode " + i);
                episode.setEpisodeNumber(i);
                episode.setSeries(series);
                double epNote = 6.0 + random.nextDouble() * 3.5; // note entre 6.0 et 9.5
                episode.setNote(epNote);
                episodeRepo.save(episode);
            }
        }
    }

    /**
     * Crée 25 utilisateurs avec des noms, âges et genres aléatoires.
     * Le premier utilisateur reçoit le rôle d’administrateur.
     */
    private void seedUsers() {
        System.out.println(" Creating users...");

        String[] lastNames = {
                "Itadori", "Fushiguro", "Kugisaki", "Gojo", "Geto",
                "Sukuna", "Nanami", "Todo", "Choso", "Toji"
        };

        for (int i = 1; i <= 25; i++) {
            User user = new User();
            user.setUsername("user" + i);
            user.setPassword(encoder.encode("password"));
            user.setFname("User" + i);
            user.setLname(lastNames[random.nextInt(lastNames.length)]);
            user.setAge(18 + random.nextInt(40));
            user.setGender(Gender.values()[random.nextInt(Gender.values().length)]);
            user.setRole(i == 1 ? "ADMIN" : "USER");
            userRepo.save(user);
        }
    }

    /**
     * Génère des notes de séries et un historique de visionnage
     * pour chaque utilisateur.
     * <p>
     * Certains utilisateurs ("power users") obtiennent plus d’entrées
     * dans leur historique pour simuler une activité plus élevée.
     * </p>
     */
    private void seedRatingsAndHistory() {
        System.out.println("⭐ Generating ratings + history");

        List<User> users = userRepo.findAll();
        List<Series> allSeries = seriesRepo.findAll();
        List<Episode> allEpisodes = episodeRepo.findAll();

        // Sélectionne 5 utilisateurs "power users"
        Set<String> powerUsers = new HashSet<>();
        while (powerUsers.size() < 5) {
            powerUsers.add("user" + (1 + random.nextInt(25)));
        }

        for (User user : users) {
            // Génère entre 3 et 8 notes
            int nbRatings = 3 + random.nextInt(6);
            for (int i = 0; i < nbRatings; i++) {
                Series series = allSeries.get(random.nextInt(allSeries.size()));
                RatingSeries rating = new RatingSeries();
                rating.setSeries(series);
                rating.setUser(user);
                rating.setRating((float) (1 + random.nextInt(5)));
                rating.setRatedAt(randomDateWithinLastMonths());
                ratingRepo.save(rating);
            }

            // Génère entre 5 et 20 historiques, plus pour les power users
            int nbViews = 5 + random.nextInt(16);
            if (powerUsers.contains(user.getUsername())) {
                nbViews += 10 + random.nextInt(11);
            }

            for (int i = 0; i < nbViews; i++) {
                Episode ep = allEpisodes.get(random.nextInt(allEpisodes.size()));
                History history = new History();
                history.setUser(user);
                history.setEpisode(ep);
                history.setWatchedAt(randomDateWithinLastMonths());
                historyRepo.save(history);
            }
        }
    }

    /**
     * Génère une date aléatoire dans les trois derniers mois.
     * <p>Environ 30 % des dates se trouvent dans la dernière semaine.</p>
     *
     * @return une {@link LocalDateTime} aléatoire
     */
    private LocalDateTime randomDateWithinLastMonths() {
        int daysAgo = random.nextInt(90);
        if (random.nextDouble() < 0.3) {
            daysAgo = random.nextInt(7);
        }
        return LocalDateTime.now()
                .minusDays(daysAgo)
                .withHour(random.nextInt(24))
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    /**
     * Retourne un genre aléatoire parmi une liste prédéfinie.
     *
     * @return le genre choisi
     */
    private String randomGenre() {
        String[] genres = {"Crime", "Action", "Animation", "Drama", "Sci-Fi", "Fantasy"};
        return genres[random.nextInt(genres.length)];
    }
}
