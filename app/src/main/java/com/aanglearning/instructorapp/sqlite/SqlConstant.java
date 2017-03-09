package com.aanglearning.instructorapp.sqlite;

/**
 * Created by Vinay.
 */
public interface SqlConstant {

    String DATABASE_NAME = "project.db";
    int DATABASE_VERSION = 1;

    String CREATE_ACTIVITY = "CREATE TABLE activity (" +
            "  Id INTEGER PRIMARY KEY," +
            "  SectionId INTEGER," +
            "  ExamId INTEGER," +
            "  SubjectId INTEGER," +
            "  ActivityName INTEGER," +
            "  Type TEXT DEFAULT 'Mark'," +
            "  MaximumMark REAL," +
            "  Weightage REAL," +
            "  Calculation INTEGER," +
            "  ActivityAvg REAL," +
            "  Orders INTEGER" +
            ")";

    String CREATE_ACTIVITY_SCORE = "CREATE TABLE activity_score (" +
            "  Id INTEGER" +
            "  ActivityId INTEGER" +
            "  StudentId INTEGER" +
            "  Mark REAL," +
            "  Grade TEXT" +
            ")";

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

    String CREATE_CCE_ASPECT_GRADE = "CREATE TABLE cce_aspect_grade (" +
            "  Id INTEGER," +
            "  SectionId INTEGER," +
            "  StudentId INTEGER," +
            "  AspectId INTEGER," +
            "  Type INTEGER," +
            "  Term INTEGER," +
            "  Grade TEXT," +
            "  Value INTEGER," +
            "  Description TEXT" +
            ")";

    String CREATE_CCE_ASPECT_PRIMARY = "CREATE TABLE cce_aspect_primary (" +
            "  Id INTEGER," +
            "  Name TEXT," +
            "  TopicId INTEGER" +
            ")";

    String CREATE_CCE_COSCHOLASTIC = "CREATE TABLE cce_coscholastic (" +
            "  Id INTEGER," +
            "  SchoolId INTEGER," +
            "  Name TEXT" +
            ")";

    String CREATE_CCE_COSCHOLASTIC_CLASS = "CREATE TABLE cce_coscholastic_class (" +
            "  Id INTEGER," +
            "  CoScholasticId INTEGER," +
            "  ClassId INTEGER," +
            "  ClassName TEXT" +
            ")";

    String CREATE_CCE_SECTION_HEADING = "CREATE TABLE cce_section_heading (" +
            "  Id INTEGER," +
            "  Name TEXT," +
            "  CoScholasticId INTEGER" +
            ")";

    String CREATE_CCE_STUDENT_PROFILE = "CREATE TABLE cce_student_profile (" +
            "  Id INTEGER," +
            "  SectionId INTEGER," +
            "  StudentId INTEGER," +
            "  Term INTEGER," +
            "  FromDate date," +
            "  ToDate date," +
            "  TotalDays INTEGER," +
            "  DaysAttended REAL," +
            "  Height REAL," +
            "  Weight REAL," +
            "  BloodGroup TEXT," +
            "  HealthStatus TEXT," +
            "  VisionLeft TEXT," +
            "  VisionRight TEXT," +
            "  Ailment TEXT," +
            "  OralHygiene TEXT" +
            ")";

    String CREATE_CCE_TOPIC_GRADE = "CREATE TABLE cce_topic_grade (" +
            "  Id INTEGER," +
            "  TopicId INTEGER," +
            "  Grade TEXT," +
            "  Value INTEGER" +
            ")";

    String CREATE_CCE_TOPIC_PRIMARY = "CREATE TABLE cce_topic_primary (" +
            "  Id INTEGER," +
            "  Name TEXT," +
            "  SectionHeadingId INTEGER," +
            "  Evaluation INTEGER" +
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

    String CREATE_EXAM = "CREATE TABLE exam (" +
            "  Id INTEGER," +
            "  ExamName TEXT," +
            "  ClassId INTEGER," +
            "  Term INTEGER," +
            "  Type TEXT," +
            "  Percentage REAL" +
            ")";

