package com.gdgcatania.info.studyjamattendance.api;

import android.net.Uri;
import android.util.Log;

import com.gdgcatania.info.studyjamattendance.object.Lesson;
import com.gdgcatania.info.studyjamattendance.object.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Andrea on 05/02/2015.
 */
public class StudyJamAttendanceAPI {

    private static String LOG_TAG = StudyJamAttendanceAPI.class.getSimpleName();

    private static final String JSON_DATA = "Data";
    private static final String JSON_USERS_ID = "id";
    private static final String JSON_USERS_SURNAME = "cognome";
    private static final String JSON_USERS_NAME = "nome";
    private static final String JSON_USERS_EMAIL = "email";

    private static final String JSON_LESSON_USER_ID = "id_s";
    private static final String JSON_LESSON_1 = "l1";
    private static final String JSON_LESSON_2 = "l2";
    private static final String JSON_LESSON_3 = "l3";
    private static final String JSON_LESSON_4 = "l4";
    private static final String JSON_LESSON_5 = "l5";
    private static final String JSON_LESSON_6 = "l6";
    private static final String JSON_LESSON_7 = "l7";
    private static final String JSON_LESSON_8 = "l8";

    private static ArrayList<User> newsArray;
    private static ArrayList<Lesson> lessonsArray;

    private static HttpURLConnection urlConnection;
    private static BufferedReader reader;
    private static String jsonStr;


    public static ArrayList<User> getUsersJson(){
        try {
            final String GDG_URL = "http://gdgcatania.info/api/getStudents";
            //final String QUERY_PARAM = "id";

            Uri builtUri = Uri.parse(GDG_URL).buildUpon()
                    //.appendQueryParameter(QUERY_PARAM, "1")
                    .build();

            URL url = new URL(builtUri.toString());
            Log.v(LOG_TAG, "Built URI " + builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
            }

            jsonStr = buffer.toString();
            Log.v(LOG_TAG, "JsonTest: " + jsonStr);

            try {
                newsArray = getNews(jsonStr); //il mio arrayList contenente il json
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return newsArray;
    }

    private static ArrayList<User> getNews(String jsonString) throws JSONException{

        //HO UN JSON ALL'INTERNO DI UN ALTRO JSON COSI' MI RESTITUISCE SEMPRE QUALCOSA
        JSONObject jsonObject = new JSONObject(jsonString);
        ArrayList<User> array = new ArrayList<User>();

        JSONArray jArr = jsonObject.getJSONArray(JSON_DATA);
        for (int i=0; i < jArr.length(); i++) {
            JSONObject jsonData = jArr.getJSONObject(i);
            int user_id = jsonData.getInt(JSON_USERS_ID);
            String surname = jsonData.getString(JSON_USERS_SURNAME);
            String name = jsonData.getString(JSON_USERS_NAME);
            String email = jsonData.getString(JSON_USERS_EMAIL);
            array.add(new User(user_id, surname, name, email));
        }

        return array;
    }

    public static ArrayList<Lesson> getLessonsJson(){
        try {
            final String GDG_URL = "http://gdgcatania.info/api/getStudentsPresences";
            //final String QUERY_PARAM = "id";

            Uri builtUri = Uri.parse(GDG_URL).buildUpon()
                    //.appendQueryParameter(QUERY_PARAM, "1")
                    .build();

            URL url = new URL(builtUri.toString());
            Log.v(LOG_TAG, "Built URI " + builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
            }

            jsonStr = buffer.toString();
            Log.v(LOG_TAG, "JsonTest: " + jsonStr);

            try {
                lessonsArray = getLessons(jsonStr); //il mio arrayList contenente il json
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return lessonsArray;
    }

    private static ArrayList<Lesson> getLessons(String jsonString) throws JSONException{

        //HO UN JSON ALL'INTERNO DI UN ALTRO JSON COSI' MI RESTITUISCE SEMPRE QUALCOSA
        JSONObject jsonObject = new JSONObject(jsonString);
        ArrayList<Lesson> array = new ArrayList<Lesson>();

        JSONArray jArr = jsonObject.getJSONArray(JSON_DATA);
        for (int i=0; i < jArr.length(); i++) {
            JSONObject jsonData = jArr.getJSONObject(i);
            int user_id = jsonData.getInt(JSON_LESSON_USER_ID);
            int lesson1 = jsonData.getInt(JSON_LESSON_1);
            int lesson2 = jsonData.getInt(JSON_LESSON_2);
            int lesson3 = jsonData.getInt(JSON_LESSON_3);
            int lesson4 = jsonData.getInt(JSON_LESSON_4);
            int lesson5 = jsonData.getInt(JSON_LESSON_5);
            int lesson6 = jsonData.getInt(JSON_LESSON_6);
            int lesson7 = jsonData.getInt(JSON_LESSON_7);
            int lesson8 = jsonData.getInt(JSON_LESSON_8);

            array.add(new Lesson(user_id, lesson1, lesson2, lesson3, lesson4, lesson5, lesson6, lesson7, lesson8));
        }

        return array;
    }

}