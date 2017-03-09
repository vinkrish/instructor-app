package com.aanglearning.instructorapp.model;

public class CceStudentProfile {
    private long id;
    private long sectionId;
    private long studentId;
    private int term;
    private String fromDate;
    private String toDate;
    private int totalDays;
    private float daysAttended;
    private float height;
    private float weight;
    private String healthStatus;
    private String bloodGroup;
    private String visionLeft;
    private String visionRight;
    private String ailment;
    private String oralHygiene;

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

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public float getDaysAttended() {
        return daysAttended;
    }

    public void setDaysAttended(float daysAttended) {
        this.daysAttended = daysAttended;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getVisionLeft() {
        return visionLeft;
    }

    public void setVisionLeft(String visionLeft) {
        this.visionLeft = visionLeft;
    }

    public String getVisionRight() {
        return visionRight;
    }

    public void setVisionRight(String visionRight) {
        this.visionRight = visionRight;
    }

    public String getAilment() {
        return ailment;
    }

    public void setAilment(String ailment) {
        this.ailment = ailment;
    }

    public String getOralHygiene() {
        return oralHygiene;
    }

    public void setOralHygiene(String oralHygiene) {
        this.oralHygiene = oralHygiene;
    }

}
