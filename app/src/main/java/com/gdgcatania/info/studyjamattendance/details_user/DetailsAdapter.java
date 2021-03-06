package com.gdgcatania.info.studyjamattendance.details_user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.gdgcatania.info.studyjamattendance.R;

import java.util.ArrayList;

/**
 * Created by Andrea on 06/02/2015.
 */
public class DetailsAdapter extends RecyclerView.Adapter<DetailsViewHolder>{

    private Context context;
    private ArrayList<Integer> items;

    public DetailsAdapter(Context context, ArrayList<Integer> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public DetailsViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_details_lesson, parent, false);
        DetailsViewHolder viewHolder = new DetailsViewHolder(context, view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DetailsViewHolder detailsViewHolder, int position) {
        int attendance = items.get(position);

        detailsViewHolder.lessonName.setText("Lesson "+(position+1));
        if(attendance==1){
            detailsViewHolder.lessonAttendence.setText(R.string.details_attendance_presence);
            detailsViewHolder.imageLesson.setImageResource(R.mipmap.ic_action_attendance);
        }else{
            detailsViewHolder.lessonAttendence.setText(R.string.details_attendance_no_presence);
            detailsViewHolder.imageLesson.setImageResource(R.mipmap.ic_action_no_attendance);
        }



    }

    @Override
    public int getItemCount() {
            return items.size();
    }
}