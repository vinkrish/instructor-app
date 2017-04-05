package com.aanglearning.instructorapp.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.aanglearning.instructorapp.model.Groups;
import com.aanglearning.instructorapp.util.AppGlobal;

/**
 * Created by Vinay on 03-04-2017.
 */

public class GroupDao {

    public static int insert(Groups group) {
        String sql = "insert into groups(Id, Name, SectionId, IsSection, ClassId, IsClass, CreatedBy, CreatedDate, IsActive) " +
                "values(?,?,?,?,?,?,?,?,?)";
        SQLiteDatabase db = AppGlobal.getSqlDbHelper().getWritableDatabase();
        db.beginTransactionNonExclusive();
        SQLiteStatement stmt = db.compileStatement(sql);
        try {
            stmt.bindLong(1, group.getId());
            stmt.bindString(2, group.getName());
            stmt.bindLong(3, group.getSectionId());
            stmt.bindString(4, Boolean.toString(group.isSection()));
            stmt.bindLong(5, group.getClassId());
            stmt.bindString(6, Boolean.toString(group.isClas()));
            stmt.bindLong(7, group.getCreatedBy());
            stmt.bindString(8, group.getCreatedDate());
            stmt.bindString(9, Boolean.toString(group.isActive()));
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

    public static Groups getGroup() {
        Groups group = new Groups();
        SQLiteDatabase sqliteDatabase = AppGlobal.getSqlDbHelper().getReadableDatabase();
        Cursor c = sqliteDatabase.rawQuery("select * from groups", null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            group.setId(c.getLong(c.getColumnIndex("Id")));
            group.setName(c.getString(c.getColumnIndex("Name")));
            group.setSectionId(c.getLong(c.getColumnIndex("SectionId")));
            group.setSection(Boolean.parseBoolean(c.getString(c.getColumnIndex("IsSection"))));
            group.setClassId(c.getLong(c.getColumnIndex("ClassId")));
            group.setClas(Boolean.parseBoolean(c.getString(c.getColumnIndex("IsClass"))));
            group.setCreatedBy(c.getLong(c.getColumnIndex("CreatedBy")));
            group.setCreatedDate(c.getString(c.getColumnIndex("CreatedDate")));
            group.setActive(Boolean.parseBoolean(c.getString(c.getColumnIndex("IsActive"))));
            c.moveToNext();
        }
        c.close();
        return group;
    }

    public static int clear() {
        String sql = "delete from groups";
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
