package com.example.animarol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class MasterMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_menu);
    }

    public void CreateNewEntityButton (View view){
        Intent intent = new Intent(this,EnemyCreatorFormActivity.class);
        startActivity(intent);
    }

    public void ModifyEntityButton (View view) {
        Intent intent = new Intent(this, ModifyEnemySelectorActivity.class);
        startActivity(intent);
    }
}
