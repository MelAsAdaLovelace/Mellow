package com.mellow.mellow.Helpers;

public class CourseHelper {

    String title, semester, instructor, time, classroom, grade;

    public CourseHelper(String title, String semester, String instructor, String time, String classroom, String grade) {
        this.title = title;
        this.semester = semester;
        this.instructor = instructor;
        this.time = time;
        this.classroom = classroom;
        this.grade = grade;
    }


    public String getTitle() {
        return title;
    }

    public String getSemester() {
        return semester;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getTime() {
        return time;
    }

    public String getClassroom() {
        return classroom;
    }

    public String getGrade() {
        return grade;
    }
}
