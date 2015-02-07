package com.gdgcatania.info.studyjamattendance.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.gdgcatania.info.studyjamattendance.database.StudyJamAttendanceContract.UsersEntry;
import com.gdgcatania.info.studyjamattendance.database.StudyJamAttendanceContract.LessonsEntry;

/**
 * Created by Andrea on 05/02/2015.
 */
public class StudyJamAttendanceContentProvider extends ContentProvider{

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private StudyJamAttendanceDbHelper mOpenHelper;

    private static final int USER = 100;
    private static final int USER_ID = 101;
    private static final int LESSON = 200;
    private static final int LESSON_ID = 201;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = StudyJamAttendanceContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, StudyJamAttendanceContract.PATH_USERS, USER);
        matcher.addURI(authority, StudyJamAttendanceContract.PATH_USERS+ "/#", USER_ID);
        matcher.addURI(authority, StudyJamAttendanceContract.PATH_LESSONS, LESSON);
        matcher.addURI(authority, StudyJamAttendanceContract.PATH_LESSONS+ "/#", LESSON_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new StudyJamAttendanceDbHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case USER:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        UsersEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case USER_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        UsersEntry.TABLE_NAME,
                        projection,
                        UsersEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case LESSON: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        LessonsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case LESSON_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        LessonsEntry.TABLE_NAME,
                        projection,
                        LessonsEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case USER:
                return UsersEntry.CONTENT_TYPE;
            case USER_ID:
                return UsersEntry.CONTENT_ITEM_TYPE;
            case LESSON:
                return LessonsEntry.CONTENT_TYPE;
            case LESSON_ID:
                return LessonsEntry.CONTENT_ITEM_TYPE;
            default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case USER: {
                long _id = db.insert(UsersEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = UsersEntry.buildUsersUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case LESSON: {
                long _id = db.insert(LessonsEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = LessonsEntry.buildLessonsUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case USER:
                rowsDeleted = db.delete(UsersEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case LESSON:
                rowsDeleted = db.delete(LessonsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case USER:
                rowsUpdated = db.update(UsersEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case LESSON:
                rowsUpdated = db.update(LessonsEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}

