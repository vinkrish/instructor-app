package com.aanglearning.instructorapp.attendance;

import com.aanglearning.instructorapp.model.Attendance;
import com.aanglearning.instructorapp.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinay on 22-04-2017.
 */

public class AttendanceSet {
    private ArrayList<Attendance> attendanceList;
    private ArrayList<Student> students;

    public ArrayList<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(ArrayList<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }
}
