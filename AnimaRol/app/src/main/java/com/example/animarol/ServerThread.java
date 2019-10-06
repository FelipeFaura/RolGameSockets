package com.example.animarol;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerThread extends AsyncTask<Void, Void, Void> {
    Socket playerSocket;
    DataInputStream DIS;
    DataOutputStream DOS;
    int idPlayer;
    String playerName;
    public enum ServerActions{
        HELLOALL
    }
    ServerActions serverCurretAction;
    String  message = "Mensaje Por Defecto";

    public ServerThread(Socket playerSocket,int idPlayer){
        this.playerSocket = playerSocket;
        this.idPlayer = idPlayer;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            DIS = new DataInputStream(playerSocket.getInputStream());
            DOS = new DataOutputStream(playerSocket.getOutputStream());
            playerName = DIS.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
