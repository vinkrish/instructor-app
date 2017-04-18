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
        String sql = "insert into service(Id, SchoolId, IsMessage, IsSms, IsAttendance, IsAttendanceSms, isHomework, isHomeworksms) " +
                "values(?,?,?,?,?,?,?,?)";
        SQLiteDatabase db = AppGlobal.getSqlDbHelper().getWritableDatabase();
        db.beginTransactionNonExclusive();
        SQLiteStatement stmt = db.compileStatement(sql);
        try {
            stmt.bindLong(1, service.getId());
            stmt.bindLong(2, service.getSchoolId());
            stmt.bindString(3, Boolean.toString(service.isMessage()));
            stmt.bindString(4, Boolean.toString(service.isSms()));
            stmt.bindString(5, Boolean.toString(service.isAttendance()));
            stmt.bindString(6, Boolean.toString(service.isAttendanceSms()));
            stmt.bindString(7, Boolean.toString(service.isHomework()));
            stmt.bindString(8, Boolean.toString(service.isHomeworkSms()));
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
            service.setMessage(Boolean.parseBoolean(c.getString(c.getColumnIndex("IsMessage"))));
            service.setMessage(Boolean.parseBoolean(c.getString(c.getColumnIndex("IsSms"))));
            service.setMessage(Boolean.parseBoolean(c.getString(c.getColumnIndex("IsAttendance"))));
            service.setMessage(Boolean.parseBoolean(c.getString(c.getColumnIndex("IsAttendanceSms"))));
            service.setMessage(Boolean.parseBoolean(c.getString(c.getColumnIndex("IsHomework"))));
            service.setMessage(Boolean.parseBoolean(c.getString(c.getColumnIndex("IsHomeworkSms"))));
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
