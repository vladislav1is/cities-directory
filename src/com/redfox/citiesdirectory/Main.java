package com.redfox.citiesdirectory;

import com.redfox.citiesdirectory.model.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String fileName = "Задача ВС Java Сбер.csv";
        String dataDirectory = getDataDirectory();
        printExistedData(dataDirectory, fileName);
    }

    private static String getDataDirectory() {
        return Paths.get("data")
                .toAbsolutePath()
                .toString();
    }

    private static void printExistedData(String dataDirectory, String fileName) {
        try {
            printData(dataDirectory,fileName);
        } catch (FileNotFoundException exception) {
            System.out.println("File '" + fileName + "' not found in '" + dataDirectory +"'");
            exception.printStackTrace();
        }
    }

    private static void printData(String dataDirectory, String fileName) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(dataDirectory, fileName))) {
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                String[] data = row.split(";");
                City city = new City(data[1], data[2], data[3], Integer.parseInt(data[4]), data.length == 6 ? data[5] : "");
                System.out.println(city);
            }
        }
    }
}