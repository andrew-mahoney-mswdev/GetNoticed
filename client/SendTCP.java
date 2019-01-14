package com.example.mahoneandr.getnoticedapp;

import android.os.AsyncTask;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class SendTCP extends AsyncTask<String, Void, Void> {

    private Socket socket;
    private DataOutputStream dataOutputStream;
    private PrintWriter printWriter;

    @Override
    protected Void doInBackground(String... voids) {

        String message = voids[0];

        try {
            socket = new Socket("192.168.2.70", 80);//("10.140.52.67", 80); //
            printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.write(message);
            printWriter.flush();
            socket.close();

        } catch (IOException e) {e.printStackTrace();}

        return null;
    }
}
