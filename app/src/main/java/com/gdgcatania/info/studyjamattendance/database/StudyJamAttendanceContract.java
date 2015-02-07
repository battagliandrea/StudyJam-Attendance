package com.gdgcatania.info.studyjamattendance.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Andrea on 05/02/2015.
 */
public class StudyJamAttendanceContract {

    public static final String CONTENT_AUTHORITY ="com.gdgcatania.info.studyjamattendence.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_USERS = "users";
    public static final String PATH_LESSONS = "lessons";

    public static final class UsersEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USERS).build();

        public static final String CONTENT_TYPE ="vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_USERS;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_USERS;

        public static final String TABLE_NAME = "users";

        public static final String COLUMN_SURNAME = "surname";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";


        public static Uri buildUsersUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildUsersUriWithId(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

    }

    public static final class LessonsEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_LESSONS).build();

        public static final String CONTENT_TYPE ="vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_LESSONS;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_LESSONS;

        public static final String TABLE_NAME = "lessons";

        public static final String COLUMN_LESSON1 = "lesson_one";
        public static final String COLUMN_LESSON2 = "lesson_two";
        public static final String COLUMN_LESSON3 = "lesson_three";
        public static final String COLUMN_LESSON4 = "lesson_four";
        public static final String COLUMN_LESSON5 = "lesson_five";
        public static final String COLUMN_LESSON6 = "lesson_six";
        public static final String COLUMN_LESSON7 = "lesson_seven";
        public static final String COLUMN_LESSON8 = "lesson_eigth";

        public static Uri buildLessonsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildLessonsUriWithId(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }


    }

}
