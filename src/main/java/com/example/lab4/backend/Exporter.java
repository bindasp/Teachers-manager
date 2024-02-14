package com.example.lab4.backend;

import com.example.lab4.models.ClassTeacher;
import com.example.lab4.models.Rate;
import com.example.lab4.models.Teacher;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.hibernate.Session;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

public class Exporter {
    public static void exportToCsv(String tableName, String csvFileName) {
        if (tableName == "groups") {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/" + csvFileName))) {
                StatefulBeanToCsv<ClassTeacher> statefulBeanToCsv = new StatefulBeanToCsvBuilder<ClassTeacher>(bufferedWriter)
                        .withSeparator(CSVWriter.DEFAULT_SEPARATOR).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();

                statefulBeanToCsv.write(DbManager.getInstance().getAllClassTeachers());

                System.out.println("Dane zostały wyeksportowane do pliku CSV.");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CsvRequiredFieldEmptyException e) {
                throw new RuntimeException(e);
            } catch (CsvDataTypeMismatchException e) {
                throw new RuntimeException(e);
            }
        } else if (tableName == "teachers") {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/" + csvFileName))) {
                StatefulBeanToCsv<Teacher> statefulBeanToCsv = new StatefulBeanToCsvBuilder<Teacher>(bufferedWriter)
                        .withSeparator(CSVWriter.DEFAULT_SEPARATOR).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();

                statefulBeanToCsv.write(DbManager.getInstance().getAllTeachers());

                System.out.println("Dane zostały wyeksportowane do pliku CSV.");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CsvRequiredFieldEmptyException e) {
                throw new RuntimeException(e);
            } catch (CsvDataTypeMismatchException e) {
                throw new RuntimeException(e);
            }

        }
        else if(tableName == "rate"){
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/" + csvFileName))) {
                StatefulBeanToCsv<Rate> statefulBeanToCsv = new StatefulBeanToCsvBuilder<Rate>(bufferedWriter)
                        .withSeparator(CSVWriter.DEFAULT_SEPARATOR).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();

                statefulBeanToCsv.write(DbManager.getInstance().getAllRates());

                System.out.println("Dane zostały wyeksportowane do pliku CSV.");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CsvRequiredFieldEmptyException e) {
                throw new RuntimeException(e);
            } catch (CsvDataTypeMismatchException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
