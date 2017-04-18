package com.aanglearning.instructorapp.model;

public class Service {
    private long id;
    private long schoolId;
    private boolean message;
    private boolean sms;
    private boolean attendance;
    private boolean homework;
    private boolean attendanceSms;
    private boolean homeworkSms;

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

    public boolean isMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public boolean isSms() {
        return sms;
    }

    public void setSms(boolean sms) {
        this.sms = sms;
    }

    public boolean isAttendance() {
        return attendance;
    }

    public void setAttendance(boolean attendance) {
        this.attendance = attendance;
    }

    public boolean isHomework() {
        return homework;
    }

    public void setHomework(boolean homework) {
        this.homework = homework;
    }

    public boolean isAttendanceSms() {
        return attendanceSms;
    }

    public void setAttendanceSms(boolean attendanceSms) {
        this.attendanceSms = attendanceSms;
    }

    public boolean isHomeworkSms() {
        return homeworkSms;
    }

    public void setHomeworkSms(boolean homeworkSms) {
        this.homeworkSms = homeworkSms;
    }

}
