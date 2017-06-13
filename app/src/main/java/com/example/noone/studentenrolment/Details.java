package com.example.noone.studentenrolment;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.noone.studentenrolment.data.StudentContract;

public class Details extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


  private  Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
         uri = intent.getData();
      getLoaderManager().initLoader(1,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String [] projection = {
                StudentContract.StudentEntry.COLUMN_ID,
                StudentContract.StudentEntry.COLUMN_FIRST_NAME,
                StudentContract.StudentEntry.COLUMN_LAST_NAME,
                StudentContract.StudentEntry.MARKS
        };
        return new CursorLoader(this, uri, projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        EditText first = (EditText)findViewById(R.id.first_name_edit);
        EditText last = (EditText) findViewById(R.id.last_name_edit);
        EditText mark = (EditText) findViewById(R.id.marks);
        if(data.moveToFirst()) {

            first.setText(data.getString(data.getColumnIndexOrThrow(StudentContract.StudentEntry.COLUMN_FIRST_NAME)));
            last.setText(data.getString(data.getColumnIndexOrThrow(StudentContract.StudentEntry.COLUMN_LAST_NAME)));
            mark.setText(Integer.toString(data.getInt(data.getColumnIndexOrThrow(StudentContract.StudentEntry.MARKS))));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.save:
                onSaveStudent();
                return true;
            case R.id.delete:
                onDelete();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSaveStudent(){
        EditText first = (EditText)findViewById(R.id.first_name_edit);
        EditText last = (EditText) findViewById(R.id.last_name_edit);
        EditText mark = (EditText) findViewById(R.id.marks);
        ContentValues values = new ContentValues();
        values.put(StudentContract.StudentEntry.COLUMN_FIRST_NAME,first.getText().toString());
        values.put(StudentContract.StudentEntry.COLUMN_LAST_NAME,last.getText().toString());
        values.put(StudentContract.StudentEntry.MARKS,mark.getText().toString());
       String selection = StudentContract.StudentEntry.COLUMN_ID + "=?";
        String [] selectionargs = new String[]{String.valueOf(ContentUris.parseId(uri))};
        getContentResolver().update(StudentContract.StudentEntry.CONTENT_URI,values, selection,selectionargs);
    }

    public void onDelete(){

    }
}
