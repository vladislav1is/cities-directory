package com.redfox.citiesdirectory;

import com.redfox.citiesdirectory.model.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) {
        String fileName = "Задача ВС Java Сбер.csv";
        String directoryName = "data";
        String dataPath = getDataPath(directoryName);
        List<City> cities = loadExistedData(dataPath, fileName);

        Comparator<City> comparatorByName = Comparator.comparing(City::getName, String::compareToIgnoreCase);
        Comparator<City> comparatorByDistrictAndName = Comparator.comparing(City::getDistrict).thenComparing(City::getName);
        List<City> sortedCities = sort(cities, comparatorByDistrictAndName);
        sortedCities.forEach(System.out::println);

        City[] citiesArray = cities.toArray(City[]::new);
        int maxPopulationIndex = maxPopulation(citiesArray);
        System.out.format("[%s] = %s", maxPopulationIndex, citiesArray[maxPopulationIndex].getPopulation());
    }

    private static String getDataPath(String directoryName) {
        return Paths.get(directoryName)
                .toAbsolutePath()
                .toString();
    }

    private static List<City> loadExistedData(String dataPath, String fileName) {
        try {
            return readData(dataPath, fileName);
        } catch (FileNotFoundException exception) {
            System.out.println("File '" + fileName + "' not found in '" + dataPath + "'");
            return Collections.emptyList();
        }
    }

    private static List<City> readData(String dataPath, String fileName) throws FileNotFoundException {
        List<City> cities = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(dataPath, fileName))) {
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                String[] data = row.split(";");
                City city = new City(data[1], data[2], data[3], Integer.parseInt(data[4]), data.length == 6 ? data[5] : "");
                cities.add(city);
            }
        }
        return cities;
    }

    private static List<City> sort(List<City> cities, Comparator<City> comparator) {
        return cities.stream()
                .sorted(comparator.reversed())
                .collect(toList());
    }

    private static int maxPopulation(City[] cities) {
        int maxPopulationIndex = 0;
        for (int i = 1; i < cities.length; i++) {
            if (cities[i].getPopulation() > cities[maxPopulationIndex].getPopulation()) {
                maxPopulationIndex = i;
            }
        }
        return maxPopulationIndex;
    }
}