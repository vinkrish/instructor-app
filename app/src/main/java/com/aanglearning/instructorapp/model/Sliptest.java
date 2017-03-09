package com.aanglearning.instructorapp.model;

public class Sliptest {
    private long id;
    private long sectionId;
    private long subjectId;
    private String sliptestName;
    private String portionIds;
    private String extraPortion;
    private float maximumMark;
    private float average;
    private String testDate;
    private String submissionDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getSliptestName() {
        return sliptestName;
    }

    public void setSliptestName(String sliptestName) {
        this.sliptestName = sliptestName;
    }

    public String getExtraPortion() {
        return extraPortion;
    }

    public void setExtraPortion(String extraPortion) {
        this.extraPortion = extraPortion;
    }

    public float getMaximumMark() {
        return maximumMark;
    }

    public void setMaximumMark(float maximumMark) {
        this.maximumMark = maximumMark;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getPortionIds() {
        return portionIds;
    }

    public void setPortionIds(String portionIds) {
        this.portionIds = portionIds;
    }

}
