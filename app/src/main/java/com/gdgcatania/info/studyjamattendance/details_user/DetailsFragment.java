package com.gdgcatania.info.studyjamattendance.details_user;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdgcatania.info.studyjamattendance.R;
import com.gdgcatania.info.studyjamattendance.database.StudyJamAttendanceContract.UsersEntry;
import com.gdgcatania.info.studyjamattendance.database.StudyJamAttendanceContract.LessonsEntry;
import com.gdgcatania.info.studyjamattendance.object.Lesson;
import com.gdgcatania.info.studyjamattendance.object.User;
import com.gdgcatania.info.studyjamattendance.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Andrea on 05/02/2015.
 */
public class DetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private RecyclerView recyclerView;
    private ArrayList<Integer> lessons;
    private DetailsAdapter detailsAdapter;

    private static final int USER_DETAILS_LOADER = 0;
    private static final int LESSON_DETAILS_LOADER = 1;

    private static final String[] USER_COLUMNS = {
            UsersEntry.TABLE_NAME + "." + UsersEntry._ID,
            UsersEntry.COLUMN_SURNAME,
            UsersEntry.COLUMN_NAME,
            UsersEntry.COLUMN_EMAIL
    };

    private static final String[] LESSONS_COLUMNS ={
            LessonsEntry.TABLE_NAME + "." + UsersEntry._ID,
            LessonsEntry.COLUMN_LESSON1,
            LessonsEntry.COLUMN_LESSON2,
            LessonsEntry.COLUMN_LESSON3,
            LessonsEntry.COLUMN_LESSON4,
            LessonsEntry.COLUMN_LESSON5,
            LessonsEntry.COLUMN_LESSON6,
            LessonsEntry.COLUMN_LESSON7,
            LessonsEntry.COLUMN_LESSON8
    };

    private TextView userNameTv;
    private TextView userEmailTv;

    public DetailsFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(USER_DETAILS_LOADER, null, this);
        getLoaderManager().initLoader(LESSON_DETAILS_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View detailsView = inflater.inflate(R.layout.fragment_details, container, false);

        lessons = new ArrayList<Integer>();
        detailsAdapter = new DetailsAdapter(getActivity(), lessons);
        recyclerView = (RecyclerView) detailsView.findViewById(R.id.fragment_details_list_lessons);
        recyclerView.setAdapter(detailsAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return detailsView;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Intent intent = getActivity().getIntent();
        if (intent == null || !intent.hasExtra(Utils.INTENT_USER_ID)) {
            return null;
        }
        int extraUserId = intent.getIntExtra(Utils.INTENT_USER_ID, -1);


       switch(id){

           case USER_DETAILS_LOADER:
               Uri userUri = UsersEntry.buildUsersUriWithId(String.valueOf(extraUserId));
              return new CursorLoader(getActivity(),userUri,USER_COLUMNS,null,null,null);

           case LESSON_DETAILS_LOADER:
               Uri lessonUri = LessonsEntry.buildLessonsUriWithId(String.valueOf(extraUserId));
               return new CursorLoader(getActivity(),lessonUri,LESSONS_COLUMNS,null,null,null);

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
                        data.getInt(data.getColumnIndex(UsersEntry._ID)),
                        data.getString(data.getColumnIndex(UsersEntry.COLUMN_SURNAME)),
                        data.getString(data.getColumnIndex(UsersEntry.COLUMN_NAME)),
                        data.getString(data.getColumnIndex(UsersEntry.COLUMN_EMAIL))
                );

                userEmailTv = (TextView) getView().findViewById(R.id.fragment_details_email_textview);
                userEmailTv.setText(user.getEmail());
                data.close();

                break;

            case LESSON_DETAILS_LOADER:
                if (!data.moveToFirst()) { return; }

                Lesson lesson = new Lesson(
                        data.getInt(data.getColumnIndex(LessonsEntry.COLUMN_LESSON1)),
                        data.getInt(data.getColumnIndex(LessonsEntry.COLUMN_LESSON2)),
                        data.getInt(data.getColumnIndex(LessonsEntry.COLUMN_LESSON3)),
                        data.getInt(data.getColumnIndex(LessonsEntry.COLUMN_LESSON4)),
                        data.getInt(data.getColumnIndex(LessonsEntry.COLUMN_LESSON5)),
                        data.getInt(data.getColumnIndex(LessonsEntry.COLUMN_LESSON6)),
                        data.getInt(data.getColumnIndex(LessonsEntry.COLUMN_LESSON7)),
                        data.getInt(data.getColumnIndex(LessonsEntry.COLUMN_LESSON8))
                );


                lessons.add(lesson.getLesson1());
                lessons.add(lesson.getLesson2());
                lessons.add(lesson.getLesson3());
                lessons.add(lesson.getLesson4());
                lessons.add(lesson.getLesson5());
                lessons.add(lesson.getLesson6());
                lessons.add(lesson.getLesson7());
                lessons.add(lesson.getLesson8());

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
