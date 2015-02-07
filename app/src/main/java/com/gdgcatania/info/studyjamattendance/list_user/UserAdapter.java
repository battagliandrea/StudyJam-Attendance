package com.gdgcatania.info.studyjamattendance.list_user;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdgcatania.info.studyjamattendance.R;
import com.gdgcatania.info.studyjamattendance.utils.CursorRecyclerViewAdapter;

/**
 * Created by Andrea on 05/02/2015.
 */
public class UserAdapter extends CursorRecyclerViewAdapter<UserViewHolder> {

    private Context context;

    public UserAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.context=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        UserViewHolder viewHolder = new UserViewHolder(context, view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(UserViewHolder viewHolder, Cursor cursor) {

        String surname = cursor.getString(UserListFragment.COL_USERS_SURNAME);
        String name = cursor.getString(UserListFragment.COL_USERS_NAME);
        String email = cursor.getString(UserListFragment.COL_USERS_EMAIL);

        viewHolder.userName.setText(surname + " " + name);
        viewHolder.userMail.setText(email);
        viewHolder.imageUser.setImageResource(R.mipmap.ic_launcher);
    }

    /*@Override
    public int getItemCount() {*
        return items.size();
    }*/
}