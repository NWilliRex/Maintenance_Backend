package com.wam.lab1_maintenance.service;

import com.wam.lab1_maintenance.model.Series;
import com.wam.lab1_maintenance.repository.SeriesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class TrendingService {

    private final SeriesRepository seriesRepository;

    public List<Series> topTendance() {

        List<Series> listSeries = getSeries();
        Map<Series, Double> scoresMap = new HashMap<>();

        for (Series series : listSeries) {
            Float note = series.getNote();
            Integer vues = series.getNbEpisodes();

            if (note != null && vues != null && vues > 0) {
                double racineCarre = Math.sqrt(vues);
                double scoreTendance = racineCarre * note;
                scoresMap.put(series, scoreTendance);
            }
        }

        List<Series> listTop10 = new ArrayList<>(scoresMap.keySet());
        listTop10.sort((s1, s2) -> Double.compare(scoresMap.get(s2), scoresMap.get(s1)));

        if (listTop10.size() > 10) {
            listTop10 = listTop10.subList(0, 10);
        }

        return listTop10;
    }

    public List<Series> getSeries() {
        return seriesRepository.findAll();
    }
}
