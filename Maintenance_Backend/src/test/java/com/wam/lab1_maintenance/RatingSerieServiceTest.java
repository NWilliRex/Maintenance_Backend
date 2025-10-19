package com.wam.lab1_maintenance;

import com.wam.lab1_maintenance.model.RatingSeries;
import com.wam.lab1_maintenance.model.Series;
import com.wam.lab1_maintenance.model.User;
import com.wam.lab1_maintenance.repository.RatingSeriesRepository;
import com.wam.lab1_maintenance.repository.SeriesRepository;
import com.wam.lab1_maintenance.repository.UserRepository;
import com.wam.lab1_maintenance.request.RatingRequestBody;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.wam.lab1_maintenance.service.RatingSerieService;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RatingSerieServiceTest {

    @Autowired
    private RatingSerieService ratingSerieService;

    @Autowired
    private RatingSeriesRepository ratingSeriesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    private User user;
    private Series series;

    @BeforeEach
    void setUp() {
        user = userRepository.save(User.builder()
                .fname("John")
                .lname("Doe")
                .build());

        series = seriesRepository.save(Series.builder()
                .title("My Series")
                .note(4.3f)
                .build());
    }

    @Test
    void addUserSeriesRating_ShouldAddRating_WhenUserAndSeriesExist() {
        ratingSerieService.addUserSeriesRating(user.getId(), series.getId(), 4.5f);

        RatingSeries rating = ratingSeriesRepository.findByUserIdAndSeriesId(user.getId(), series.getId());
        assertThat(rating).isNotNull();
        assertThat(rating.getUserRating()).isEqualTo(4.5f);
    }

    @Test
    void addUserSeriesRating_ShouldThrowNotFound_WhenUserOrSeriesMissing() {
        Long invalidUserId = 999L;
        Long invalidSeriesId = 888L;

        assertThatThrownBy(() ->
                ratingSerieService.addUserSeriesRating(invalidUserId, series.getId(), 4.0f))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("user n'a pas été trouvé");

        assertThatThrownBy(() ->
                ratingSerieService.addUserSeriesRating(user.getId(), invalidSeriesId, 4.0f))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("série n'a pas été trouvée");
    }

    @Test
    void addUserSeriesRating_ShouldThrowConflict_WhenRatingAlreadyExists() {
        ratingSerieService.addUserSeriesRating(user.getId(), series.getId(), 4.0f);

        assertThatThrownBy(() ->
                ratingSerieService.addUserSeriesRating(user.getId(), series.getId(), 3.5f))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("déjà reçue une note")
                .extracting(e -> ((ResponseStatusException) e).getStatusCode())
                .isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void getUserSeriesRating_ShouldReturnRatingValue() {
        ratingSerieService.addUserSeriesRating(user.getId(), series.getId(), 4.2f);

        Float ratingValue = ratingSerieService.getUserSeriesRating(user.getId(), series.getId());

        assertThat(ratingValue).isEqualTo(4.2f);
    }

    @Test
    void getSeriesRating_ShouldReturnSeriesNote() {
        Float result = ratingSerieService.getSeriesRating(series.getId());

        assertThat(result).isEqualTo(4.3f);
    }

    @Test
    void getSeriesRating_ShouldThrowException_WhenSeriesNotFound() {
        assertThatThrownBy(() -> ratingSerieService.getSeriesRating(999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Episode not found");
    }

    @Test
    void updateUserSeriesRating_ShouldUpdateExistingRating() {
        // Given an existing rating
        ratingSerieService.addUserSeriesRating(user.getId(), series.getId(), 3.8f);

        RatingRequestBody updateBody = new RatingRequestBody(user, 4.7f);
        RatingSeries updated = ratingSerieService.updateUserSeriesRating(user.getId(), series.getId(), updateBody);

        assertThat(updated.getUserRating()).isEqualTo(4.7f);

        RatingSeries inDb = ratingSeriesRepository.findByUserIdAndSeriesId(user.getId(), series.getId());
        assertThat(inDb.getUserRating()).isEqualTo(4.7f);
    }
}
