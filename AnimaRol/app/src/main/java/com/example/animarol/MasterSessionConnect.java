package com.example.animarol;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MasterSessionConnect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_session_connect);
        Thread serverThread = new Thread(new ServerSocketConection());
        serverThread.start();
        TextView ipTextView = (TextView) findViewById(R.id.MSC_IPTextView);
        String finalIP = "IP MASTER: "+IPDetector();
        ipTextView.setText(finalIP);

    }


    private String IPDetector(){

        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wifi.getConnectionInfo().getIpAddress());
        return ip;
    }

    // Socket de conexión esta es la parte del servidor.
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
                    //DIS = new DataInputStream(socket.getInputStream());
                    //message = DIS.readUTF();
                    postHandler();

                }

            }catch (Exception e){


            }

        }

        private void postHandler (){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Client Connected", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
