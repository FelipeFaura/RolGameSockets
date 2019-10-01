package com.example.animarol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // FIX no se porque no se eliminan los archivos que se realizaron en las pruebas así que se borrarán todos los archivos en la primera instalación de la app.
        if(!prefs.getBoolean("firstTime", false)) {
            Log.d("CleanFilesTEST", "Nunca se ha ejecutado este codigo Se acaba de instalar");
            // run your one time code here
            CleanFilesOnFirstInstall();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        } else {
            Log.d("CleanFilesTEST", "Ya se ha ejecutado el codigo una vez");
        }


    }

    private void CleanFilesOnFirstInstall() {
        FilenameFilter filter = new FilenameFilter() {

            @Override
            public boolean accept(File file, String s) {
                //return true;
                return s.endsWith(".enemy");
            }
        };
        File dir = new File("/data/data/com.example.animarol/files");
        String[] list = dir.list(filter);
        if (list != null){
            for (int c=0; c < list.length;c++){
                Log.d("CleanFilesTEST", "Se va a borrar");
                Log.d("CleanFilesTEST", list[c]);
                deleteFile(list[c]);
            }
        }

    }

// **************Buttons*****************
    public void MasterModeButton(View view){
        Intent intent = new Intent(this,MasterMenu.class);
        startActivity(intent);
    }

    public void PlayerModeButton(View view){
        Intent intent = new Intent(this,PlayerMenu.class);
        startActivity(intent);
    }
}
