package com.gdgcatania.info.studyjamattendance.details_user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdgcatania.info.studyjamattendance.R;
import com.gdgcatania.info.studyjamattendance.database.StudyJamAttendanceContract.LessonsEntry;
import com.gdgcatania.info.studyjamattendance.list_user.UserAdapter;
import com.gdgcatania.info.studyjamattendance.object.Lesson;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

/**
 * Created by Andrea on 05/02/2015.
 */
public class DetailsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Integer> lessons;
    private DetailsAdapter detailsAdapter;

    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View detailsView = inflater.inflate(R.layout.fragment_details, container, false);

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
}
