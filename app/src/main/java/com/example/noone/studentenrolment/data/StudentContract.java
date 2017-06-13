package com.example.noone.studentenrolment.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by No One on 4/1/2017.
 */

public final class StudentContract {
    private StudentContract(){};
    public static final String CONTENT_AUTHORITY = "com.example.noone.studentenrolment";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_STUDENTS = "student";

    public static final class StudentEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI , PATH_STUDENTS);
        public static final String TABLE_NAME = "student";
        public static final String COLUMN_FIRST_NAME = "firstname";
        public static final String COLUMN_LAST_NAME = "lastname";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String MARKS = "marks";

    }
}
