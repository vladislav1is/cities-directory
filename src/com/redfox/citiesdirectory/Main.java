package com.redfox.citiesdirectory;

import com.redfox.citiesdirectory.model.City;

import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.redfox.citiesdirectory.util.CityUtils.*;

public class Main {

    public static void main(String[] args) {
        String fileName = "Задача ВС Java Сбер.csv";
        String directoryName = "data";

        String dataPath = Paths.get(directoryName).toAbsolutePath().toString();
        List<City> cities = checkParse(dataPath, fileName);
        print(cities);

        Comparator<City> comparatorByName = (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName());
        Comparator<City> comparatorByDistrictAndName = Comparator.comparing(City::getDistrict).thenComparing(City::getName);
        List<City> sortedCities = sort(cities, comparatorByDistrictAndName);
        print(sortedCities);

        City[] citiesArray = cities.toArray(City[]::new);
        int maxPopulationIndex = findMaxPopulationIndex(citiesArray);
        System.out.println(MessageFormat.format("[{0}] = {1}", maxPopulationIndex, citiesArray[maxPopulationIndex].getPopulation()));

        Map<String, Integer> regions = findCountCityByRegionV1(cities);
        regions.forEach((k, v) -> System.out.println(MessageFormat.format("{0} - {1}", k, v)));
    }
}