package com.example.animarol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.SocketKeepalive;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class PlayerSessionSelector extends AppCompatActivity {
    String dataPath = "/data/data/com.example.animarol/files";
    String fileExtension = ".player";
    LinearLayout playerContainerLayout;
    String[] listPlayersFiles;
    ArrayList<PlayerEntity> playersDataRecovered;
    PlayerEntity playerSelected;
    Spinner playerSelectedSpinner;
    EditText IPEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_session_selector);
        playersDataRecovered = new ArrayList<PlayerEntity>();
        startComponents();
        GetPlayersSearch();
    }

    private void startComponents() {
        //Detección de componentes de la interfaz
        playerContainerLayout = (LinearLayout) findViewById(R.id.MESEnemyContainer);
        // Creando la referencia del spinner.
        playerSelectedSpinner = (Spinner)findViewById(R.id.PSS_PlayerSelector);
        //Obteniendo el campo donde se introduce la ip
        IPEditText = (EditText) findViewById(R.id.PSS_IPEditText);

    }

    // Recoge todos los archivos de personajes del jugador.
    private void GetPlayersSearch() {
        // Detección de los archivos guardados de player
        FilenameFilter filter = new FilenameFilter() {

            @Override
            public boolean accept(File file, String s) {
                //return true;
                return s.endsWith(fileExtension);
            }
        };
        File dir = new File(dataPath);
        // si el directorio existe se procede a coger los enemigos del directorio, solo los que tienen la extensión .enemy
        if (dir.isDirectory() == true){
            listPlayersFiles = dir.list(filter);
            Log.d("Busqueda Archivos",Integer.toString(listPlayersFiles.length));
            // Introducción de los nombres según la lista en un spinner
            // Se crea un adaptador que hace de intermediario entre los elementos de una lista y el propio spinner.
            if (listPlayersFiles.length > 0) {
                ArrayAdapter adpt = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listPlayersFiles);
                // Se añade el adaptador al propio spinner.
                playerSelectedSpinner.setAdapter(adpt);
                GetPlayersData(listPlayersFiles);
            }
        }
    }

    // Coge los datos de todos los personajes del jugador y los almacena en un array.
    private void GetPlayersData(String[] list){
        for (int c=0; c < list.length; c++){
            try{
                Log.d("EXCEPCION",list[c]);
                FileInputStream FIS = openFileInput(list[c]);

                ObjectInputStream OIS = new ObjectInputStream(FIS);

                //Guardar los datos del archivo
                PlayerEntity playerTemp = (PlayerEntity) OIS.readObject();
                playersDataRecovered.add(playerTemp);
                Log.d("EnemyTempData",playerTemp.name);
                OIS.close();
                FIS.close();

            }catch (Exception e){
                Log.d("EXCEPCION",e.toString());
                Log.d("EXCEPCION","Se ha producido una excepción al coger el archivo");

            }
        }

    }

    // Busca mediante un hilo todas las sesiones que han sido abiertas por un master, y las muestra en la app con un boton
    public void ConnectButton(View view){
        //TODO Orden de conexion, 1) encontrar el archivo de player 2) Enviarlo al hilo junto con los demás 3)Ejecutar el hilo conectandose al socket
        PlayerThread playerThread = new PlayerThread();
        playerThread.execute(IPEditText.getText().toString(),playerSelectedSpinner.getSelectedItem().toString());

    }

}

