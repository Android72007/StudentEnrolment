package com.example.noone.studentenrolment.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by No One on 4/1/2017.
 */

public class StudentProvider extends ContentProvider {

    private static final int STUDENT = 100;
    private static final int STUDENT_ID = 101;
    final static UriMatcher sMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        sMatcher.addURI(StudentContract.CONTENT_AUTHORITY, StudentContract.PATH_STUDENTS, STUDENT);
        sMatcher.addURI(StudentContract.CONTENT_AUTHORITY, StudentContract.PATH_STUDENTS + "/#" , STUDENT_ID);
    }
    private StudentDBHelper dbHelper;
    @Override
    public boolean onCreate() {
        dbHelper = new StudentDBHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection,String selection,  String[] selectionArgs,  String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c;
        int id = sMatcher.match(uri);
        switch (id){
            case STUDENT:
                    c = db.query(StudentContract.StudentEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case STUDENT_ID:
                selection = StudentContract.StudentEntry.COLUMN_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                c = db.query(StudentContract.StudentEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        c.setNotificationUri(getContext().getContentResolver(),uri);
        return c;
    }


    @Override
    public String getType( Uri uri) {
        return null;
    }


    @Override
    public Uri insert( Uri uri, ContentValues values) {
        int id = sMatcher.match(uri);
        switch (id){
            case STUDENT:
                return insertStudent(values,uri);
            default:
                throw new IllegalArgumentException("Insertio is not supported"+uri);
        }
    }

    @Override
    public int delete(Uri uri,  String selection,  String[] selectionArgs) {

        return 0;
    }

    @Override
    public int update( Uri uri,  ContentValues values, String selection,  String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(StudentContract.StudentEntry.TABLE_NAME,values,selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);
        return 0;
    }

    public Uri insertStudent(ContentValues values, Uri uri){
        String firstname = values.getAsString(StudentContract.StudentEntry.COLUMN_FIRST_NAME);
        String lastname = values.getAsString(StudentContract.StudentEntry.COLUMN_LAST_NAME);
        if(firstname == null && lastname == null){
            throw new IllegalArgumentException("Student requires either first name or last name");
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert(StudentContract.StudentEntry.TABLE_NAME,null,values);
        if(id == -1){
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri,id);



    }
}
