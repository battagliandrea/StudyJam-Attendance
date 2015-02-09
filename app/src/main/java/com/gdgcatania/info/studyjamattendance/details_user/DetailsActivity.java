package com.gdgcatania.info.studyjamattendance.details_user;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.gdgcatania.info.studyjamattendance.R;
import com.gdgcatania.info.studyjamattendance.database.StudyJamAttendanceContract;
import com.gdgcatania.info.studyjamattendance.object.Lesson;
import com.gdgcatania.info.studyjamattendance.object.User;
import com.gdgcatania.info.studyjamattendance.utils.Utils;


public class DetailsActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int USER_DETAILS_LOADER = 0;
    private static final String[] USER_COLUMNS = {
            StudyJamAttendanceContract.UsersEntry.TABLE_NAME + "." + StudyJamAttendanceContract.UsersEntry._ID,
            StudyJamAttendanceContract.UsersEntry.COLUMN_SURNAME,
            StudyJamAttendanceContract.UsersEntry.COLUMN_NAME,
            StudyJamAttendanceContract.UsersEntry.COLUMN_EMAIL
    };

    private Toolbar toolbar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getLoaderManager().initLoader(USER_DETAILS_LOADER, null, this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_details);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container_details, new DetailsFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra(Utils.INTENT_USER_ID)) {
            return null;
        }
        int extraUserId = intent.getIntExtra(Utils.INTENT_USER_ID, -1);

        switch(id){
            case USER_DETAILS_LOADER:
                Uri userUri = StudyJamAttendanceContract.UsersEntry.buildUsersUriWithId(String.valueOf(extraUserId));
                return new CursorLoader(this,userUri,USER_COLUMNS,null,null,null);

            default:
                return null;
        }
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()){

            case USER_DETAILS_LOADER:
                if (!data.moveToFirst()) { return; }

                User user = new User(
                        data.getInt(data.getColumnIndex(StudyJamAttendanceContract.UsersEntry._ID)),
                        data.getString(data.getColumnIndex(StudyJamAttendanceContract.UsersEntry.COLUMN_SURNAME)),
                        data.getString(data.getColumnIndex(StudyJamAttendanceContract.UsersEntry.COLUMN_NAME)),
                        data.getString(data.getColumnIndex(StudyJamAttendanceContract.UsersEntry.COLUMN_EMAIL))
                );

                toolbar.setTitle(user.getId() + ". " + user.getSurname() + " " + user.getName());
                data.close();

                break;
            default:
                Log.v("ERRORE", "Nessun Loader");
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
