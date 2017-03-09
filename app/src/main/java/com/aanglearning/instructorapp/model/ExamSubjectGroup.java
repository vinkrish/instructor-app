package com.aanglearning.instructorapp.model;

public class ExamSubjectGroup {
    private long id;
    private long examId;
    private long subjectGroupId;
    private String subjectGroupName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public long getSubjectGroupId() {
        return subjectGroupId;
    }

    public void setSubjectGroupId(long subjectGroupId) {
        this.subjectGroupId = subjectGroupId;
    }

    public String getSubjectGroupName() {
        return subjectGroupName;
    }

    public void setSubjectGroupName(String subjectGroupName) {
        this.subjectGroupName = subjectGroupName;
    }

}
