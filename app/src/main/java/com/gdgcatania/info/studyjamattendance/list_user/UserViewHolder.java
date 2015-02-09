package com.gdgcatania.info.studyjamattendance.list_user;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdgcatania.info.studyjamattendance.R;
import com.gdgcatania.info.studyjamattendance.database.StudyJamAttendanceContract;
import com.gdgcatania.info.studyjamattendance.object.User;
import com.gdgcatania.info.studyjamattendance.service.StudyJamAttendanceService;
import com.gdgcatania.info.studyjamattendance.utils.Utils;
import com.gdgcatania.info.studyjamattendance.details_user.DetailsActivity;

/**
 * Created by Andrea on 05/02/2015.
 */
public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    private Context context;
    public TextView userName;
    public TextView userMail;
    public ImageView imageUser;

    public int user_id;
    private String [] lessonKey;


    public UserViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        userName = (TextView) itemView.findViewById(R.id.item_user_name_textview);
        userMail = (TextView) itemView.findViewById(R.id.item_user_mail_textview);
        imageUser = (ImageView) itemView.findViewById(R.id.item_user_image);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(context, userName.getText().toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(Utils.INTENT_USER_ID, user_id);
        context.startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v) {

        lessonKey =  context.getResources().getStringArray(R.array.lessons_keys);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.details_dialog_message)
                .setPositiveButton(R.string.details_dialog_positive, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // SAVE!

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setItems(lessonKey, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                User user = getUserFromId(String.valueOf(user_id));
                                Toast.makeText(context, user.getSurname() + " " + user.getName() + " --> Presente alla lezione n°:  " + (which + 1), Toast.LENGTH_SHORT).show();

                                //FACCIO PARTIRE UN SECONDO SERVIZIO PER FARE IL POST E L'UPDATE NEL DATABASE
                                Intent serviceIntent = new Intent(context, StudyJamAttendanceService.class);
                                serviceIntent.putExtra(Utils.SERVICE_KEY, Utils.SERVICE_POST);
                                serviceIntent.putExtra(Utils.INTENT_POST_ID, user.getId());
                                serviceIntent.putExtra(Utils.INTENT_POST_L_ID, (which+1));
                                context.startService(serviceIntent);
                            }
                        });
                        builder.create();
                        builder.show();

                    }
                })
                .setNegativeButton(R.string.details_dialog_negative, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // CANCELL
                    }
                });
        builder.create();
        builder.show();

        return true;
    }

    private User getUserFromId(String id){

        ContentResolver cr = context.getContentResolver();
        Uri userUri = StudyJamAttendanceContract.UsersEntry.buildUsersUriWithId(String.valueOf(id));
        final String[] USER_COLUMNS = {
                StudyJamAttendanceContract.UsersEntry.TABLE_NAME + "." + StudyJamAttendanceContract.UsersEntry._ID,
                StudyJamAttendanceContract.UsersEntry.COLUMN_SURNAME,
                StudyJamAttendanceContract.UsersEntry.COLUMN_NAME,
                StudyJamAttendanceContract.UsersEntry.COLUMN_EMAIL
        };

        Cursor cursor = cr.query(userUri,USER_COLUMNS,null,null,null);

        if (!cursor.moveToFirst()) { return null; }

        User user = new User(
                cursor.getInt(cursor.getColumnIndex(StudyJamAttendanceContract.UsersEntry._ID)),
                cursor.getString(cursor.getColumnIndex(StudyJamAttendanceContract.UsersEntry.COLUMN_SURNAME)),
                cursor.getString(cursor.getColumnIndex(StudyJamAttendanceContract.UsersEntry.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(StudyJamAttendanceContract.UsersEntry.COLUMN_EMAIL))
        );
        return user;
    }
}