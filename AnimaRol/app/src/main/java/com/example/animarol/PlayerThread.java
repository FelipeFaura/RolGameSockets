package com.example.animarol;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PlayerThread extends AsyncTask<String, Void, String> {
    Socket socket;
    String ip;
    String playerName;
    DataOutputStream DOS;
    DataInputStream DIS;
    PlayerEntity playerSelected;


    @Override
    protected String doInBackground(String... params) {
        ip = params[0];
        playerName = params[1];
        try {
            socket = new Socket(ip,9700);
            DOS = new DataOutputStream(socket.getOutputStream());
            DIS = new DataInputStream(socket.getInputStream());

            DOS.writeUTF(playerName);
            //TODO Entrada de los diferentes Inputs de comunicación.
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
