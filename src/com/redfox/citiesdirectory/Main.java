package com.redfox.citiesdirectory;

import com.redfox.citiesdirectory.model.City;
import com.redfox.citiesdirectory.util.CityScanner;
import com.redfox.citiesdirectory.util.CityUtil;

import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String fileName = "Задача ВС Java Сбер.csv";
        String directoryName = "data";

        String dataPath = getDataPath(directoryName);
        List<City> cities = CityScanner.loadExistedData(dataPath, fileName);

        Comparator<City> comparatorByName = Comparator.comparing(City::getName, String::compareToIgnoreCase);
        Comparator<City> comparatorByDistrictAndName = Comparator.comparing(City::getDistrict).thenComparing(City::getName);
        printSortedCities(cities, comparatorByDistrictAndName);

        printMaxPopulation(cities);
        printNumberOfCitiesByRegions(cities);
    }

    private static String getDataPath(String directoryName) {
        return Paths.get(directoryName)
                .toAbsolutePath()
                .toString();
    }

    private static void printSortedCities(List<City> cities, Comparator<City> comparatorByDistrictAndName) {
        List<City> sortedCities = CityUtil.sort(cities, comparatorByDistrictAndName);
        sortedCities.forEach(System.out::println);
    }

    private static void printMaxPopulation(List<City> cities) {
        City[] citiesArray = cities.toArray(City[]::new);
        int maxPopulationIndex = CityUtil.findMaxPopulationIndex(citiesArray);
        System.out.format("\n[%s] = %s\n\n", maxPopulationIndex, citiesArray[maxPopulationIndex].getPopulation());
    }

    private static void printNumberOfCitiesByRegions(List<City> cities) {
        Map<String, Integer> numberOfCitiesByRegions = CityUtil.countCitiesByRegions(cities);
        numberOfCitiesByRegions.forEach((region, citiesNumber) -> System.out.format("%s - %s\n", region, citiesNumber));
    }
}