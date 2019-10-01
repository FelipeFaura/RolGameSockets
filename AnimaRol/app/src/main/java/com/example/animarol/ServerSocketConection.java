package com.example.animarol;

import android.os.Handler;
import android.util.Log;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class  ServerSocketConection implements Runnable {
    ServerSocket serverSocket;
    Socket socket;
    DataInputStream DIS;
    DataOutputStream DOS;
    String  message = "te has conectado";
    Handler handler = new Handler();


    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(9700);
            while (true) {
                socket = serverSocket.accept();
                DIS = new DataInputStream(socket.getInputStream());
                message = DIS.readUTF();
                postHandler();

            }

        }catch (Exception e){


        }

    }

    private void postHandler (){
        handler.post(new Runnable() {
            @Override
            public void run() {
            }
        });
    }
}
