package com.example.animarol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ModifyEnemySelectorActivity extends AppCompatActivity {
    String dataPath = "/data/data/com.example.animarol/files";
    String fileExtension = ".enemy";
    LinearLayout enemyContainerLayout;
    ArrayList<String> nameFilesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_enemy_selector);
        startComponents();
        InitiateEnemiesSearch();
    }

    private void startComponents() {
        //Detección de componentes de la interfaz
        enemyContainerLayout = (LinearLayout) findViewById(R.id.MESEnemyContainer);
        nameFilesList = new ArrayList<String>();
    }

    private void InitiateEnemiesSearch() {
        // Detección de los archivos guardados de enemigo
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
            String[] list = dir.list(filter);
            Log.d("Busqueda Archivos",Integer.toString(list.length));
            for (int c=0; c < list.length; c++){
                Button enemyButtonTemp = new Button(this);
                enemyButtonTemp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                enemyButtonTemp.setText(list[c]);
                nameFilesList.add(list[c]);
                enemyButtonTemp.setId(c);
                //Creación del listener de los botones.
                enemyButtonTemp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("BotonID",Integer.toString(view.getId()));
                        ModifyEntityButton(view.getId());
                    }
                });
                enemyContainerLayout.addView(enemyButtonTemp);
                // Se creará un evento de click por cada uno de los botones.
                Log.d("Busqueda Archivos",list[c]);
            }
        }

    }



    private void ModifyEntityButton(int buttonId){
        //Encontrar archivo para modificar.
        try{
            Log.d("EXCEPCION",nameFilesList.get(buttonId));
            FileInputStream FIS = openFileInput(nameFilesList.get(buttonId));

            ObjectInputStream OIS = new ObjectInputStream(FIS);

            //Guardar los datos del archivo
            EnemyEntity enemyTemp = (EnemyEntity) OIS.readObject();
            Log.d("EnemyTempData",enemyTemp.name);
            OIS.close();
            FIS.close();
            //Realizar el intent enviando esos datos.
            Intent intent = new Intent(this,EnemyCreatorFormActivity.class);
            intent.putExtra("EnemyToModify",enemyTemp);
            startActivity(intent);
        }catch (Exception e){
            Log.d("EXCEPCION",e.toString());
            Log.d("EXCEPCION","Se ha producido una excepción al coger el archivo");

        }

    }
}
