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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdgcatania.info.studyjamattendance.R;
import com.gdgcatania.info.studyjamattendance.database.StudyJamAttendanceContract.UsersEntry;
import com.gdgcatania.info.studyjamattendance.utils.Utils;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

/**
 * Created by Andrea on 05/02/2015.
 */
public class DetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private RecyclerView recyclerView;
    private ArrayList<Integer> lessons;
    private DetailsAdapter detailsAdapter;

    private static final int USER_DETAILS_LOADER = 0;

    private static final String[] USER_COLUMNS = {
            UsersEntry.TABLE_NAME + "." + UsersEntry._ID,
            UsersEntry.COLUMN_SURNAME,
            UsersEntry.COLUMN_NAME,
            UsersEntry.COLUMN_EMAIL,
    };

    private int user_id;
    private String userSurname;
    private String userName;
    private String emailUser;

    private TextView userNameTv;
    private TextView userEmailTv;

    public DetailsFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(USER_DETAILS_LOADER, null, this);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View detailsView = inflater.inflate(R.layout.fragment_details, container, false);

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            emailUser = bundle.getString(Utils.INTENT_USER_ID);
        }

        createAndPopulateLessonsArray();

        detailsAdapter = new DetailsAdapter(getActivity(), lessons);
        recyclerView = (RecyclerView) detailsView.findViewById(R.id.fragment_details_list_lessons);
        recyclerView.setAdapter(detailsAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FloatingActionButton fab = (FloatingActionButton) detailsView.findViewById(R.id.fragment_details_add_lesson_button);
        fab.attachToRecyclerView(recyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0);*/
            }
        });

        return detailsView;
    }


    private void createAndPopulateLessonsArray() {
        lessons = new ArrayList<Integer>();
        lessons.add(1);
        lessons.add(1);
        lessons.add(0);
        lessons.add(0);
        lessons.add(0);
        lessons.add(0);
        lessons.add(0);
        lessons.add(0);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Intent intent = getActivity().getIntent();
        if (intent == null || !intent.hasExtra(Utils.INTENT_USER_ID)) {
            return null;
        }

        user_id = intent.getIntExtra(Utils.INTENT_USER_ID, -1);
        Uri userUri = UsersEntry.buildUsersUriWithId(String.valueOf(user_id));

        return new CursorLoader(getActivity(),userUri,USER_COLUMNS,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (!data.moveToFirst()) { return; }

        user_id = data.getInt(data.getColumnIndex(UsersEntry._ID));
        userSurname = data.getString(data.getColumnIndex(UsersEntry.COLUMN_SURNAME));
        userName = data.getString(data.getColumnIndex(UsersEntry.COLUMN_NAME));
        emailUser = data.getString(data.getColumnIndex(UsersEntry.COLUMN_EMAIL));

        userNameTv = (TextView) getView().findViewById(R.id.fragment_detail_name_textview);
        userEmailTv = (TextView) getView().findViewById(R.id.fragment_details_email_textview);

        userNameTv.setText(user_id + ". " + userSurname + " " + userName);
        userEmailTv.setText(emailUser);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
