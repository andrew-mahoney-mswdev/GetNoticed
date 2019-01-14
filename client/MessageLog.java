package com.example.mahoneandr.getnoticedapp;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.concurrent.locks.Lock;

public class MessageLog {
    /** This class manages the MessageLog TextView, which displays all previous messages sent by the user.
      */

    private ScrollView scrView;
    private TextView msgLog; //The text view.
    private CharSequence message; //The text within the text view.
    //Note that OnRestoreInstanceState is not called when app is loaded by user, therefore the message variable must loaded and stored
    //separately from the textView text.

    public MessageLog(TextView _msgLog, ScrollView _scrView){
        msgLog = _msgLog;
        scrView = _scrView;
        message = "";
    }

    public void init(CharSequence _message) {
        //Loads all previous messages sent from either the buffer or shared preferences.
        message = _message;
    }

    public void apply() {
        //Displays previous messages. This is called in OnResume.
        msgLog.setText(message);
    }

    public void add(CharSequence newMessage) {
        //Adds a message with a time stamp.
        if (!message.equals("")) {//Necessary for correct display if there are no messages yet.
            message = message + "\n\n";
        }

        message = message + Calendar.getInstance().getTime().toString() + ":\n" + newMessage;

        msgLog.setText(message);
        MainActivity.scrollDown(scrView);
    }

    public CharSequence getText() { //Used for loading text into buffer.
        return message;
    }

    public String getString() { //Used for loading text into shared preferences.
        return message.toString();
    }
}
