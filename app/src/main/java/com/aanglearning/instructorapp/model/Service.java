package com.aanglearning.instructorapp.model;

public class Service {
    private long id;
    private long schoolId;
    private String IsMessage;
    private String isSms;
    private String isAttendance;
    private String isHomework;
    private String isAttendanceSms;
    private String isHomeworkSms;

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

    public String getIsMessage() {
        return IsMessage;
    }

    public void setIsMessage(String isMessage) {
        IsMessage = isMessage;
    }

    public String getIsSms() {
        return isSms;
    }

    public void setIsSms(String isSms) {
        this.isSms = isSms;
    }

    public String getIsAttendance() {
        return isAttendance;
    }

    public void setIsAttendance(String isAttendance) {
        this.isAttendance = isAttendance;
    }

    public String getIsHomework() {
        return isHomework;
    }

    public void setIsHomework(String isHomework) {
        this.isHomework = isHomework;
    }

    public String getIsAttendanceSms() {
        return isAttendanceSms;
    }

    public void setIsAttendanceSms(String isAttendanceSms) {
        this.isAttendanceSms = isAttendanceSms;
    }

    public String getIsHomeworkSms() {
        return isHomeworkSms;
    }

    public void setIsHomeworkSms(String isHomeworkSms) {
        this.isHomeworkSms = isHomeworkSms;
    }
}
