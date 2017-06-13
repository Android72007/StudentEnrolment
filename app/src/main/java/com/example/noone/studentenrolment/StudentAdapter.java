package com.example.noone.studentenrolment;

import android.widget.CursorAdapter;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.noone.studentenrolment.data.StudentContract;

/**
 * Created by No One on 4/1/2017.
 */

public class StudentAdapter extends CursorAdapter {
    public StudentAdapter(Context context, Cursor c){
        super(context,c,0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView firstname = (TextView)view.findViewById(R.id.first_name);
        TextView lastname = (TextView)view.findViewById(R.id.last_name);

        int firstindex = cursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_FIRST_NAME);
        int lastindex = cursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_LAST_NAME);

        firstname.setText(cursor.getString(firstindex));
        lastname.setText(cursor.getString(lastindex));
    }
}
