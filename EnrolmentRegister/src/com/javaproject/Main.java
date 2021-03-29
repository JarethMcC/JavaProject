package com.javaproject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();

        Student[] studentList;
        studentList = main.readFileAsObjects();

        System.out.println(studentList[1].getName());
    }

    // Opens the StudentInfo.txt file and reads in already created students, create them as objects and then append
    // them a studentList array
    public Student [] readFileAsObjects() {
        try {
            Student[] studentList = new Student[20];
            FileReader studentFile = new FileReader("StudentInfo.txt");
            Scanner fileReader = new Scanner(studentFile);
            int i = 0;
            while (fileReader.hasNextLine()) {
                String readInput = fileReader.nextLine();
                String[] asList = readInput.split(",");
                // Next two lines are to cast to other types to allow them to be used in the constructor
                LocalDate castDOB = LocalDate.parse(asList[1]);
                char castGender = asList[3].charAt(0);
                Student newStudent = new Student(asList[0], castDOB, asList[2], castGender);
                studentList[i] = newStudent;
                // i is used to append to the correct place in the array
                i++;
            }
            fileReader.close();
            return studentList;
        } catch (FileNotFoundException e) {
            // Would like to make this create an empty studentInfo file and then allow the user to proceed
            System.out.println("StudentInfo file was not found");
        }
        return null;
    }


}
