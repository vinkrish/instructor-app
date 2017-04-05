package com.aanglearning.instructorapp.sqlite;

/**
 * Created by Vinay.
 */
public interface SqlConstant {

    String DATABASE_NAME = "project.db";
    int DATABASE_VERSION = 1;

    String CREATE_ATTENDANCE = "CREATE TABLE attendance (" +
            "  Id INTEGER PRIMARY KEY," +
            "  SectionId INTEGER," +
            "  StudentId INTEGER," +
            "  StudentName TEXT," +
            "  SubjectId INTEGER," +
            "  Type TEXT," +
            "  Session INTEGER," +
            "  DateAttendance date," +
            "  TypeOfLeave TEXT" +
            ")";

    String CREATE_CLASS = "CREATE TABLE class (" +
            "  Id INTEGER," +
            "  ClassName TEXT," +
            "  SchoolId INTEGER," +
            "  AttendanceType TEXT" +
            ")";

    String CREATE_CLASS_SUBJECT_GROUP = "CREATE TABLE class_subject_group (" +
            "  Id INTEGER," +
            "  ClassId INTEGER," +
            "  SubjectGroupId INTEGER," +
            "  SubjectGroupName TEXT" +
            ")";

    String CREATE_HOMEWORK = "CREATE TABLE homework (" +
            "  Id INTEGER," +
            "  SectionId INTEGER," +
            "  SubjectId INTEGER," +
            "  SubjectName TEXT," +
            "  HomeworkMessage TEXT," +
            "  HomeworkDate date" +
            ")";

    String CREATE_SCHOOL = "CREATE TABLE school (" +
            "  Id INTEGER," +
            "  SchoolName TEXT," +
            "  Website TEXT," +
            "  ShortenedSchoolName TEXT," +
            "  ContactPersonName TEXT," +
            "  AdminUsername TEXT," +
            "  AdminPassword TEXT," +
            "  Landline TEXT," +
            "  Mobile1 TEXT," +
            "  Mobile2 TEXT," +
            "  Email TEXT," +
            "  Street TEXT," +
            "  City TEXT," +
            "  District TEXT," +
            "  State TEXT," +
            "  Pincode TEXT," +
            "  PrincipalId INTEGER," +
            "  NumberOfStudents INTEGER" +
            ")";

    String CREATE_SECTION = "CREATE TABLE section (" +
            "  Id INTEGER," +
            "  SectionName TEXT," +
            "  ClassId INTEGER," +
            "  TeacherId INTEGER" +
            ")";

    String CREATE_STUDENT = "CREATE TABLE student (" +
            "  Id INTEGER," +
            "  StudentName TEXT," +
            "  SchoolId INTEGER," +
            "  SectionId INTEGER," +
            "  AdmissionNo TEXT," +
            "  RollNo INTEGER," +
            "  Username TEXT," +
            "  Password TEXT," +
            "  Image TEXT," +
            "  FatherName TEXT," +
            "  MotherName TEXT," +
            "  DateOfBirth date," +
            "  Gender TEXT," +
            "  Email TEXT," +
            "  Mobile1 TEXT," +
            "  Mobile2 TEXT," +
            "  Street TEXT," +
            "  City TEXT," +
            "  District TEXT," +
            "  State TEXT," +
            "  Pincode TEXT" +
            ")";

    String CREATE_SUBJECT = "CREATE TABLE subject (" +
            "  Id INTEGER," +
            "  SchoolId INTEGER," +
            "  SubjectName TEXT," +
            "  PartitionType INTEGER," +
            "  TheorySubjectId INTEGER," +
            "  PracticalSubjectId INTEGER" +
            ")";

    String CREATE_SUBJECT_GROUP = "CREATE TABLE subject_group (" +
            "  Id INTEGER," +
            "  SchoolId INTEGER," +
            "  SubjectGroupName TEXT" +
            ")";

    String CREATE_SUBJECT_GROUP_SUBJECT = "CREATE TABLE subject_group_subject (" +
            "  Id INTEGER," +
            "  SubjectGroupId INTEGER," +
            "  SubjectId INTEGER," +
            "  SubjectName TEXT" +
            ")";

    String CREATE_SUBJECT_STUDENT = "CREATE TABLE subject_student (" +
            "  Id INTEGER," +
            "  SectionId INTEGER," +
            "  SubjectId INTEGER," +
            "  StudentIds TEXT" +
            ")";

    String CREATE_SUBJECT_TEACHER = "CREATE TABLE subject_teacher (" +
            "  Id INTEGER," +
            "  SectionId INTEGER," +
            "  SubjectId INTEGER," +
            "  SubjectName TEXT," +
            "  TeacherId INTEGER," +
            "  TeacherName TEXT" +
            ")";

    String CREATE_TEACHER = "CREATE TABLE teacher (" +
            "  Id INTEGER," +
            "  TeacherName TEXT," +
            "  Image TEXT," +
            "  Username TEXT," +
            "  Password TEXT," +
            "  SchoolId INTEGER," +
            "  DateOfBirth date," +
            "  Mobile TEXT," +
            "  Qualification TEXT," +
            "  DateOfJoining date," +
            "  Gender TEXT," +
            "  Email TEXT" +
            ")";

    String CREATE_TIMETABLE = "CREATE TABLE timetable (" +
            "  Id INTEGER," +
            "  SectionId INTEGER," +
            "  DayOfWeek TEXT," +
            "  PeriodNo INTEGER," +
            "  SubjectId INTEGER," +
            "  TimingFrom time," +
            "  TimingTo time" +
            ")";

    String CREATE_GROUPS = "CREATE TABLE groups (" +
            " Id INTEGER, " +
            " Name TEXT, " +
            " SectionId INTEGER, " +
            " IsSection INTEGER, " +
            " ClassId INTEGER, " +
            " IsClass INTEGER," +
            " CreatedBy INEGER, " +
            " CreatedDate TEXT, " +
            " IsActive INTEGER" +
            ")";

}
