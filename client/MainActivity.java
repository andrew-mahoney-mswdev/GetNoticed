package com.example.mahoneandr.getnoticedapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String STATE_MSGLOG = "msgLog";
    private static boolean appPaused = false;

    static NotificationCompat.Builder notification;
    private static final int uniqueID = (int)Math.random() * 10000;
    GetTCPThread getTCP;
    Thread getTCPThread;

    MessageLog messageLog;

    private ScrollView scrView;
    private EditText userMsg;
    private Button btnPost;

    public static boolean isPaused() {return appPaused;}

    public static void scrollDown(final ScrollView scrView) {
        scrView.post(new Runnable() {
            @Override
            public void run() {
                scrView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d("TEST", "onCreate()");

//        UNCOMMENT TO DELETE SHARED PREFERENCES
//        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
//        prefs.edit().remove(STATE_MSGLOG).commit();

//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE); //Should resize, but doesn't.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); //Pans up for soft input, pushing first text above screen.
        setContentView(R.layout.activity_main);

        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        scrView = (ScrollView)findViewById(R.id.scrView);
        userMsg = (EditText)findViewById(R.id.userMsg);
        btnPost = (Button)findViewById(R.id.btnPost);
        messageLog = new MessageLog((TextView)findViewById(R.id.msgLog), scrView);

        getTCP = new GetTCPThread(messageLog, MainActivity.this, notification, uniqueID);
        getTCPThread = new Thread(getTCP);
        getTCPThread.start();

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = userMsg.getText().toString();
                messageLog.add(message);
                userMsg.setText("");

                SendTCP sendtcp = new SendTCP();
                sendtcp.execute(message);
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
//        Log.d("TEST", "onRestore()");

        messageLog.init(inState.getCharSequence(STATE_MSGLOG));
    }

    @Override
    protected void onResume() {
        super.onResume();
        appPaused = false;
//        Log.d("TEST", "onResume()");

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        if (messageLog.getText().equals("")) {
            messageLog.init(prefs.getString(STATE_MSGLOG, ""));
        }

        messageLog.apply();
        scrollDown(scrView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        appPaused = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        Log.d("TEST", "onSave()");

        outState.putCharSequence(STATE_MSGLOG, messageLog.getText());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Log.d("TEST", "onBackPressed()");

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(STATE_MSGLOG, messageLog.getString());
        editor.apply();
    }

}
