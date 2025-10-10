package com.wam.lab1_maintenance.service;

import com.wam.lab1_maintenance.model.RatingSeries;
import com.wam.lab1_maintenance.model.Series;
import com.wam.lab1_maintenance.model.User;
import com.wam.lab1_maintenance.repository.RatingSeriesRepository;
import com.wam.lab1_maintenance.repository.SeriesRepository;
import com.wam.lab1_maintenance.repository.UserRepository;
import com.wam.lab1_maintenance.request.RatingRequestBody;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class RatingSerieService {

    private final RatingSeriesRepository ratingSeriesRepository;
    private final UserRepository userRepository;
    private final SeriesRepository seriesRepository;

    public void addUserSeriesRating(long userId, long seriesId, float rating) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Erreur! Le user n'a pas été trouvé"));

        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Erreur! La série n'a pas été trouvée"));

        if (ratingSeriesRepository.findByUserIdAndSeriesId(userId, seriesId) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Erreur! Cette série a déjà reçue une note");
        }

        RatingSeries ratingsSeries = RatingSeries.builder()
                .series(series)
                .user(user)
                .userRating(rating)
                .build();

        ratingSeriesRepository.save(ratingsSeries);
    }

    public Float getUserSeriesRating(long userId, long seriesId) {
        RatingSeries ratingsSeries = ratingSeriesRepository.findByUserIdAndSeriesId(userId, seriesId);
        return ratingsSeries.getUserRating();
    }

    public Float getSeriesRating(long id) {
        Series series = seriesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Episode not found"));
        return series.getNote();
    }

    public RatingSeries updateUserSeriesRating(long userId, long seriesId, RatingRequestBody body) {

        RatingSeries ratingsSeries = ratingSeriesRepository.findByUserIdAndSeriesId(userId, seriesId);

        RatingSeries updateRatingsSeries = RatingSeries.builder()
                .id(ratingsSeries.getId())
                .user(ratingsSeries.getUser())
                .series(ratingsSeries.getSeries())
                .userRating(body.userRating() == null ? ratingsSeries.getUserRating() : body.userRating())
                .build();

        return ratingSeriesRepository.save(updateRatingsSeries);
    }
}
