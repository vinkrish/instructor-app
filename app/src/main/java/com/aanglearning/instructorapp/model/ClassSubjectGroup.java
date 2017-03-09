package com.aanglearning.instructorapp.model;

public class ClassSubjectGroup {
    private long id;
    private long classId;
    private long subjectGroupId;
    private String subjectGroupName;

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
