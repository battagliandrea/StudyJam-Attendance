package com.gdgcatania.info.studyjamattendance.details_user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdgcatania.info.studyjamattendance.R;

/**
 * Created by Andrea on 06/02/2015.
 */
public class DetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private Context context;
    public TextView lessonName;
    public TextView lessonAttendence;
    public ImageView imageLesson;

    public DetailsViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        lessonName = (TextView) itemView.findViewById(R.id.item_details_lesson_textview);
        lessonAttendence = (TextView) itemView.findViewById(R.id.item_details_attendence_textview);
        imageLesson = (ImageView) itemView.findViewById(R.id.item_details_lesson_image);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
