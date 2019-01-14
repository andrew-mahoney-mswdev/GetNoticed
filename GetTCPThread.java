package com.example.mahoneandr.getnoticedapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static android.content.Context.NOTIFICATION_SERVICE;

public class GetTCPThread implements Runnable {
    private Socket socket;
    private ServerSocket serverSocket;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    private Handler handler = new Handler();

    private String message;

    MessageLog messageLog;
    Context context;
    NotificationCompat.Builder notification;
    int uniqueID;

    GetTCPThread(MessageLog _messageLog, Context _context, NotificationCompat.Builder _notification, int _uniqueID) {
        messageLog = _messageLog;
        context = _context;
        notification = _notification;
        uniqueID = _uniqueID;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(7801);

            while (true) {
                socket = serverSocket.accept();
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                message = bufferedReader.readLine() + "\n";
                message += bufferedReader.readLine();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        messageLog.add(message);
                    }
                });

                if (MainActivity.isPaused()) {
                    notificationCall();
                }
            }
        } catch (IOException e) {e.printStackTrace();}
    }

    private void notificationCall() {
        notification.setSmallIcon(R.drawable.ic_launcher_background);
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Get Noticed!");
        notification.setContentText("You have a new message.");

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(uniqueID, notification.build());
    }

}
