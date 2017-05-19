package com.aanglearning.instructorapp.dao;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.aanglearning.instructorapp.model.Groups;
import com.aanglearning.instructorapp.util.AppGlobal;

import java.util.ArrayList;
import java.util.List;

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

    public static int insertMany(List<Groups> groups) {
        String sql = "insert into groups(Id, Name, SectionId, IsSection, ClassId, IsClass, CreatedBy, CreatedDate, IsActive) " +
                "values(?,?,?,?,?,?,?,?,?)";
        SQLiteDatabase db = AppGlobal.getSqlDbHelper().getWritableDatabase();
        db.beginTransactionNonExclusive();
        SQLiteStatement stmt = db.compileStatement(sql);
        try {
            for(Groups group: groups) {
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
            }
        } catch (Exception e) {
            db.endTransaction();
            return 0;
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return 1;
    }

    public static Groups getGroup(long id) {
        Groups group = new Groups();
        SQLiteDatabase sqliteDatabase = AppGlobal.getSqlDbHelper().getReadableDatabase();
        Cursor c = sqliteDatabase.rawQuery("select * from groups where Id = " + id, null);
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

    public static List<Groups> getGroups() {
        List<Groups> groups = new ArrayList<>();
        SQLiteDatabase sqliteDatabase = AppGlobal.getSqlDbHelper().getReadableDatabase();
        Cursor c = sqliteDatabase.rawQuery("select * from groups", null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Groups group = new Groups();
            group.setId(c.getLong(c.getColumnIndex("Id")));
            group.setName(c.getString(c.getColumnIndex("Name")));
            group.setSectionId(c.getLong(c.getColumnIndex("SectionId")));
            group.setSection(Boolean.parseBoolean(c.getString(c.getColumnIndex("IsSection"))));
            group.setClassId(c.getLong(c.getColumnIndex("ClassId")));
            group.setClas(Boolean.parseBoolean(c.getString(c.getColumnIndex("IsClass"))));
            group.setCreatedBy(c.getLong(c.getColumnIndex("CreatedBy")));
            group.setCreatedDate(c.getString(c.getColumnIndex("CreatedDate")));
            group.setActive(Boolean.parseBoolean(c.getString(c.getColumnIndex("IsActive"))));
            groups.add(group);
            c.moveToNext();
        }
        c.close();
        return groups;
    }

    public static int clear() {
        SQLiteDatabase sqliteDb = AppGlobal.getSqlDbHelper().getWritableDatabase();
        try {
            sqliteDb.execSQL("delete from groups");
        } catch(SQLException e) {
            return 0;
        }
        return 1;
    }
}
