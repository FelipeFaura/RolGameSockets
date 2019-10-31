package com.example.animarol;

import android.util.Log;
import android.view.View;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread{
    Socket playerSocket;
    DataInputStream DIS;
    DataOutputStream DOS;
    int idPlayer;
    String playerName;
    int eventNumber;

    public enum ServerActions{
        HELLOALL
    }
    ServerActions serverCurretAction;
    String  message = "Mensaje Por Defecto";

    public ServerThread(Socket playerSocket,int idPlayer){
        this.playerSocket = playerSocket;
        this.idPlayer = idPlayer;
    }

    public void run() {
        try {
            DIS = new DataInputStream(playerSocket.getInputStream());
            DOS = new DataOutputStream(playerSocket.getOutputStream());
            playerName = DIS.readUTF();
            while (true){
                //eventNumber =DIS.readInt();
                switch (AsyncTest.serverAction){
                    case Hello:
                        ServerHelloAll();
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("ERRORESHILOS", "run: El Hilo de servidor se ha detenido");
        }
    }


    public void ServerHelloAll(){
        try {
            DOS.writeInt(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
