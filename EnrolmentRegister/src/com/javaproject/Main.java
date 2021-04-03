package com.javaproject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    // Course is created here, a class was created to allow expansion later if more courses are added
    //Course course1 = new Course("Computer Science", "G400", "Casey Muratori");
    Student[] studentList = new Student[19];
    Course[] courseList = new Course[19];
    boolean exit;

    public static void main(String[] args) {
        // Create an instance of Main
        Main main = new Main();
        main.readCourseFile();
        main.readStudentFile();
        main.runMenu();
    }


    // Opens the StudentInfo.txt file and reads in already created students, create them as objects and then append
    // them a studentList array
    public void readStudentFile() {
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
                Student newStudent = new Student(asList[0], castDOB, asList[2], castGender, asList[4]);
                studentList[i] = newStudent;
                // i is used to append to the correct place in the array
                i++;
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            // Would like to make this create an empty studentInfo file and then allow the user to proceed
            System.out.println("StudentInfo file was not found");
        }
    }

    // Opens the StudentInfo.txt file and reads in already created students, create them as objects and then append
    // them a studentList array
    public void readCourseFile() {
        try {
            FileReader studentFile = new FileReader("CourseInfo.txt");
            Scanner fileReader = new Scanner(studentFile);
            int i = 0;
            while (fileReader.hasNextLine()) {
                String readInput = fileReader.nextLine();
                String[] asList = readInput.split(",");
                Course newCourse = new Course(asList[0], asList[1], asList[2]);
                courseList[i] = newCourse;
                // i is used to append to the correct place in the array
                i++;
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            // Would like to make this create an empty studentInfo file and then allow the user to proceed
            System.out.println("StudentInfo file was not found");
        }
    }


    public void runMenu() {
        printTitle();
        while (!exit) {
            printMenu();
            int choice = getInput();
            runFunction(choice);
        }
    }


    private void printTitle() {
        System.out.println("!_________________________________________!");
        System.out.println("!|                                       |!");
        System.out.println("!|               Welcome To              |!");
        System.out.println("!|          Enrolment Register!          |!");
        System.out.println("!|_______________________________________|!");
    }


    private void printMenu() {
        System.out.println("\nOptions");
        System.out.println("[1] Add Student Details");
        System.out.println("[2] Remove Student Details");
        System.out.println("[3] Student Search");
        System.out.println("[4] Course Report");
        System.out.println("[0] Exit");
    }


    private int getInput() {
        //create scanner
        Scanner kb = new Scanner(System.in);
        //set init choice to -1
        int choice = -1;
        //while choice is invalid request input from user
        while (choice < 0 || choice > 4) {
            System.out.println("\n(Enter A Number Between 0-4)");
            try {
                System.out.print("Enter Your Selection: ");
                choice = Integer.parseInt(kb.nextLine());
            } //exception if error when reading from kb
            catch (NumberFormatException e) {
                System.out.println("Invalid Menu Option - Try Again");
            }
        }
        return choice;
    }


    private void runFunction(int choice) {
        switch (choice) {
            case 0:
                exit = true;
                System.out.println("\nThank You For Using Enrolment Register!");
                break;
            case 1:
                System.out.println("\nAdd Student Details");

                //ADD FUNCTION HERE
                break;
            case 2:
                System.out.println("\nRemove Student Details");
                //REMOVE FUNCTION HERE
                break;
            case 3:
                System.out.println("\nStudent Search");
                //SEARCH FUNCTION HERE
                break;
            case 4:
                System.out.println("\nCourse Report");
                printReport();
                printStudents(studentList);
                break;
            default:
                System.out.println("You've broken the application somehow. Well done!");
        }
    }

    public void printReport() {
        System.out.println("Course Name: " + courseList[0].getCourseName());
        System.out.println("Course Code: " + courseList[0].getCourseCode());
        System.out.println("Lecturer: " + courseList[0].getLecturer());
        System.out.println("Total number of students: " + Student.numberOfObjects);
        maleFemaleSplit();
    }

    // Function that calculates the male-female split in the student list and provides a return as a percentage
    public void maleFemaleSplit() {
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

        System.out.println("Males: " + malePercentage + "%" + "  :  " + "Females: " + femalePercentage + "%");
    }


    // Print students formatted so that it is more easily readable
    public void printStudents(Student [] studentList) {

        // Find the longest name and address to use to find what gap is needed between attributes
        int longestName = 0;
        int longestAddress = 0;

        String[] heading = {"Name", "DOB", "Address", "Gender", "Course"};

        // Check each name/ address and if the selected name/ address is longer then replace longest
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
        String genderGap = "    ";

        // Find the difference between the currently selected value and the longest and add the difference to the string
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

        System.out.print(heading[3] + genderGap);
        System.out.print(heading[4]);
        System.out.println();

        // Print formatted student details underneath headings
        for (int j = 0; j < Student.numberOfObjects; j++) {
            nameGap = new StringBuilder("    ");
            dobGap = "    ";
            addressGap = new StringBuilder("    ");
            genderGap = "         ";


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
            System.out.print(studentList[j].getGender() + genderGap);
            System.out.print(studentList[j].getCourse());
            System.out.println();
        }
    }
}