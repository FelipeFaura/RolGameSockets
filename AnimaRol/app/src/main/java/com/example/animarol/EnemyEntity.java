package com.example.animarol;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class EnemyEntity implements Serializable {
    String category;
    String name;
    int level;
    int health;
    int strength;
    int agility;
    int constitution;
    int dexterity;
    int weapon;
    int armor;
    int attack;
    int defense;

    public EnemyEntity(String category,String name, int level,int health,int strength,int agility,int constitution,int dexterity,int weapon,int armor,int attack,int defense){
        this.category = category;
        this.name = name;
        this.level = level;
        this.health = health;
        this.strength = strength;
        this.agility = agility;
        this.constitution = constitution;
        this.dexterity = dexterity;
        this.weapon = weapon;
        this.armor = armor;
        this.attack = attack;
        this.defense = defense;
    }

    public void ShowEnemyData(){
        Log.d("DATACLASSENEMY", this.category);
        Log.d("DATACLASSENEMY", this.name);
        Log.d("DATACLASSENEMY", Integer.toString(this.level));
        Log.d("DATACLASSENEMY", Integer.toString(this.health));
        Log.d("DATACLASSENEMY", Integer.toString(this.strength));
        Log.d("DATACLASSENEMY", Integer.toString(this.agility));
        Log.d("DATACLASSENEMY", Integer.toString(this.constitution));
        Log.d("DATACLASSENEMY", Integer.toString(this.dexterity));
        Log.d("DATACLASSENEMY", Integer.toString(this.weapon));
        Log.d("DATACLASSENEMY", Integer.toString(this.armor));
        Log.d("DATACLASSENEMY", Integer.toString(this.attack));
        Log.d("DATACLASSENEMY", Integer.toString(this.defense));

    }
}
