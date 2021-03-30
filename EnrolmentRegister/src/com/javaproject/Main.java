package com.javaproject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    Student student = new Student();
    Student[] studentList = new Student[19];


    public static void main(String[] args) {
        // Create an instance of Main
        Main main = new Main();

        main.studentList = main.readFileAsObjects();
        System.out.println(main.studentList[3].getName());

        String maleFemaleCount = main.maleFemaleSplit();
        System.out.println(maleFemaleCount);
    }

    // Opens the StudentInfo.txt file and reads in already created students, create them as objects and then append
    // them a studentList array
    public Student [] readFileAsObjects() {
        try {
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

    public void printStudents() {

    }

    public String maleFemaleSplit() {
        int numberOfObjects = student.numberOfObjects;
        int maleCounter = 0;
        int femaleCounter = 0;
        System.out.println(numberOfObjects);
        for (int i = 0; i <= (numberOfObjects - 1); i++) {
            if (studentList[i].getGender().toString().equals("M")) {
                maleCounter++;
            } else {
                femaleCounter++;
            }
        }
        int malePercentage = (100 * maleCounter / numberOfObjects);
        int femalePercentage = (100 * femaleCounter / numberOfObjects);

        return("Males: " + malePercentage + "%" +  "\nFemales: " + femalePercentage + "%");
    }


}
