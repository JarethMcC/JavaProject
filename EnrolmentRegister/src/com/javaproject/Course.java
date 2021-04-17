package com.javaproject;

public class Course {

    private String courseName;
    private String courseCode;
    private String lecturer;

    // Use this to count total number of courses for use in for loops
    public static int numberOfObjects = 0;
    public static int numberOfMales = 0;
    public static int numberOfFemales = 0;

    public Course(String courseName, String courseCode, String lecturer) {
        this.setCourseName(courseName);
        this.setCourseCode(courseCode);
        this.setLecturer(lecturer);
        numberOfObjects++;
    }

    // Start of setters
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }
    //End of setters

    // Start of getters
    public String getCourseName() {
        return courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getLecturer() {
        return lecturer;
    }
    //End of getters
}
