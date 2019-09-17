package com.example.animarol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PlayerMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_menu);
    }
    public void CreateNewPlayerButton (View view){
        Intent intent = new Intent(this,PlayerClassSelector.class);
        startActivity(intent);
    }

    public void SearchSessionButton (View view) {
        Intent intent = new Intent(this, PlayerSessionSelector.class);
        startActivity(intent);
    }
}
