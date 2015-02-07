package com.gdgcatania.info.studyjamattendance.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gdgcatania.info.studyjamattendance.database.StudyJamAttendanceContract.UsersEntry;
import com.gdgcatania.info.studyjamattendance.database.StudyJamAttendanceContract.LessonsEntry;

/**
 * Created by Andrea on 05/02/2015.
 */
public class StudyJamAttendanceDbHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "studyjam.db";
    private SQLiteDatabase db;

    public StudyJamAttendanceDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + UsersEntry.TABLE_NAME + " (" +
                UsersEntry._ID + " INTEGER NOT NULL," +
                UsersEntry.COLUMN_SURNAME + " VARCHAR(256) NOT NULL," +
                UsersEntry.COLUMN_NAME + " VARCHAR(256) NOT NULL," +
                UsersEntry.COLUMN_EMAIL + " VARCHAR(256) NOT NULL);";

        final String SQL_CREATE_LESSONS_TABLE = "CREATE TABLE " + LessonsEntry.TABLE_NAME + " (" +
                LessonsEntry._ID + " INTEGER NOT NULL," +
                LessonsEntry.COLUMN_LESSON1 + " INTEGER NOT NULL," +
                LessonsEntry.COLUMN_LESSON2 + " INTEGER NOT NULL," +
                LessonsEntry.COLUMN_LESSON3 + " INTEGER NOT NULL," +
                LessonsEntry.COLUMN_LESSON4 + " INTEGER NOT NULL," +
                LessonsEntry.COLUMN_LESSON5 + " INTEGER NOT NULL," +
                LessonsEntry.COLUMN_LESSON6 + " INTEGER NOT NULL," +
                LessonsEntry.COLUMN_LESSON7 + " INTEGER NOT NULL," +
                LessonsEntry.COLUMN_LESSON8 + " INTEGER NOT NULL," +
                " FOREIGN KEY (" + LessonsEntry._ID + ") REFERENCES " +
                UsersEntry.TABLE_NAME + " (" + UsersEntry._ID + "));";

        db.execSQL(SQL_CREATE_USERS_TABLE);
        db.execSQL(SQL_CREATE_LESSONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UsersEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LessonsEntry.TABLE_NAME);
        onCreate(db);
    }
}
