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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.noone.studentenrolment.data.StudentContract;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private StudentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView)findViewById(R.id.list_view);
        adapter = new StudentAdapter(this,null);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,Details.class);
                    Uri uri = ContentUris.withAppendedId(StudentContract.StudentEntry.CONTENT_URI,id);
                intent.setData(uri);
                startActivity(intent);
            }
        });

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
        return new CursorLoader(this, StudentContract.StudentEntry.CONTENT_URI, projection, null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        switch(id){
            case R.id.new_student:
                insertStudent();
                return true;
            case R.id.delete_all:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void insertStudent(){
        ContentValues values = new ContentValues();
        values.put(StudentContract.StudentEntry.COLUMN_FIRST_NAME,"ravi tej");
        values.put(StudentContract.StudentEntry.COLUMN_LAST_NAME,"kallepalli");
        values.put(StudentContract.StudentEntry.MARKS,100);
      Uri uri = getContentResolver().insert(StudentContract.StudentEntry.CONTENT_URI,values);

    }
}
