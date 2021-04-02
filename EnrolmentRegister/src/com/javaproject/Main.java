package com.javaproject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    Student[] studentList = new Student[20];


    public static void main(String[] args) {
        // Create an instance of Main
        Main main = new Main();

        main.studentList = main.readFileAsObjects();
        System.out.println(main.studentList[3].getName());

        String maleFemaleCount = main.maleFemaleSplit();
        System.out.println(maleFemaleCount);

        main.printStudents(main.studentList);
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

    // Print students formatted so that it is more easily readable
    public void printStudents(Student [] studentList) {

        // Find the longest name and address to use to find what gap is needed between attributes
        int longestName = 0;
        int longestAddress = 0;

        String[] heading = {"Name", "DOB", "Address", "Gender"};

        for (int i = 0; i < Student.numberOfObjects; i++) {
            int length = studentList[i].getName().length();
            if (length > longestName) {
                longestName = length;
            }
            length = studentList[i].getAddress().length();
            if (length > longestAddress) {
                longestAddress = length;
            }
        }
        // If the name of the heading is longer than any of the entries then use the heading as the longest value
        if (heading[0].length() > longestName) {
            longestName = heading[0].length();
        }
        if (heading[3].length() > longestName) {
            longestName = heading[3].length();
        }

        // Print headings for student details with correct gap
        StringBuilder nameGap = new StringBuilder("    ");
        String dobGap = "           ";
        StringBuilder addressGap = new StringBuilder("    ");

        // Find the difference between the currently selected value and the longest and fill in the difference
        int difference = longestName - heading[0].length();
        for (int e = 0; e <= difference; e++) {
            nameGap.append(" ");
        }
        System.out.print(heading[0] + nameGap);

        System.out.print(heading[1] + dobGap);

        difference = longestAddress - heading[2].length();
        for (int e = 0; e <= difference; e++) {
            addressGap.append(" ");
        }
        System.out.print(heading[2] + addressGap);

        System.out.print(heading[3]);
        System.out.println();

        // Print formatted student details underneath headings
        for (int j = 0; j < Student.numberOfObjects; j++) {
            nameGap = new StringBuilder("    ");
            dobGap = "    ";
            addressGap = new StringBuilder("    ");

            difference = longestName - studentList[j].getName().length();
            // Loop is used to create the gap as a string to insert between attributes
            for (int e = 0; e <= difference; e++) {
                nameGap.append(" ");
            }
            System.out.print(studentList[j].getName() + nameGap);

            System.out.print (studentList[j].getDateOfBirth() + dobGap);

            difference = longestAddress - studentList[j].getAddress().length();
            for (int e = 0; e <= difference; e++) {
                addressGap.append(" ");
            }
            System.out.print(studentList[j].getAddress() + addressGap);

            System.out.print(studentList[j].getGender());

            System.out.println();
        }
    }

    // Function that calculates the male-female split in the student list and provides a return as a percentage
    public String maleFemaleSplit() {
        int maleCounter = 0;
        int femaleCounter = 0;
        // If the result equals M then increase maleCounter by 1, otherwise increase femaleCounter
        for (int i = 0; i < (Student.numberOfObjects); i++) {
            if (studentList[i].getGender().toString().equals("M")) {
                maleCounter++;
            } else {
                femaleCounter++;
            }
        }
        // Calculate the numbers as a percentage
        int malePercentage = (100 * maleCounter / Student.numberOfObjects);
        int femalePercentage = (100 * femaleCounter / Student.numberOfObjects);

        return("Males: " + malePercentage + "%" +  "\nFemales: " + femalePercentage + "%");
    }


}
