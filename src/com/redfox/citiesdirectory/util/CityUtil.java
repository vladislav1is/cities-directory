package com.redfox.citiesdirectory.util;

import com.redfox.citiesdirectory.model.City;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class CityUtil {

    public static List<City> sort(List<City> cities, Comparator<City> comparator) {
        return cities.stream()
                .sorted(comparator.reversed())
                .collect(toList());
    }

    public static int findMaxPopulationIndex(City[] cities) {
        int maxPopulationIndex = 0;
        for (int i = 1; i < cities.length; i++) {
            if (cities[i].getPopulation() > cities[maxPopulationIndex].getPopulation()) {
                maxPopulationIndex = i;
            }
        }
        return maxPopulationIndex;
    }

    public static Map<String, Integer> countCitiesByRegions(List<City> cities) {
        Map<String, Integer> map = new HashMap<>();
        for (City city : cities) {
            String region = city.getRegion();
            Integer cityCounter = map.get(region);
            if (cityCounter == null) {
                map.put(region, 1);
            } else {
                cityCounter++;
                map.put(region, cityCounter);
            }
        }
        return map;
    }
}