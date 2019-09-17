package com.example.animarol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class PlayerClassSelector extends AppCompatActivity {

    PlayerEntity.Classes selectedClass = null;
    String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_class_selector);
    }
    public void CreateWarriorButton (View view){
        selectedClass = PlayerEntity.Classes.Warrior;
        CreatePlayer();
        Intent intent = new Intent(this,PlayerMenu.class);
        startActivity(intent);
    }

    public void CreateMageButton (View view) {
        selectedClass = PlayerEntity.Classes.Mage;
        CreatePlayer();
        Intent intent = new Intent(this, PlayerMenu.class);
        startActivity(intent);
    }
    public void CreateAssassinButton(View view){
        selectedClass = PlayerEntity.Classes.Assassin;
        CreatePlayer();
        Intent intent = new Intent(this,PlayerMenu.class);
        startActivity(intent);
    }


    private void CreatePlayer() {

        // Proceso de Comprobación si el nombre existe
        EditText nameEditText = (EditText) findViewById(R.id.PCS_EditTextName);
        playerName = nameEditText.getText().toString();

        try {
            // Aquí comprobaremos si estamos creando un enemigo nuevo o modificando uno
                FileInputStream FIS = openFileInput(playerName);// esto busca el archivo en modo creación, si no lo encuentra salta la excepción y lo crea, en cambio si lo encuentra simplemente muestra un toast de error. y no produce cambios.
                Toast.makeText(getApplicationContext(),"Ya existe un Personaje con ese nombre",Toast.LENGTH_LONG).show();
                nameEditText.setBackgroundColor(Color.RED);
        } catch (FileNotFoundException e) {
            // si se ha producido el error significa que ese nombre no existe así que procedemos a guardar la información.
            //Creación del objeto
            PlayerEntity playerData = new PlayerEntity(selectedClass,playerName);
            // Introducción de los datos, nombre dado por el jugador, clase dada por el boton pulsado (enum) resto de datos Hardcoded en PlayerEntity.
            // Proceso de guardado
            SavePlayer(playerName,playerData);
        }
    }

    private void SavePlayer(String nameEntity, PlayerEntity playerObject){
        try {

            String fileName = nameEntity+".player";
            FileOutputStream FOS = openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream OS = new ObjectOutputStream(FOS);
            OS.writeObject(playerObject);
            OS.close();
            FOS.close();
        } catch (Exception i){
            Log.d("Save Exception",i.toString());
        }
    }


    }

