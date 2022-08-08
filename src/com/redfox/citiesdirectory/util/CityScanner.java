package com.redfox.citiesdirectory.util;

import com.redfox.citiesdirectory.model.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CityScanner {

    public static List<City> loadExistedData(String dataPath, String fileName) {
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
                City city = getCityFromRow(row);
                cities.add(city);
            }
        }
        return cities;
    }

    private static City getCityFromRow(String row) {
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
}