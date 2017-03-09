package com.aanglearning.instructorapp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shrey.
 */
public class SqlDbHelper extends SQLiteOpenHelper implements SqlConstant {
    private static SqlDbHelper dbHelper;
    public SQLiteDatabase sqliteDatabase;

    private SqlDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SqlDbHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new SqlDbHelper(context.getApplicationContext());
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACTIVITY);
        db.execSQL(CREATE_ACTIVITY_SCORE);
        db.execSQL(CREATE_ATTENDANCE);
        db.execSQL(CREATE_CCE_ASPECT_GRADE);
        db.execSQL(CREATE_CCE_ASPECT_PRIMARY);
        db.execSQL(CREATE_CCE_COSCHOLASTIC);
        db.execSQL(CREATE_CCE_COSCHOLASTIC_CLASS);
        db.execSQL(CREATE_CCE_SECTION_HEADING);
        db.execSQL(CREATE_CCE_STUDENT_PROFILE);
        db.execSQL(CREATE_CCE_TOPIC_GRADE);
        db.execSQL(CREATE_CCE_TOPIC_PRIMARY);
        db.execSQL(CREATE_CLASS);
        db.execSQL(CREATE_CLASS_SUBJECT_GROUP);
        db.execSQL(CREATE_EXAM);
        db.execSQL(CREATE_EXAM_SUBJECT);
        db.execSQL(CREATE_EXAM_SUBJECT_GROUP);
        db.execSQL(CREATE_GRADE_CLASS_WISE);
        db.execSQL(CREATE_HOMEWORK);
        db.execSQL(CREATE_MARK);
        db.execSQL(CREATE_PORTION);
        db.execSQL(CREATE_SCHOOL);
        db.execSQL(CREATE_SECTION);
        db.execSQL(CREATE_SLIPTEST);
        db.execSQL(CREATE_SLIPTEST_PORTION);
        db.execSQL(CREATE_SLIPTEST_SCORE);
        db.execSQL(CREATE_STUDENT);
        db.execSQL(CREATE_SUBACTIVITY);
        db.execSQL(CREATE_SUBACTIVITY_SCORE);
        db.execSQL(CREATE_SUBJECT);
        db.execSQL(CREATE_SUBJECT_GROUP);
        db.execSQL(CREATE_SUBJECT_GROUP_SUBJECT);
        db.execSQL(CREATE_SUBJECT_STUDENT);
        db.execSQL(CREATE_SUBJECT_TEACHER);
        db.execSQL(CREATE_TEACHER);
        db.execSQL(CREATE_TERM_REMARK);
        db.execSQL(CREATE_TIMETABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS activity");
        db.execSQL("DROP TABLE IF EXISTS activity_score");
        db.execSQL("DROP TABLE IF EXISTS attendance");
        db.execSQL("DROP TABLE IF EXISTS cce_aspect_grade");
        db.execSQL("DROP TABLE IF EXISTS cce_aspect_primary");
        db.execSQL("DROP TABLE IF EXISTS cce_coscholastic");
        db.execSQL("DROP TABLE IF EXISTS cce_coscholastic_class");
        db.execSQL("DROP TABLE IF EXISTS cce_section_heading");
        db.execSQL("DROP TABLE IF EXISTS cce_student_profile");
        db.execSQL("DROP TABLE IF EXISTS cce_topic_grade");
        db.execSQL("DROP TABLE IF EXISTS cce_topic_primary");
        db.execSQL("DROP TABLE IF EXISTS class");
        db.execSQL("DROP TABLE IF EXISTS class_subject_group");
        db.execSQL("DROP TABLE IF EXISTS exam");
        db.execSQL("DROP TABLE IF EXISTS exam_subject");
        db.execSQL("DROP TABLE IF EXISTS exam_subject_group");
        db.execSQL("DROP TABLE IF EXISTS grade_class_wise");
        db.execSQL("DROP TABLE IF EXISTS homework");
        db.execSQL("DROP TABLE IF EXISTS mark");
        db.execSQL("DROP TABLE IF EXISTS portion");
        db.execSQL("DROP TABLE IF EXISTS school");
        db.execSQL("DROP TABLE IF EXISTS section");
        db.execSQL("DROP TABLE IF EXISTS sliptest");
        db.execSQL("DROP TABLE IF EXISTS sliptest_portion");
        db.execSQL("DROP TABLE IF EXISTS sliptest_score");
        db.execSQL("DROP TABLE IF EXISTS student");
        db.execSQL("DROP TABLE IF EXISTS subactivity");
        db.execSQL("DROP TABLE IF EXISTS subactivity_score");
        db.execSQL("DROP TABLE IF EXISTS subject");
        db.execSQL("DROP TABLE IF EXISTS subject_group");
        db.execSQL("DROP TABLE IF EXISTS subject_group_subject");
        db.execSQL("DROP TABLE IF EXISTS subject_student");
        db.execSQL("DROP TABLE IF EXISTS subject_teacher");
        db.execSQL("DROP TABLE IF EXISTS teacher");
        db.execSQL("DROP TABLE IF EXISTS term_remark");
        db.execSQL("DROP TABLE IF EXISTS timetable");
        onCreate(db);
    }

    public void deleteTables() {
        sqliteDatabase = dbHelper.getWritableDatabase();
        sqliteDatabase.delete("activity", null, null);
        sqliteDatabase.delete("activity_score", null, null);
        sqliteDatabase.delete("attendance", null, null);
        sqliteDatabase.delete("cce_aspect_grade", null, null);
        sqliteDatabase.delete("cce_aspect_primary", null, null);
        sqliteDatabase.delete("cce_coscholastic", null, null);
        sqliteDatabase.delete("cce_coscholastic_class", null, null);
        sqliteDatabase.delete("cce_section_heading", null, null);
        sqliteDatabase.delete("cce_student_profile", null, null);
        sqliteDatabase.delete("cce_topic_grade", null, null);
        sqliteDatabase.delete("cce_topic_primary", null, null);
        sqliteDatabase.delete("class", null, null);
        sqliteDatabase.delete("class_subject_group", null, null);
        sqliteDatabase.delete("exam", null, null);
        sqliteDatabase.delete("exam_subject", null, null);
        sqliteDatabase.delete("exam_subject_group", null, null);
        sqliteDatabase.delete("grade_class_wise", null, null);
        sqliteDatabase.delete("homework", null, null);
        sqliteDatabase.delete("mark", null, null);
        sqliteDatabase.delete("portion", null, null);
        sqliteDatabase.delete("school", null, null);
        sqliteDatabase.delete("section", null, null);
        sqliteDatabase.delete("sliptest", null, null);
        sqliteDatabase.delete("sliptest_portion", null, null);
        sqliteDatabase.delete("sliptest_score", null, null);
        sqliteDatabase.delete("student", null, null);
        sqliteDatabase.delete("subactivity", null, null);
        sqliteDatabase.delete("subactivity_score", null, null);
        sqliteDatabase.delete("subject", null, null);
        sqliteDatabase.delete("subject_group", null, null);
        sqliteDatabase.delete("subject_group_subject", null, null);
        sqliteDatabase.delete("subject_student", null, null);
        sqliteDatabase.delete("subject_teacher", null, null);
        sqliteDatabase.delete("teacher", null, null);
        sqliteDatabase.delete("term_remark", null, null);
        sqliteDatabase.delete("timetable", null, null);
    }
}
