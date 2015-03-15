package com.gdgcatania.info.studyjamattendance.list_user;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gdgcatania.info.studyjamattendance.R;
import com.gdgcatania.info.studyjamattendance.object.User;
import com.gdgcatania.info.studyjamattendance.utils.Utils;
import com.gdgcatania.info.studyjamattendance.service.StudyJamAttendanceService;
import com.melnykov.fab.FloatingActionButton;

import com.gdgcatania.info.studyjamattendance.database.StudyJamAttendanceContract.UsersEntry;

/**
 * Created by Andrea on 05/02/2015.
 */
public class UserListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int USERS_LOADER = 0;

    private static final String[] USERS_COLUMNS = {
            UsersEntry.TABLE_NAME + "." + UsersEntry._ID,
            UsersEntry.COLUMN_SURNAME,
            UsersEntry.COLUMN_NAME,
            UsersEntry.COLUMN_EMAIL
    };

    public static final int COL_USERS_ID = 0;
    public static final int COL_USERS_SURNAME = 1;
    public static final int COL_USERS_NAME = 2;
    public static final int COL_USERS_EMAIL = 3;


    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    private String [] lessonKey;
    private String contentsQR;



    public UserListFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(USERS_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        lessonKey =  this.getResources().getStringArray(R.array.lessons_keys);

        userAdapter = new UserAdapter(getActivity(), null);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_main_list_user);
        recyclerView.setAdapter(userAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fragment_main_qr_button);
        fab.attachToRecyclerView(recyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0);
            }
        });
        return rootView;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == getActivity().RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Log.i("Barcode Result", contents);
                contentsQR = contents;
                int userID=Integer.parseInt(contentsQR);
                Log.i("SJ Registro","QR letto: " +userID);
                if(userID > 0 && userID<=40){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setItems(lessonKey, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            User user = getUserFromQrCode(contentsQR);
                            Toast.makeText(getActivity(), user.getSurname() + " " + user.getName() + " --> Presente alla lezione n°:  " + (which+1) , Toast.LENGTH_SHORT).show();

                            //FACCIO PARTIRE UN SECONDO SERVIZIO PER FARE IL POST E L'UPDATE NEL DATABASE
                            Intent serviceIntent = new Intent(getActivity(), StudyJamAttendanceService.class);
                            serviceIntent.putExtra(Utils.SERVICE_KEY, Utils.SERVICE_POST);
                            serviceIntent.putExtra(Utils.INTENT_POST_ID, user.getId());
                            serviceIntent.putExtra(Utils.INTENT_POST_L_ID, (which+1));
                            getActivity().startService(serviceIntent);
                        }
                    });
                    builder.create();
                    builder.show();
                }else{
                    Toast.makeText(getActivity(), "QR Code non valido!!" , Toast.LENGTH_SHORT).show();
                }



                // Handle successful scan
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // Handle cancel
                Log.i("Barcode Result","Result canceled");
            }
        }
    }


    private User getUserFromQrCode(String id){

        ContentResolver cr = getActivity().getContentResolver();
        Uri userUri = UsersEntry.buildUsersUriWithId(String.valueOf(id));
        final String[] USER_COLUMNS = {
                UsersEntry.TABLE_NAME + "." + UsersEntry._ID,
                UsersEntry.COLUMN_SURNAME,
                UsersEntry.COLUMN_NAME,
                UsersEntry.COLUMN_EMAIL
        };

        Cursor cursor = cr.query(userUri,USER_COLUMNS,null,null,null);

        if (!cursor.moveToFirst()) { return null; }

        User user = new User(
                cursor.getInt(cursor.getColumnIndex(UsersEntry._ID)),
                cursor.getString(cursor.getColumnIndex(UsersEntry.COLUMN_SURNAME)),
                cursor.getString(cursor.getColumnIndex(UsersEntry.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(UsersEntry.COLUMN_EMAIL))
        );
        return user;
    }


    @Override
    public void onAttach(Activity activity) {
        Intent serviceIntent = new Intent(getActivity(), StudyJamAttendanceService.class);
        serviceIntent.putExtra(Utils.SERVICE_KEY,Utils.SERVICE_USERS);
        getActivity().startService(serviceIntent);
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder = UsersEntry._ID + " ASC";
        Uri usersUri = UsersEntry.CONTENT_URI;

        return new CursorLoader(
                getActivity(),
                usersUri,
                USERS_COLUMNS,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        userAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        userAdapter.swapCursor(null);
    }


}
