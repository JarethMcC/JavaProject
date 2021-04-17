package com.javaproject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    // Course is created here, a class was created to allow expansion later if more courses are added
    //Course course1 = new Course("Computer Science", "G400", "Casey Muratori");
    Student[] studentList = new Student[20];
    Course[] courseList = new Course[20];
    //Maximum amount of students allowed within the course
    final int MAX = 20;
    //exit used to close the menu
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
                Student.numberOfObjects = Integer.parseInt(asList[3]);
                Course.numberOfMales = Integer.parseInt(asList[4]);
                Course.numberOfFemales = Integer.parseInt(asList[5]);
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


    // Writes to the respective text file at the end of the session
    public void writeStudentFile() {
        try {
            FileWriter fileWriter = new FileWriter("StudentInfo.txt");
            for (int i = 0; i < Student.numberOfObjects; i++) {
                // If there is no content left to write to the file then break
                if (studentList[i].getName().equals("") && i == Student.numberOfObjects) {
                    break;
                } else if (studentList[i].getName().equals("")) {
                    // If there is no content in this place in the array then move to the next
                    i++;
                }
                fileWriter.write(studentList[i].getName().strip() + "," + studentList[i].getDateOfBirth().toString().strip() + ","
                        + studentList[i].getAddress().strip() + "," + studentList[i].getGender().toString().strip() + ","
                        + studentList[i].getCourse().strip() + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error: File not found");
            e.printStackTrace();
        }
    }


    // Writes to the respective text file at the end of the session
    public void writeCourseFile() {
        try {
            FileWriter fileWriter = new FileWriter("CourseInfo.txt");
            for (int i = 0; i < Course.numberOfObjects; i++) {
                fileWriter.write(courseList[i].getCourseName().strip() + "," + courseList[i].getCourseCode().strip() + ","
                        + courseList[i].getLecturer().strip() + "," + Student.numberOfObjects + "," + Course.numberOfMales
                        + "," + Course.numberOfFemales + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error: File not found");
            e.printStackTrace();
        }
    }


    // Function to run the menu, here to call other necessary functions
    public void runMenu() {
        printTitle();
        while (!exit) {
            printMenu();
            int choice = getInput();
            runFunction(choice);
        }
    }


    // Print out the title card
    private void printTitle() {
        System.out.println("!_________________________________________!");
        System.out.println("!|                                       |!");
        System.out.println("!|               Welcome To              |!");
        System.out.println("!|          Enrolment Register!          |!");
        System.out.println("!|_______________________________________|!");
    }


    // Print out the options for the menu
    private void printMenu() {
        System.out.println("\nOptions");
        System.out.println("[1] Add Student Details");
        System.out.println("[2] Remove Student Details");
        System.out.println("[3] Student Search");
        System.out.println("[4] Course Report");
        System.out.println("[5] Print Students");
        System.out.println("[0] Exit");
    }


    // Get input from the user to check their menu selection
    private int getInput() {
        //create scanner
        Scanner kb = new Scanner(System.in);
        //set init choice to -1
        int choice = -1;
        //while choice is invalid request input from user
        while (choice < 0 || choice > 5) {
            System.out.println("\n(Enter A Number Between 0-5)");
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


    // Switch to execute the correct functions based on the user input
    private void runFunction(int choice) {
        switch (choice) {
            case 0:
                exit = true;
                writeStudentFile();
                writeCourseFile();
                System.out.println("\nThank You For Using Enrolment Register!");
                break;
            case 1:
                System.out.println("\nAdd Student Details");
                addStudent();
                break;
            case 2:
                System.out.println("\nRemove Student Details");
                removeStudent();
                break;
            case 3:
                System.out.println("\nStudent Search");
                searchStudent();
                break;
            case 4:
                System.out.println("\nCourse Report");
                printReport();
                break;
            case 5:
                System.out.println("\nPrint Students");
                printStudents(studentList);
                break;
            default:
                System.out.println("You've broken the application somehow. Well done!");
        }
    }


    // Function to allow the user to add a new student
    public void addStudent() {
        // Formats the input date in the correct way
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (Student.numberOfObjects < MAX) {
            Scanner userInput = new Scanner(System.in);
            System.out.println("Enter Student Name:");
            String name = userInput.nextLine();
            System.out.println("Enter Student Date Of Birth (Format DD/MM/YYYY):");
            LocalDate dateOfBirth;

            // Checks to see if a user entered the date in the correct format, if not then the date is defaulted
            try {
                String dateOfBirthRaw = userInput.nextLine();
                dateOfBirth = LocalDate.parse(dateOfBirthRaw, dateTimeFormatter);
            } catch (Exception e) {
                System.out.println("You did not enter a valid date of birth, the date of birth has been defaulted to 01/01/2000\n");
                String dateOfBirthRaw = "01/01/2000";
                dateOfBirth = LocalDate.parse(dateOfBirthRaw, dateTimeFormatter);
            }
            System.out.println("Enter Student Address:");
            String address = userInput.nextLine();
            System.out.println("Enter Student Gender:");
            char gender;

            // Checks to see if a user entered a gender value, if not then it is defaulted
            try {
                String tempGender = userInput.nextLine();
                gender = tempGender.charAt(0);
            } catch (Exception e) {
                System.out.println("You did not enter a valid gender, the gender has been defaulted to M\n");
                String tempGender = "M";
                gender = tempGender.charAt(0);
            }
            System.out.println("Enter Student Course:");
            String course = userInput.nextLine();

            // If the user failed to enter one of the values then they are notified that the record will not be saved
            if (name.equals("") || address.equals("") || course.equals("")) {
                System.out.println("As one or more values have been left blank, this student record will not be saved at the end of the session");
            }
            System.out.println();
            Student newStudent = new Student(name, dateOfBirth, address, gender, course);
            // numberOfObjects decremented by 1 as it does not start at 0 value
            studentList[Student.numberOfObjects - 1] = newStudent;
            if (newStudent.getGender() == 'M') {
                Course.numberOfMales++;
            } else {
                Course.numberOfFemales++;
            }
            Student.numberOfObjects++;
        } else {
            System.out.println("Student Limit Reached!");
        }
    }


    // Allows the user to remove a student by searching for them by name
    public void removeStudent() {
        // If there are no students to remove then the user will be notified
        if (Student.numberOfObjects == 0) {
            System.out.println("Students must be added before you can remove a student");
        } else {
            boolean loop = false;
            while (!loop) {
                Scanner input = new Scanner(System.in);
                System.out.println("Please enter the name of the student that you would like to delete: ");
                String studentName = input.nextLine().toLowerCase();
                // If a student is found it is added to a new array to be printed later
                Student[] foundStudent = new Student[1];
                int numberFoundStudents = 0;
                for (int i = 0; i < Student.numberOfObjects; i++) {
                    // Checks the user input to see if it is contained within any of the names, if left blank then it
                    // will return all students
                    if (studentList[i].getName().toLowerCase().contains(studentName)) {
                        foundStudent[0] = studentList[i];
                        printStudents(foundStudent);
                        System.out.println("Is this the student that you wanted to delete? (Y for yes, anything else for no)\n");
                        String yOrN = input.nextLine().toLowerCase();
                        numberFoundStudents++;
                        if (yOrN.equals("y")) {
                            if (studentList[i].getGender() == 'M') {
                                Course.numberOfMales--;
                            } else {
                                Course.numberOfFemales--;
                            }
                            studentList[i] = null;
                            Student.numberOfObjects--;
                        }
                    } else if (i == Student.numberOfObjects - 1 && numberFoundStudents == 0) {
                        System.out.println("No students were found with that name\n");
                    }
                }
                Student[] tempStudentList = new Student[19];
                int numberOfStudents = 0;
                // Reorganises the studentList so that there are no empty gaps
                for (int n = 0; n < Student.numberOfObjects + 1; n++) {
                    if (studentList[n] != null) {
                        tempStudentList[numberOfStudents] = studentList[n];
                        numberOfStudents++;
                    }
                }
                studentList = tempStudentList;
                // Checks the user at the end if they would like to delete another student rather than having to select
                // the option from the menu again
                System.out.println("Would you like to remove another student? (Y for yes, anything else for no)");
                String yOrN = input.nextLine().toLowerCase();
                if (yOrN.equals("y")) {
                    loop = false;
                } else {
                    loop = true;
                }
            }
        }
    }


    // Allows the user to search for a student/ students by name
    public void searchStudent() {
        // If the are no students then the user is notified that some have to be added first
        if (Student.numberOfObjects == 0) {
            System.out.println("Students must be added before you can search for a student");
        } else {
            Scanner input = new Scanner(System.in);
            System.out.println("Please enter the name of the student that you would like to search: ");
            String studentName = input.nextLine().toLowerCase();
            Student[] foundStudents = new Student[20];
            int numberFoundStudents = 0;
            // Students that are found are added to a new array to print later
            for (int i = 0; i < Student.numberOfObjects; i++) {
                if (studentList[i].getName().toLowerCase().contains(studentName)) {
                    foundStudents[numberFoundStudents] = studentList[i];
                    numberFoundStudents++;
                } else if (i == Student.numberOfObjects - 1 && numberFoundStudents == 0) {
                    System.out.println("No students were found with that name");
                }
            }
            // If there are students found then they are passed to the printStudents function to print
            if (foundStudents[0] != null) {
                printStudents(foundStudents);
            }
        }
    }


    // Prints a report of important details of the course
    public void printReport() {
        // If there are no students then the user is informed that students must first be added
        if (Student.numberOfObjects == 0) {
            System.out.println("Students must be added before a report can be printed");
        } else {
            System.out.println("Course Name: " + courseList[0].getCourseName());
            System.out.println("Course Code: " + courseList[0].getCourseCode());
            System.out.println("Lecturer: " + courseList[0].getLecturer());
            System.out.println("Total number of students: " + Student.numberOfObjects);
            maleFemaleSplit();
        }
    }


    // Gets the number of males and females from the course class, calculates them as a percentage and prints them
    // for the report
    public void maleFemaleSplit() {
        int malePercentage = (100 * Course.numberOfMales / Student.numberOfObjects);
        int femalePercentage = (100 * Course.numberOfFemales / Student.numberOfObjects);

        System.out.println("Males: " + malePercentage + "%" + "  :  " + "Females: " + femalePercentage + "%");
    }


    // Formats the date of birth that is stored as LocalDate so that it prints in day/month/year format and returns it to be printed
    public String dateFormatter(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }


    // Print students formatted so that it is more easily readable
    public void printStudents(Student[] studentsToPrint) {
        if (Student.numberOfObjects == 0) {
            System.out.println("Students must be added before you can print any students");
        } else {
            // Find the longest name and address to use to find what gap is needed between attributes
            int longestName = 0;
            int longestAddress = 0;
            int numberOfStudents = 0;
            for (int i = 0; i < studentsToPrint.length; i++) {
                if (studentsToPrint[i] != null) {
                    numberOfStudents++;
                }
            }
            String[] heading = {"Name", "DOB", "Address", "Gender", "Course"};

            // Check each name/ address and if the selected name/ address is longer then replace longest
            for (int i = 0; i < numberOfStudents; i++) {
                int length = studentsToPrint[i].getName().length();
                if (length > longestName) {
                    longestName = length;
                }
                length = studentsToPrint[i].getAddress().length();
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
            for (int j = 0; j < numberOfStudents; j++) {
                nameGap = new StringBuilder("    ");
                dobGap = "    ";
                addressGap = new StringBuilder("    ");
                genderGap = "         ";
                difference = longestName - studentsToPrint[j].getName().length();

                // Loop is used to create the gap as a string to insert between attributes
                for (int e = 0; e <= difference; e++) {
                    nameGap.append(" ");
                }
                System.out.print(studentsToPrint[j].getName() + nameGap);
                System.out.print(dateFormatter(studentsToPrint[j].getDateOfBirth()) + dobGap);
                difference = longestAddress - studentsToPrint[j].getAddress().length();

                for (int e = 0; e <= difference; e++) {
                    addressGap.append(" ");
                }
                System.out.print(studentsToPrint[j].getAddress() + addressGap);
                System.out.print(studentsToPrint[j].getGender() + genderGap);
                System.out.print(studentsToPrint[j].getCourse());
                System.out.println();
            }
        }
    }
}