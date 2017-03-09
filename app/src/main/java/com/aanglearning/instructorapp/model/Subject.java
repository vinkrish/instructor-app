package com.aanglearning.instructorapp.model;

public class Subject {
    private long id;
    private long schoolId;
    private String subjectName;
    private int partitionType;
    private long theorySubjectId;
    private long practicalSubjectId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getPartitionType() {
        return partitionType;
    }

    public void setPartitionType(int partitionType) {
        this.partitionType = partitionType;
    }

    public long getTheorySubjectId() {
        return theorySubjectId;
    }

    public void setTheorySubjectId(long theorySubjectId) {
        this.theorySubjectId = theorySubjectId;
    }

    public long getPracticalSubjectId() {
        return practicalSubjectId;
    }

    public void setPracticalSubjectId(long practicalSubjectId) {
        this.practicalSubjectId = practicalSubjectId;
    }

}
