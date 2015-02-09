package com.gdgcatania.info.studyjamattendance.details_user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdgcatania.info.studyjamattendance.R;
import com.gdgcatania.info.studyjamattendance.object.User;
import com.gdgcatania.info.studyjamattendance.service.StudyJamAttendanceService;
import com.gdgcatania.info.studyjamattendance.utils.Utils;

/**
 * Created by Andrea on 06/02/2015.
 */
public class DetailsViewHolder extends RecyclerView.ViewHolder{

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
    }

}
