package com.wam.lab1_maintenance.service;

import com.wam.lab1_maintenance.model.Series;
import com.wam.lab1_maintenance.repository.SeriesRepository;
import com.wam.lab1_maintenance.request.SeriesRequestBody;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SerieService {

    private final SeriesRepository seriesRepository;

    public List<Series> searchSeriesPersonalised(
            String genre,
            String title,
            Integer nbEpisode) {
        if (genre == null || genre.isEmpty()) {
            return seriesRepository.findByGenre(genre);
        } else if (title == null || title.isEmpty()) {
            return seriesRepository.findByTitleStartingWith(title);
        } else if (nbEpisode == null || nbEpisode < 0) {
            return seriesRepository.findByNbEpisodesGreaterThanEqual(nbEpisode);
        } else {
            return seriesRepository.findAll();
        }
    }

    public List<Series> getAllSeries() {
        return seriesRepository.findAll();
    }

    public Series getSerieById(Long id) {
        return seriesRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Series addSeries(@org.jetbrains.annotations.NotNull SeriesRequestBody body) {
        Series series = Series.builder()
                .title(body.title())
                .genre(body.genre())
                .nbEpisodes(body.nbEpisodes())
                .note(body.note())
                .build();
        seriesRepository.save(series);
        return series;
    }

    public Series updateSeries(Long id, SeriesRequestBody body) {
        Series series = seriesRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        Series updateSeries = Series.builder()
                .id(series.getId())
                .title(body.title() == null ? series.getTitle() : body.title())
                .genre(body.genre() == null ? series.getGenre() : body.genre())
                .nbEpisodes(body.nbEpisodes() == null ? series.getNbEpisodes() : body.nbEpisodes())
                .note(body.note() == null ? series.getNote() : body.note())
                .build();
        return seriesRepository.save(updateSeries);
    }

    public void deleteSeries(Long id) {
        seriesRepository.deleteById(id);
    }
}
