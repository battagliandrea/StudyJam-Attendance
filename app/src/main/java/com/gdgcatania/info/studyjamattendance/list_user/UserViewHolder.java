package com.gdgcatania.info.studyjamattendance.list_user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdgcatania.info.studyjamattendance.R;
import com.gdgcatania.info.studyjamattendance.utils.Utils;
import com.gdgcatania.info.studyjamattendance.details_user.DetailsActivity;

/**
 * Created by Andrea on 05/02/2015.
 */
public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private Context context;
    public TextView userName;
    public TextView userMail;
    public ImageView imageUser;

    public int user_id;


    public UserViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        userName = (TextView) itemView.findViewById(R.id.item_user_name_textview);
        userMail = (TextView) itemView.findViewById(R.id.item_user_mail_textview);
        imageUser = (ImageView) itemView.findViewById(R.id.item_user_image);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(context, userName.getText().toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(Utils.INTENT_USER_ID, user_id);
        context.startActivity(intent);
    }
}