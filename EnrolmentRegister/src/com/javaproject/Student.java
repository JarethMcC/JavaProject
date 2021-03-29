package com.javaproject;

import java.time.LocalDate;

public class Student {

    private String name;
    // LocalDate takes the format Year, Month, Day, will have to change this later
    private LocalDate dateOfBirth;
    private String address;
    private Character gender;

    // Use this to count total number of students as a maximum of 20 is set
    Integer numberOfObjects = 0;

    // A constructor for the class
    public Student(String name, LocalDate dateOfBirth, String address, Character gender) {
        this.setName(name);
        this.setDateOfBirth(dateOfBirth);
        this.setAddress(address);
        this.setGender(gender);
        numberOfObjects++;

    }

    public Student() {

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
        this.gender = gender;
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
    // End of getters
}