package com.example.animarol;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PlayerThread extends Thread{
    Socket socket;
    String ip;
    String playerName;
    DataOutputStream DOS;
    DataInputStream DIS;
    PlayerEntity playerSelected;
    int eventNumber;

public PlayerThread(String ip,String playerName){
    this.ip = ip;
    this.playerName = playerName;
}

    public void run() {
        try {
            socket = new Socket(ip,9700);
            DOS = new DataOutputStream(socket.getOutputStream());
            DIS = new DataInputStream(socket.getInputStream());

            DOS.writeUTF(playerName);
            while (true){
                eventNumber =DIS.readInt();
                switch (eventNumber){
                    case 1:
                        Log.d("CONSEGUIDO", "Se ha recibido el mensaje desde el servidor yo soy " + playerName);
                        break;
                    default:
                        break;
                }
            }
            //TODO Entrada de los diferentes Inputs de comunicación.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
