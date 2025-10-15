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

@Component
@Profile("seed")
public class DataSeeder implements CommandLineRunner {

    @Autowired private SeriesRepository seriesRepo;
    @Autowired private EpisodeRepository episodeRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private RatingSeriesRepository ratingRepo;
    @Autowired private HistoryRepository historyRepo;

    private final Random random = new Random();
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) {
        System.out.println("=== Starting Data Seeding ===");


        // Vérifie si des données existent déjà
        if (seriesRepo.count() > 0 || userRepo.count() > 0) {
            System.out.println("Data already exists - no seeding.");
            return;
        }

        seedSeries();
        seedUsers();
        seedRatingsAndHistory();

        System.out.println("Seeding done");
    }

  // creation des séries et épisodes (avec notes) 
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
            int nbEpisodes = 5 + random.nextInt(16); // 5..20
            series.setNbEpisodes(nbEpisodes);
            float seriesNote = 6.0f + random.nextFloat() * 3.5f;
            series.setNote(seriesNote);
            seriesRepo.save(series);

            // create episodes et set une note pour chaque épisode
            for (int i = 1; i <= nbEpisodes; i++) {
                Episode episode = new Episode();
                episode.setTitle("Episode " + i);
                episode.setEpisodeNumber(i);
                episode.setSeries(series); 
                // episode note entre 6.0 and 9.5 
                double epNote = 6.0 + random.nextDouble() * 3.5;
                episode.setNote(epNote);
                episodeRepo.save(episode);
            }
        }
    }


    // creation de 25 users avec fname lname gender age role
    private void seedUsers() {
        System.out.println(" Creating users...");

            String[] lastNames = {
                "Itadori",   
                "Fushiguro", 
                "Kugisaki",
                "Gojo",      
                "Geto",     
                "Sukuna",   
                "Nanami",    
                "Todo",     
                "Choso",     
                "Toji"    
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

   // create note et historique pour chaque user
    private void seedRatingsAndHistory() {
        System.out.println("⭐ Generating ratings + history");

        List<User> users = userRepo.findAll();
        List<Series> allSeries = seriesRepo.findAll();
        List<Episode> allEpisodes = episodeRepo.findAll();

        // 5 utilisateurs qui seront des power users
        Set<String> powerUsers = new HashSet<>();
        while (powerUsers.size() < 5) {
            powerUsers.add("user" + (1 + random.nextInt(25)));
        }

        for (User user : users) {
            // notes, 3 à 8
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

            // historique, 5 à 20, plus pour power users
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

// date random dans les 3 derniers mois, 30 % dans la dernière semaine
    private LocalDateTime randomDateWithinLastMonths() {
        int daysAgo = random.nextInt(90); // dans les 3 derniers mois
        if (random.nextDouble() < 0.3) {
            daysAgo = random.nextInt(7); // 30 % dans la dernière semaine
        }
        // hour random entre 0 and 23
        return LocalDateTime.now().minusDays(daysAgo).withHour(random.nextInt(24)).withMinute(0).withSecond(0).withNano(0);
    }

    private String randomGenre() {
        String[] genres = {"Crime", "Action", "Animation", "Drama", "Sci-Fi", "Fantasy"};
        return genres[random.nextInt(genres.length)];
    }
}
