package com.aanglearning.instructorapp.model;

public class GradeClassWise {
    private long id;
    private long classId;
    private String grade;
    private int markFrom;
    private int markTo;
    private int gradePoint;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getMarkFrom() {
        return markFrom;
    }

    public void setMarkFrom(int markFrom) {
        this.markFrom = markFrom;
    }

    public int getMarkTo() {
        return markTo;
    }

    public void setMarkTo(int markTo) {
        this.markTo = markTo;
    }

    public int getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(int gradePoint) {
        this.gradePoint = gradePoint;
    }

}
