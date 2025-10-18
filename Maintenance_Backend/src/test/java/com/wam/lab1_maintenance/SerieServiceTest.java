package com.wam.lab1_maintenance;

import com.wam.lab1_maintenance.model.Series;
import com.wam.lab1_maintenance.repository.SeriesRepository;
import com.wam.lab1_maintenance.request.SeriesRequestBody;
import com.wam.lab1_maintenance.service.SerieService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class SerieServiceTest {

    @Mock
    private SeriesRepository seriesRepository;

    @InjectMocks
    private SerieService serieService;

    private Series sampleSeries;

    @BeforeEach
    void setUp() {
        sampleSeries = Series.builder()
                .id(1L)
                .title("Attack on Titan")
                .genre("Action")
                .nbEpisodes(25)
                .note(9.5f)
                .build();
    }

    // ✅ searchSeriesPersonalised()

    @Test
    void testSearchSeriesPersonalised_WhenGenreIsNull_ShouldCallFindByGenre() {
        when(seriesRepository.findByGenre(null)).thenReturn(List.of(sampleSeries));

        List<Series> result = serieService.searchSeriesPersonalised(null, "SomeTitle", 5);

        assertEquals(1, result.size());
        verify(seriesRepository, times(1)).findByGenre(null);
        verify(seriesRepository, never()).findByTitleStartingWith(any());
    }

    @Test
    void testSearchSeriesPersonalised_WhenTitleIsNull_ShouldCallFindByTitleStartingWith() {
        when(seriesRepository.findByTitleStartingWith(null)).thenReturn(List.of(sampleSeries));

        List<Series> result = serieService.searchSeriesPersonalised("Action", null, 5);

        assertEquals(1, result.size());
        verify(seriesRepository, times(1)).findByTitleStartingWith(null);
    }

    @Test
    void testSearchSeriesPersonalised_WhenNbEpisodeNegative_ShouldCallFindByNbEpisodesGreaterThanEqual() {
        when(seriesRepository.findByNbEpisodesGreaterThanEqual(-1)).thenReturn(List.of(sampleSeries));

        List<Series> result = serieService.searchSeriesPersonalised("Action", "Attack", -1);

        assertEquals(1, result.size());
        verify(seriesRepository, times(1)).findByNbEpisodesGreaterThanEqual(-1);
    }


    @Test
    void testSearchSeriesPersonalised_WhenAllFieldsPresent_ShouldCallFindAll() {
        when(seriesRepository.findAll()).thenReturn(List.of(sampleSeries));

        List<Series> result = serieService.searchSeriesPersonalised("Action", "Attack", 10);

        assertEquals(1, result.size());
        verify(seriesRepository, times(1)).findAll();
    }

    // ✅ getAllSeries()

    @Test
    void testGetAllSeries_ReturnsAll() {
        when(seriesRepository.findAll()).thenReturn(List.of(sampleSeries));

        List<Series> result = serieService.getAllSeries();

        assertEquals(1, result.size());
        assertEquals("Attack on Titan", result.get(0).getTitle());
    }

    // ✅ getSerieById()

    @Test
    void testGetSerieById_Found() {
        when(seriesRepository.findById(1L)).thenReturn(Optional.of(sampleSeries));

        Series result = serieService.getSerieById(1L);

        assertEquals(sampleSeries, result);
    }

    @Test
    void testGetSerieById_NotFound_ShouldThrowException() {
        when(seriesRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> serieService.getSerieById(1L));
    }

    // ✅ addSeries()

    @Test
    void testAddSeries_SavesAndReturnsSeries() {
        SeriesRequestBody body = new SeriesRequestBody("Demon Slayer", "Action", 26, 9.0f);

        when(seriesRepository.save(any(Series.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Series result = serieService.addSeries(body);

        assertEquals("Demon Slayer", result.getTitle());
        assertEquals("Action", result.getGenre());
        assertEquals(26, result.getNbEpisodes());
        verify(seriesRepository, times(1)).save(any(Series.class));
    }

    // ✅ updateSeries()

    @Test
    void testUpdateSeries_UpdatesExistingFields() {
        when(seriesRepository.findById(1L)).thenReturn(Optional.of(sampleSeries));

        SeriesRequestBody body = new SeriesRequestBody("AOT Final", null, 30, null);
        Series updated = Series.builder()
                .id(1L)
                .title("AOT Final")
                .genre("Action")
                .nbEpisodes(30)
                .note(9.5f)
                .build();

        when(seriesRepository.save(any(Series.class))).thenReturn(updated);

        Series result = serieService.updateSeries(1L, body);

        assertEquals("AOT Final", result.getTitle());
        assertEquals(30, result.getNbEpisodes());
        assertEquals("Action", result.getGenre());
        verify(seriesRepository).save(any(Series.class));
    }

    @Test
    void testUpdateSeries_NotFound_ShouldThrowException() {
        when(seriesRepository.findById(1L)).thenReturn(Optional.empty());

        SeriesRequestBody body = new SeriesRequestBody("Title", "Genre", 10, 8.0f);

        assertThrows(EntityNotFoundException.class, () -> serieService.updateSeries(1L, body));
    }

    // ✅ deleteSeries()

    @Test
    void testDeleteSeries_CallsRepository() {
        serieService.deleteSeries(1L);

        verify(seriesRepository, times(1)).deleteById(1L);
    }
}
