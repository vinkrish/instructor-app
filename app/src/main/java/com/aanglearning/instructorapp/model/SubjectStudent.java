package com.aanglearning.instructorapp.model;

public class SubjectStudent {
    private long id;
    private long sectionId;
    private long subjectId;
    private String studentIds;

    public long getId() {
        return id;
    }

    public void setId(long subjectStudentId) {
        this.id = subjectStudentId;
    }

    public long getSectionId() {
        return sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public String getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(String studentIds) {
        this.studentIds = studentIds;
    }

}
