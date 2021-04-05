package com.javaproject;

import java.time.LocalDate;
import java.util.Scanner;

public class Student {

    private String name;
    // LocalDate takes the format Year, Month, Day, will have to change this later
    private LocalDate dateOfBirth;
    private String address;
    private Character gender;
    private String course;

    // Use this to count total number of students as a maximum of 20 is set
    public static int numberOfObjects = 0;

    // Create constructor for the class
    public Student(String name, LocalDate dateOfBirth, String address, Character gender, String course) {
        this.setName(name);
        this.setDateOfBirth(dateOfBirth);
        this.setAddress(address);
        this.setGender(gender);
        this.setCourse(course);
        numberOfObjects++;
    }

    // Start of setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {

        this.dateOfBirth = dateOfBirth;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(Character gender) {
        Scanner userInput = new Scanner(System.in);
        boolean loop = false;
        while (!loop) {
            if (gender == 'M' || gender == 'F') {
                this.gender = gender;
                loop = true;
            } else {
                System.out.println("Gender was not entered in required format of \"M\" or \"F\"");
                System.out.print("Please enter a new gender: ");
                gender = userInput.next().charAt(0);
                loop = false;
            }
        }
    }

    public void setCourse(String course) {
        this.course = course;
    }
    // End of setters

    // Start of getters
    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public Character getGender() {
        return gender;
    }

    public String getCourse() {
        return course;
    }
    // End of getters
}