    String CREATE_EXAM_SUBJECT = "CREATE TABLE exam_subject (" +
            "  Id INTEGER," +
            "  ExamId INTEGER," +
            "  SubjectId INTEGER," +
            "  SubjectName INTEGER," +
            "  Type TEXT," +
            "  MaximumMark REAL," +
            "  FailMark REAL," +
            "  Percentage REAL," +
            "  Orders INTEGER" +
            ")";

    String CREATE_EXAM_SUBJECT_GROUP = "CREATE TABLE exam_subject_group (" +
            "  Id INTEGER," +
            "  ExamId INTEGER," +
            "  SubjectGroupId INTEGER," +
            "  SubjectGroupName TEXT" +
            ")";

    String CREATE_GRADE_CLASS_WISE = "CREATE TABLE grade_class_wise (" +
            "  Id INTEGER," +
            "  ClassId INTEGER," +
            "  Grade TEXT," +
            "  MarkFrom INTEGER," +
            "  MarkTo INTEGER," +
            "  GradePoint INTEGER" +
            ")";

    String CREATE_HOMEWORK = "CREATE TABLE homework (" +
            "  Id INTEGER," +
            "  SectionId INTEGER," +
            "  SubjectId INTEGER," +
            "  SubjectName TEXT," +
            "  HomeworkMessage TEXT," +
            "  HomeworkDate date" +
            ")";

    String CREATE_MARK = "CREATE TABLE mark (" +
            "  Id INTEGER," +
            "  ExamId INTEGER," +
            "  SubjectId INTEGER," +
            "  SectionId INTEGER," +
            "  StudentId INTEGER," +
            "  Mark REAL" +
            "  Grade TEXT" +
            ")";

    String CREATE_PORTION = "CREATE TABLE portion (" +
            "  Id INTEGER," +
            "  ClassId INTEGER," +
            "  SubjectId INTEGER," +
            "  PortionName TEXT" +
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

    String CREATE_SLIPTEST = "CREATE TABLE sliptest (" +
            "  Id INTEGER," +
            "  SectionId INTEGER," +
            "  SubjectId INTEGER," +
            "  SliptestName TEXT," +
            "  PortionIds TEXT," +
            "  ExtraPortion TEXT," +
            "  MaximumMark REAL," +
            "  Average REAL," +
            "  TestDate date," +
            "  SubmissionDate date" +
            ")";

    String CREATE_SLIPTEST_PORTION = "CREATE TABLE sliptest_portion (" +
            "  Id INTEGER," +
            "  SliptestId INTEGER," +
            "  PortionId INTEGER," +
            "  PortionName TEXT" +
            ")";

    String CREATE_SLIPTEST_SCORE = "CREATE TABLE sliptest_score (" +
            "  Id INTEGER," +
            "  SliptestId INTEGER," +
            "  StudentId INTEGER," +
            "  Mark REAL," +
            "  Grade TEXT" +
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

    String CREATE_SUBACTIVITY = "CREATE TABLE subactivity (" +
            "  Id INTEGER," +
            "  ActivityId INTEGER," +
            "  SubActivityName TEXT," +
            "  Type TEXT," +
            "  MaximumMark REAL," +
            "  Weightage REAL," +
            "  Calculation INTEGER," +
            "  SubActivityAvg REAL," +
            "  Orders INTEGER" +
            ")";

    String CREATE_SUBACTIVITY_SCORE = "CREATE TABLE subactivity_score (" +
            "  Id INTEGER," +
            "  SubActivityId INTEGER," +
            "  StudentId INTEGER," +
            "  Mark REAL," +
            "  Grade TEXT" +
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

    String CREATE_TERM_REMARK = "CREATE TABLE term_remark (" +
            "  Id INTEGER," +
            "  SectionId INTEGER," +
            "  StudentId INTEGER," +
            "  Term INTEGER," +
            "  Remark TEXT" +
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

}
