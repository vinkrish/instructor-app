package com.aanglearning.instructorapp.model;

public class SubjectGroup {
    private long id;
    private long SchoolId;
    private String subjectGroupName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(long schoolId) {
        SchoolId = schoolId;
    }

    public String getSubjectGroupName() {
        return subjectGroupName;
    }

    public void setSubjectGroupName(String subjectGroupName) {
        this.subjectGroupName = subjectGroupName;
    }

}
