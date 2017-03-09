package com.aanglearning.instructorapp.model;

public class SubjectGroupSubject {
    private long id;
    private long subjectGroupId;
    private long subjectId;
    private String subjectName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSubjectGroupId() {
        return subjectGroupId;
    }

    public void setSubjectGroupId(long subjectGroupId) {
        this.subjectGroupId = subjectGroupId;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
