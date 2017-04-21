package com.aanglearning.instructorapp.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.aanglearning.instructorapp.model.Service;
import com.aanglearning.instructorapp.util.AppGlobal;

/**
 * Created by Vinay on 17-04-2017.
 */

public class ServiceDao {
    public static int insert(Service service) {
        String sql = "insert into service(Id, SchoolId, IsMessage, IsSms, IsAttendance, IsAttendanceSms, isHomework, isHomeworkSms) " +
                "values(?,?,?,?,?,?,?,?)";
        SQLiteDatabase db = AppGlobal.getSqlDbHelper().getWritableDatabase();
        db.beginTransactionNonExclusive();
        SQLiteStatement stmt = db.compileStatement(sql);
        try {
            stmt.bindLong(1, service.getId());
            stmt.bindLong(2, service.getSchoolId());
            stmt.bindString(3, service.getIsMessage());
            stmt.bindString(4, service.getIsSms());
            stmt.bindString(5, service.getIsAttendance());
            stmt.bindString(6, service.getIsAttendanceSms());
            stmt.bindString(7, service.getIsHomework());
            stmt.bindString(8, service.getIsHomeworkSms());
            stmt.execute();
            stmt.clearBindings();
        } catch (Exception e) {
            db.endTransaction();
            return 0;
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return 1;
    }

    public static Service getServices() {
        Service service = new Service();
        SQLiteDatabase sqliteDatabase = AppGlobal.getSqlDbHelper().getReadableDatabase();
        Cursor c = sqliteDatabase.rawQuery("select * from service", null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            service.setId(c.getLong(c.getColumnIndex("Id")));
            service.setSchoolId(c.getLong(c.getColumnIndex("SchoolId")));
            service.setIsMessage(c.getString(c.getColumnIndex("IsMessage")));
            service.setIsSms(c.getString(c.getColumnIndex("IsSms")));
            service.setIsAttendance(c.getString(c.getColumnIndex("IsAttendance")));
            service.setIsAttendanceSms(c.getString(c.getColumnIndex("IsAttendanceSms")));
            service.setIsHomework(c.getString(c.getColumnIndex("IsHomework")));
            service.setIsHomeworkSms(c.getString(c.getColumnIndex("IsHomeworkSms")));
            c.moveToNext();
        }
        c.close();
        return service;
    }

    public static int clear() {
        String sql = "delete from service";
        SQLiteDatabase db = AppGlobal.getSqlDbHelper().getWritableDatabase();
        db.beginTransactionNonExclusive();
        SQLiteStatement stmt = db.compileStatement(sql);
        try {
            stmt.execute();
            stmt.clearBindings();
        } catch (Exception e) {
            db.endTransaction();
            return 0;
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return 1;
    }
}
