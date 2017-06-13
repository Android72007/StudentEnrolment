package com.example.noone.studentenrolment.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by No One on 4/1/2017.
 */

public class StudentDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_SCHEME = "student.db";
    private static final int DATABASE_VERSION = 1;

    public StudentDBHelper(Context context){
        super(context,DATABASE_SCHEME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_STUDENT_TABLE = "CREATE TABLE "+ StudentContract.StudentEntry.TABLE_NAME + "( "
                            + StudentContract.StudentEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + StudentContract.StudentEntry.COLUMN_FIRST_NAME + " TEXT, "
                            + StudentContract.StudentEntry.COLUMN_LAST_NAME + " TEXT, "
                            + StudentContract.StudentEntry.MARKS + " INTEGER );";
        db.execSQL(SQL_CREATE_STUDENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
