package com.gdgcatania.info.studyjamattendance.service;

import android.app.IntentService;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.gdgcatania.info.studyjamattendance.api.StudyJamAttendanceAPI;
import com.gdgcatania.info.studyjamattendance.object.Lesson;
import com.gdgcatania.info.studyjamattendance.object.User;

import java.util.ArrayList;

import com.gdgcatania.info.studyjamattendance.database.StudyJamAttendanceContract.UsersEntry;
import com.gdgcatania.info.studyjamattendance.database.StudyJamAttendanceContract.LessonsEntry;

/**
 * Created by Andrea on 05/02/2015.
 */
public class StudyJamAttendanceService extends IntentService {

    private String LOG_TAG = StudyJamAttendanceService.class.getSimpleName();

    ArrayList<User> JSonUsersArray;
    ArrayList<Lesson> JSonLessonsArray;

    public StudyJamAttendanceService() {
        super("StudyJamAttendanceService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        startUsersService();
        startLessonsService();

    }

    public void startUsersService(){

        JSonUsersArray = StudyJamAttendanceAPI.getUsersJson();
        Log.v(LOG_TAG, "SERVICE: Start Users Service!! :)");

        for (User users : JSonUsersArray){
            //INSERISCE DATI ALL'INTERNO DEL DATABASE
            addUsers(users.getId(), users.getSurname(), users.getName(),users.getEmail());
        }

        Log.v(LOG_TAG, " INSERT USERS OK");

        /*Intent intentBroadcast = new Intent();
        intentBroadcast.setAction(Utils.BROADCAST_RECEIVER_ACTION_USERS);
        sendBroadcast(intentBroadcast);*/
    }

    public void startLessonsService(){


        ArrayList<Lesson> JSonLessonsArray = StudyJamAttendanceAPI.getLessonsJson();
        Log.v(LOG_TAG, "SERVICE: Start Lessons Service!! :)");

        for (Lesson lessons : JSonLessonsArray){
            //INSERISCE DATI ALL'INTERNO DEL DATABASE
            addLessons(lessons.getUserId(),
                    lessons.getLesson1(),
                    lessons.getLesson2(),
                    lessons.getLesson3(),
                    lessons.getLesson4(),
                    lessons.getLesson5(),
                    lessons.getLesson6(),
                    lessons.getLesson7(),
                    lessons.getLesson8());
        }
        Log.v(LOG_TAG, " INSERT LESSON OK");
    }

    private long addUsers(int user_id, String surname, String name, String email){

        Log.v(LOG_TAG, "INSERT USERS " + user_id +","+ surname +", "+ name +", "+ email);

        //verificare se esiste la posizione nel database
        Cursor cursor = getApplicationContext().getContentResolver().query(
                UsersEntry.CONTENT_URI,
                new String[]{UsersEntry._ID},
                UsersEntry.COLUMN_EMAIL + "=?",
                new String[]{email},
                null
        );

        if(cursor.moveToFirst()){
            Log.v(LOG_TAG, "Found it in the database!");
            int locationIdIndex = cursor.getColumnIndex(UsersEntry._ID);
            return cursor.getLong(locationIdIndex);
        }else{
            Log.v(LOG_TAG, "Didn't find it in the database, inserting now!");
            ContentValues usersValues = new ContentValues();
            usersValues.put(UsersEntry._ID, user_id);
            usersValues.put(UsersEntry.COLUMN_SURNAME, surname);
            usersValues.put(UsersEntry.COLUMN_NAME, name);
            usersValues.put(UsersEntry.COLUMN_EMAIL, email);

            Uri usersInsertUri = getApplicationContext().getContentResolver()
                    .insert(UsersEntry.CONTENT_URI, usersValues);
            return ContentUris.parseId(usersInsertUri);
        }
    }

    private long addLessons(int user_id, int lesson1, int lesson2, int lesson3, int lesson4, int lesson5, int lesson6, int lesson7, int lesson8){

        Log.v(LOG_TAG, "INSERT USERS " + user_id +","+ lesson1 +","+ lesson2 +","+ lesson3 +","+ lesson4 +","+ lesson4 +","+ lesson5 +","+ lesson6 +","+ lesson7 +","+ lesson8);

        //verificare se esiste la posizione nel database
        Cursor cursor = getApplicationContext().getContentResolver().query(
                LessonsEntry.CONTENT_URI,
                new String[]{LessonsEntry._ID},
                LessonsEntry._ID + "=?",
                new String[]{user_id+""},
                null
        );

        if(cursor.moveToFirst()){
            Log.v(LOG_TAG, "Found it in the database!");
            int locationIdIndex = cursor.getColumnIndex(LessonsEntry._ID);
            return cursor.getLong(locationIdIndex);
        }else{
            Log.v(LOG_TAG, "Didn't find it in the database, inserting now!");
            ContentValues lessonsValues = new ContentValues();
            lessonsValues.put(LessonsEntry._ID, user_id);
            lessonsValues.put(LessonsEntry.COLUMN_LESSON1, lesson1);
            lessonsValues.put(LessonsEntry.COLUMN_LESSON2, lesson2);
            lessonsValues.put(LessonsEntry.COLUMN_LESSON3, lesson3);
            lessonsValues.put(LessonsEntry.COLUMN_LESSON4, lesson4);
            lessonsValues.put(LessonsEntry.COLUMN_LESSON5, lesson5);
            lessonsValues.put(LessonsEntry.COLUMN_LESSON6, lesson6);
            lessonsValues.put(LessonsEntry.COLUMN_LESSON7, lesson7);
            lessonsValues.put(LessonsEntry.COLUMN_LESSON8, lesson8);


            Uri lessonsInsertUri = getApplicationContext().getContentResolver()
                    .insert(LessonsEntry.CONTENT_URI, lessonsValues);
            return ContentUris.parseId(lessonsInsertUri);
        }
    }

}

