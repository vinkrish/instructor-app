package com.aanglearning.instructorapp.model;

public class SliptestScore {
    private long id;
    private long sliptestId;
    private long studentId;
    private float mark;
    private String grade;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSliptestId() {
        return sliptestId;
    }

    public void setSliptestId(long sliptestId) {
        this.sliptestId = sliptestId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

}
