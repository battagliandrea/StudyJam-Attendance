package com.gdgcatania.info.studyjamattendance;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gdgcatania.info.studyjamattendance.list_user.UserListFragment;
import com.gdgcatania.info.studyjamattendance.object.User;
import com.gdgcatania.info.studyjamattendance.service.StudyJamAttendanceService;
import com.gdgcatania.info.studyjamattendance.utils.Utils;

public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private StudyJamAttendanceReceiverUsers usersReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }


        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container_main, new UserListFragment())
                    .commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.container_main);
        fragment.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_attendence) {

            String [] lessonKey =  getResources().getStringArray(R.array.lessons_keys);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setItems(lessonKey, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    //FACCIO PARTIRE UN SECONDO SERVIZIO PER FARE IL POST E L'UPDATE NEL DATABASE
                    Intent serviceIntent = new Intent(getApplicationContext(), StudyJamAttendanceService.class);
                    serviceIntent.putExtra(Utils.SERVICE_KEY, Utils.SERVICE_ANALYTICS);
                    serviceIntent.putExtra(Utils.INTENT_ANALYTICS_L_ID, (which+1));
                    startService(serviceIntent);
                }
            });
            builder.create();
            builder.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        IntentFilter filter =new IntentFilter(Utils.BROADCAST_RECEIVER_ANALYTICS);
        usersReceiver = new StudyJamAttendanceReceiverUsers();
        registerReceiver(usersReceiver, filter);
        super.onResume();
    }

    @Override
    public void onPause() {
        unregisterReceiver(usersReceiver);
        super.onPause();
    }

    public class StudyJamAttendanceReceiverUsers extends BroadcastReceiver {

        private String LOG_TAG = StudyJamAttendanceReceiverUsers.class.getSimpleName();

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.v(LOG_TAG, "StudyJamAttendance Users_Received ");

            Bundle extras = intent.getExtras();
            if (extras != null) {
                int count  = extras.getInt(Utils.COUNT);
                int lesson_id  = extras.getInt(Utils.LESSON_ID);

                if(count == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(R.string.details_dialog_message3);
                    builder.create();
                    builder.show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(getResources().getString(R.string.details_dialog_message2) + " " + String.valueOf(lesson_id)+ ": " + String.valueOf(count))
                            .setNeutralButton(R.string.details_dialog_neutral,new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    builder.create();
                    builder.show();
                }


            } else {
                Log.v(LOG_TAG, "no extras");
            }
        }
    }

}
