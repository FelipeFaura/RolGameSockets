package com.example.animarol;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MasterSessionConnect extends AppCompatActivity {
    public static ArrayList<ServerThread> playersConnectedList;
    public ArrayList<TextView> playerConnectedLabelList;
    LinearLayout playersLayout;
    AsyncTest test;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_session_connect);
        playersConnectedList = new ArrayList<ServerThread>();
        playerConnectedLabelList = new ArrayList<TextView>();
        StartServer();
        StartUIComponents();


    }

    private void StartUIComponents() {
        // Obtención de la IP del master para que pueda indicarla a los jugadores.
        TextView ipTextView = (TextView) findViewById(R.id.MSC_IPTextView);
        String finalIP = "IP MASTER: "+ IPDetector(); // método de detección de la ip, asignandola a la variable final.
        ipTextView.setText(finalIP);
        // Obtención del layout donde añadiremos los jugadores conectados.
        playersLayout = (LinearLayout) findViewById(R.id.MSC_PlayersConectedLayout);
    }


    private void StartServer() {
        // Comienzo del hilo del servidor para que acepte a los usuarios.
        Thread serverThread = new Thread(new ServerSocketConnection());
        serverThread.start();
    }


    private String IPDetector(){

        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wifi.getConnectionInfo().getIpAddress());
        return ip;
    }

    public void AddPlayerConnected(ServerThread playerSocketConnected){
        playersConnectedList.add(playerSocketConnected);
        // creación de la representación del jugador conectado
        //TextView playerNameTextView = new TextView(this);
        //playerNameTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        //playerNameTextView.setText(playerName);
        //playersLayout.addView(playerNameTextView);
        //playerConnectedLabelList.add(playerNameTextView);

    }

    public void StartGameSessionButton (View view){
        /*int number = 0;
        for (int c = 0; c <= playersConnectedList.size() ;c++){
            Log.d("Comprobando conectados", "StartGameSessionButton: " +number++);
        }
        for (int c = 0; c <= playersConnectedList.size() ;c++){
            playersConnectedList.get(c).ServerHelloAll();
        }*/
        AsyncTest.serverAction = AsyncTest.ServerActions.Hello;
    }

    // Socket de conexión esta es la parte del servidor.
    public class ServerSocketConnection implements Runnable {
        ServerSocket serverSocket;
        ServerThread serverThread;
        String  message = "Mensaje Por Defecto";
        Handler handler = new Handler();
        int idPlayer = 0;
        Socket socket;

        @Override
        public void run() {

            try {
                serverSocket = new ServerSocket(9700);
                while (true) {

                    socket = serverSocket.accept();
                    test = new AsyncTest();
                    serverThread = new ServerThread(socket,idPlayer);
                    postHandler();
                    idPlayer++;
                    serverThread.start();


                }

            }catch (Exception e){
                Log.d("ConexionERROR", e.toString() );

            }

        }

        public void SendMessageToAll(){ /*
            for (int c=0; c<=socketList.size(); c++){
                try {
                    DOS = new DataOutputStream(socketList.get(c).getOutputStream());
                    DOS.writeUTF("MensajeTest");
                    DOS.writeUTF("Hola Soy el Servidor");
                    DOS.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } */
        }

        private void postHandler (){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "ID Conectado " + idPlayer, Toast.LENGTH_SHORT).show();
                    AddPlayerConnected(serverThread);
                }
            });
        }
    }
}
