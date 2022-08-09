package com.redfox.citiesdirectory.util;

import com.redfox.citiesdirectory.model.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class CityUtils {

    public static List<City> checkParse(String dataPath, String fileName) {
        try {
            return parse(dataPath, fileName);
        } catch (FileNotFoundException exception) {
            System.out.println("File '" + fileName + "' not found in '" + dataPath + "'");
            return Collections.emptyList();
        }
    }

    private static List<City> parse(String dataPath, String fileName) throws FileNotFoundException {
        List<City> cities = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(dataPath, fileName))) {
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                City city = parse(row);
                cities.add(city);
            }
        }
        return cities;
    }

    private static City parse(String row) {
        City city = new City();
        try (Scanner scanner = new Scanner(row)) {
            scanner.useDelimiter(";");
            scanner.skip("\\d*");
            city.setName(scanner.next());
            city.setRegion(scanner.next());
            city.setDistrict(scanner.next());
            city.setPopulation(scanner.nextInt());
            city.setFoundation(scanner.hasNext() ? scanner.next() : null);
        }
        return city;
    }

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

    public static Map<String, Integer> findCountCityByRegionV1(List<City> cities) {
        Map<String, Integer> regions = new HashMap<>();
        for (City city : cities) {
            if (!regions.containsKey(city.getRegion())) {
                regions.put(city.getRegion(), 1);
            } else {
                regions.put(city.getRegion(), regions.get(city.getRegion()) + 1);
            }
        }
        return regions;
    }

    public static Map<String, Integer> findCountCityByRegionV2(List<City> cities) {
        Map<String, Integer> regions = new HashMap<>();
        cities.forEach(city -> regions.merge(city.getRegion(), 1, Integer::sum));
        return regions;
    }

    public static void print(List<City> cities) {
        cities.forEach(System.out::println);
    }
}