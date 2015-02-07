package com.gdgcatania.info.studyjamattendance.list_user;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
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

import com.gdgcatania.info.studyjamattendance.R;
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

    private StudyJamAttendanceReceiverUsers usersReceiver;

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

    @Override
    public void onAttach(Activity activity) {
        Intent serviceIntent = new Intent(getActivity(), StudyJamAttendanceService.class);
        serviceIntent.putExtra(Utils.SERVICE_KEY,Utils.SERVICE_USERS);
        getActivity().startService(serviceIntent);
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        IntentFilter filter =new IntentFilter(Utils.BROADCAST_RECEIVER_ACTION_USERS);
        usersReceiver = new StudyJamAttendanceReceiverUsers();
        getActivity().registerReceiver(usersReceiver, filter);
        super.onResume();
    }

    @Override
    public void onPause() {
        getActivity().unregisterReceiver(usersReceiver);
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

    public class StudyJamAttendanceReceiverUsers extends BroadcastReceiver {

        private String LOG_TAG = StudyJamAttendanceReceiverUsers.class.getSimpleName();

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.v(LOG_TAG, "StudyJamAttendance Users_Received ");

           /* Bundle extras = intent.getExtras();
            if (extras != null) {
                ArrayList<News> newsArray = extras.getParcelableArrayList(Utils.BROADCAST_RECEIVER_KEY);
                if(newsArray != null) {
                    newsMessage = newsArray;
                }
            } else {
                Log.v(LOG_TAG, "no extras");
            }*/
        }
    }

}
