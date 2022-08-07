package com.redfox.citiesdirectory;

import com.redfox.citiesdirectory.model.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) {
        String fileName = "Задача ВС Java Сбер.csv";
        String directoryName = "data";
        String dataPath = getDataPath(directoryName);

        Comparator<City> comparatorByName = Comparator.comparing(City::getName, String::compareToIgnoreCase);
        Comparator<City> comparatorByDistrictAndName = Comparator.comparing(City::getDistrict).thenComparing(City::getName);

        printExistedData(dataPath, fileName, comparatorByDistrictAndName);
    }

    private static String getDataPath(String directoryName) {
        return Paths.get(directoryName)
                .toAbsolutePath()
                .toString();
    }

    private static void printExistedData(String dataPath, String fileName, Comparator<City> comparator) {
        try {
            List<City> cities = sortData(dataPath, fileName, comparator);
            cities.forEach(System.out::println);
        } catch (FileNotFoundException exception) {
            System.out.println("File '" + fileName + "' not found in '" + dataPath + "'");
        }
    }

    private static List<City> sortData(String dataPath, String fileName, Comparator<City> comparator) throws FileNotFoundException {
        return readData(dataPath, fileName).stream()
                .sorted(comparator.reversed())
                .collect(toList());
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
}