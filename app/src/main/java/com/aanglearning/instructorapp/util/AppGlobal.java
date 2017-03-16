package com.aanglearning.instructorapp.util;

import android.content.Context;

import com.aanglearning.instructorapp.sqlite.SqlDbHelper;

/**
 * Created by Vinay on 21-02-2017.
 */

public class AppGlobal {
    private static SqlDbHelper sqlDbHelper;

    public static SqlDbHelper getSqlDbHelper() {
        return sqlDbHelper;
    }

    public static void setSqlDbHelper(Context context) {
        AppGlobal.sqlDbHelper = SqlDbHelper.getInstance(context);;
    }
}
